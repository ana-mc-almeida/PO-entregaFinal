package prr;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import prr.clients.Client;

import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.InvalidTerminalKeyException;
import prr.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownTerminalKeyException;

import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;
import prr.terminals.StateIdle;
import prr.terminals.StateOff;
import prr.terminals.StateSilence;
import prr.terminals.Terminal;
import prr.terminals.TerminalState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	// FIXME define attributes
	// FIXME define contructor(s)
	// FIXME define methods

	/**
	 * Stores the network's clients.
	 */
	private final Map<String, Client> clients = new TreeMap<>();
	/**
	 * Stores the network's clients sorted in CASE_INSENSITIVE_ORDER.
	 */
	private final Map<String, Client> clientsToShow = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	/**
	 * Stores the network's terminals.
	 */
	private final Map<String, Terminal> terminals = new TreeMap<>();

	/**
	 * Stores the network's communications.
	 */
	// private final Map<String, Client> clients = new TreeMap<>();

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException    if some entry is not correct
	 * @throws IOException                   if there is an IO erro while processing
	 *                                       the text file
	 * @throws DuplicateClientKeyException   if a client with the given key
	 *                                       (case-insensitive) already exists
	 * @throws InvalidTerminalKeyException   if the given terminal key is not a
	 *                                       numeric string with 6 digits
	 * @throws DuplicateTerminalKeyException if a terminal with the given key
	 *                                       (case-insensitive) already exists
	 * @throws UnknownClientKeyException     if the given client is unknown
	 * @throws UnknownTerminalKeyException   if the given terminal is unknown
	 * 
	 */
	void importFile(String filename)
			throws UnrecognizedEntryException, IOException,
			DuplicateClientKeyException, InvalidTerminalKeyException,
			DuplicateTerminalKeyException, UnknownClientKeyException,
			UnknownTerminalKeyException {
		// FIXME implement method
		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = in.readLine()) != null) {
				String[] fields = line.split("\\|");
				switch (fields[0]) {
					case "CLIENT" -> this.registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
					case "BASIC", "FANCY" -> this.importTerminal(fields[0], fields[1], fields[2], fields[3]);
					case "FRIENDS" -> this.registerFriends(fields[1], fields[2]);
					default -> throw new UnrecognizedEntryException(String.join("|", fields));
				}
			}
		}
	}

	/**
	 * Register a new client in this network, which will be created from the
	 * given parameters.
	 *
	 * @param key   The key of the client
	 * @param name  The name of the client
	 * @param taxId The taxId of the client
	 * @throws DuplicateClientKeyException if a client with the given key
	 *                                     (case-insensitive) already exists
	 */
	public void registerClient(String key, String name, int taxId) throws DuplicateClientKeyException {
		if (clients.containsKey(key))
			throw new DuplicateClientKeyException(key);

		Client newClient = new Client(key, name, taxId);
		clients.put(key, newClient);
		clientsToShow.put(key, newClient);
	}

	/**
	 * Parse and import a terminal entry from a plain text file, which will need the
	 * given parameters.
	 *
	 * @param type      The type of the terminal (BASIC ou FANCY)
	 * @param key       The key of the terminal
	 * @param keyClient The key of the terminal's owner
	 * @param state     The state of the terminal (ON, OFF or SILENCE)
	 * @throws UnrecognizedEntryException    if some entry is not correct
	 * @throws InvalidTerminalKeyException   if the given terminal key is not a
	 *                                       numeric string with 6 digits
	 * @throws DuplicateTerminalKeyException if a terminal with the given key
	 *                                       (case-insensitive) already exists
	 * @throws UnknownClientKeyException     if the given client is unknown
	 */
	private void importTerminal(String type, String key, String keyClient, String state)
			throws UnrecognizedEntryException, InvalidTerminalKeyException, DuplicateTerminalKeyException,
			UnknownClientKeyException {
		TerminalState terminalState = getTerminalState(state);
		Terminal terminal = registerTerminal(type, key, keyClient);
		terminal.setState(terminalState);
	}

	/*
	 * Parse the terminal state
	 * 
	 * @param state The state of the terminal (ON, OFF or SILENCE)
	 * 
	 * @return The corresponding {@link TerminalState}
	 * 
	 * @throws UnrecognizedEntryException if some entry is not correct
	 */
	private TerminalState getTerminalState(String state) throws UnrecognizedEntryException {
		return switch (state) {
			case "ON" -> new StateIdle();
			case "OFF" -> new StateOff();
			case "SILENCE" -> new StateSilence();
			default -> throw new UnrecognizedEntryException(state);
		};
	}

	/**
	 * Register a new terminal in this network, which will be created from the
	 * given parameters.
	 *
	 * @param type      The type of the terminal (BASIC ou FANCY)
	 * @param key       The key of the terminal
	 * @param keyClient The key of the terminal's owner
	 * 
	 * @return The {@link Terminal} that was just created
	 * 
	 * @throws UnrecognizedEntryException    if some entry is not correct
	 * @throws InvalidTerminalKeyException   if the given terminal key is not a
	 *                                       numeric string with 6 digits
	 * @throws DuplicateTerminalKeyException if a terminal with the given key
	 *                                       (case-insensitive) already exists
	 * @throws UnknownClientKeyException     if the given client is unknown
	 * @throws UnknownTerminalKeyException   if the given terminal is unknown
	 */
	public Terminal registerTerminal(String type, String key, String keyClient)
			throws InvalidTerminalKeyException, DuplicateTerminalKeyException,
			UnknownClientKeyException, UnrecognizedEntryException {
		if (!key.matches("\\d{6}"))
			throw new InvalidTerminalKeyException(key);
		if (terminals.get(key) != null)
			throw new DuplicateTerminalKeyException(key);

		Client terminalsClient = getClientByKey(keyClient);

		Terminal terminal = switch (type) {
			case "BASIC" -> new BasicTerminal(key, terminalsClient);
			case "FANCY" -> new FancyTerminal(key, terminalsClient);
			default -> throw new UnrecognizedEntryException(type);
		};
		terminals.put(key, terminal);
		terminalsClient.addTerminal(terminal);
		return terminal;
	}

	/**
	 * Register the friends of the given terminal.
	 *
	 * @param terminalKey The key of the terminal
	 * @param friendsKeys The friends to regist
	 * @throws UnknownTerminalKeyException if the given terminal is unknown
	 */
	public void registerFriends(String terminalKey, String friendsKeys) throws UnknownTerminalKeyException {

		Terminal terminal = getTerminalByKey(terminalKey);

		String[] friends = friendsKeys.split(",");
		for (String friendKey : friends)
			terminal.addFriend(getTerminalByKey(friendKey));

	}

	/**
	 * Show a client.
	 *
	 * @param key The key of the client
	 * @return The {@link Client} associated with the given key
	 * @throws UnknownClientKeyException if the given terminal is unknown
	 */
	public String showClient(String key) throws UnknownClientKeyException {
		Client client = getClientByKey(key);
		return client.toString();
	}

	/**
	 * Show all clients known to the network.
	 *
	 * @return A sorted {@link Collection} of clients
	 */
	public String showAllClients() {
		List<String> clientStrings = new ArrayList<String>();
		for (Client client : clientsToShow.values()) {
			clientStrings.add(client.toString());
		}
		return String.join("\n", clientStrings);

	}

	/**
	 * Show a terminal.
	 *
	 * @param key The key of the terminal
	 * @return The {@link Terminal} associated with the given key
	 * @throws UnknownTerminalKeyException if the given terminal is unknown
	 */
	public String showTerminal(String key) throws UnknownTerminalKeyException {
		Terminal terminal = getTerminalByKey(key);
		return terminal.toString();
	}

	/**
	 * Show all terminals known to the network,
	 *
	 * @return A sorted {@link Collection} of terminals
	 */
	public String showAllTerminals() {
		List<String> terminalStrings = new ArrayList<String>();
		for (Terminal terminal : terminals.values()) {
			terminalStrings.add(terminal.toString());
		}
		return String.join("\n", terminalStrings);
	}

	/**
	 * Get a client by its key.
	 *
	 * @param key The key of the client
	 * @return The {@link Client} associated with the given key
	 * @throws UnknownClientKeyException if the given terminal is unknown
	 */
	public Client getClientByKey(String key) throws UnknownClientKeyException {
		Client client = clients.get(key);
		if (client == null)
			throw new UnknownClientKeyException(key);
		return client;
	}

	/**
	 * Get a terminal by its key.
	 *
	 * @param key The key of the terminal
	 * @return The {@link Terminal} associated with the given key
	 * @throws UnknownTerminalKeyException if the given terminal is unknown
	 */
	public Terminal getTerminalByKey(String key) throws UnknownTerminalKeyException {
		Terminal terminal = terminals.get(key);
		if (terminal == null)
			throw new UnknownTerminalKeyException(key);
		return terminal;
	}

	/**
	 * Lookup all the terminals that haven't been used yet.
	 *
	 * @return A sorted {@link Collection} of terminals that haven't been used yet
	 */
	public String ShowUnusedTerminals() {
		List<String> terminalStrings = new ArrayList<String>();
		for (Terminal terminal : terminals.values()) {
			if (terminal.isUnused())
				terminalStrings.add(terminal.toString());
		}
		return String.join("\n", terminalStrings);
	}

}
