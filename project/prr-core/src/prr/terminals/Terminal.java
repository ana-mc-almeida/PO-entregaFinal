package prr.terminals;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.Network;
import prr.clients.Client;
import prr.communications.Communication;
import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.exceptions.CommunicationDestinationIsBusyException;
import prr.exceptions.CommunicationDestinationIsOffException;
import prr.exceptions.CommunicationDestinationIsSilentException;
import prr.exceptions.CommunicationUnsupportedAtDestinationException;
import prr.exceptions.CommunicationUnsupportedAtOriginException;
import prr.exceptions.TerminalAlreadyOffException;
import prr.exceptions.TerminalAlreadyOnException;
import prr.exceptions.TerminalAlreadySilentException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.noOngoingCommunicationException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */ {

        /** Serial number for serialization. */
        private static final long serialVersionUID = 202208091753L;

        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

        private String key;
        private Double debts = 0.0;
        private Double payments = 0.0;
        private Client client;
        // private List<String> friends; // stor nao quer list
        private Map<String, Terminal> friends;
        private TerminalState state;
        // private List<Communication> communications; // verificar se é list ou map
        private Map<Integer, Communication> communications;

        public Terminal(String key, Client client) {
                this.key = key;
                this.client = client;
                state = new StateIdle(this);
                // communications = new ArrayList<Communication>();
                communications = new TreeMap<Integer, Communication>();
                // friends = new ArrayList<String>();
                friends = new TreeMap<String, Terminal>();
        }

        public abstract String getTypeName();

        public String getKey() {
                return key;
        }

        public Client getClient() {
                return client;
        }

        public void setState(TerminalState state) {
                this.state = state;
        }

        public TerminalState getState() {
                return state;
        }

        public Double getDebts() {
                return debts;
        }

        public Double getPayments() {
                return debts;
        }

        public boolean isUnused() {
                return communications.size() == 0;
        }

        public void addFriend(Network context, String friendKey) throws UnknownTerminalKeyException {
                if (!friendKey.equals(this.key)) {
                        Terminal friend = context.getTerminalByKey(friendKey);
                        friends.put(friend.getKey(), friend);
                }
        }

        public void removeFriend(Network context, String friendKey) throws UnknownTerminalKeyException {
                context.getTerminalByKey(friendKey);
                friends.remove(friendKey);
        }

        public String friendsToString() {
                List<String> friendsStrings = new ArrayList<String>();
                for (Terminal friend : friends.values()) {
                        friendsStrings.add(friend.getKey());
                }
                return String.join(",", friendsStrings);
        }

        @Override
        public String toString() {
                String s = getTypeName() + "|" + key + "|" + client.getKey() + "|" + state.status() + "|"
                                + Math.round(payments)
                                + "|"
                                + Math.round(debts);
                if (friends.size() != 0)
                        s += "|" + friendsToString();
                return s;
        }

        public void turnOffTerminal() throws TerminalAlreadyOffException {
                TerminalState newState = new StateOff(this);
                if (state.status().equals(newState.status()))
                        throw new TerminalAlreadyOffException();
                setState(newState);
        }

        public void turnOnTerminal() throws TerminalAlreadyOnException {
                TerminalState newState = new StateIdle(this);
                if (state.status().equals(newState.status()))
                        throw new TerminalAlreadyOnException();
                setState(newState);
        }

        public void silenceTerminal() throws TerminalAlreadySilentException {
                TerminalState newState = new StateSilent(this);
                if (state.status().equals(newState.status()))
                        throw new TerminalAlreadySilentException();
                setState(newState);
        }

        public void sendTextCommunication(Network context, String destinationTerminalKey, String body)
                        throws UnknownTerminalKeyException, CommunicationDestinationIsOffException {

                Terminal destinationTerminal = context.getTerminalByKey(destinationTerminalKey);
                Communication communication = new TextCommunication(this, destinationTerminal,
                                context.getCommunicationsUUID(), body);
                communications.put(communication.getKey(), communication);
        }

        public void startInterativeCommunication(Network context, String destinationTerminalKey, String type)
                        throws UnknownTerminalKeyException,
                        CommunicationDestinationIsBusyException,
                        CommunicationDestinationIsOffException,
                        CommunicationDestinationIsSilentException,
                        CommunicationUnsupportedAtDestinationException,
                        CommunicationUnsupportedAtOriginException,
                        UnrecognizedEntryException {

                Terminal destinationTerminal = context.getTerminalByKey(destinationTerminalKey);

                Communication communication = switch (type) {
                        case "VIDEO" ->
                                new VideoCommunication(this, destinationTerminal, context.getCommunicationsUUID());
                        case "VOICE" ->
                                new VoiceCommunication(this, destinationTerminal, context.getCommunicationsUUID());
                        default -> throw new UnrecognizedEntryException(type);
                };

                communications.put(communication.getKey(), communication);
        }

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive
         *         communication) and
         *         it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
                // FIXME add implementation code
                return state.canEndCurrentCommunication();
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
                return state.canStartCommunication();
        }

        public boolean canReceiveInteractiveCommunication() throws CommunicationDestinationIsBusyException,
                        CommunicationDestinationIsOffException, CommunicationDestinationIsSilentException {
                return state.canReceiveInteractiveCommunication();
        }

        public boolean canReceiveTextCommunication() throws CommunicationDestinationIsOffException {
                return state.canReceiveTextCommunication();
        }

        public boolean isFriend(Terminal friend) {
                return friends.containsKey(friend.getKey());
        }

        public abstract boolean canDoVideoCommunication();

        public Communication getOngoingCommunication() throws noOngoingCommunicationException {
                for (Communication communication : communications.values()) {
                        if (communication.getOnGoing())
                                return communication;
                }
                throw new noOngoingCommunicationException();
        }

        public long endCurrentCommunication(int duration) throws noOngoingCommunicationException {
                Communication communication = getOngoingCommunication();
                state.returnToPreviusState();
                return Math.round(communication.end(duration));
        }

        public String showOngoingCommunication() throws noOngoingCommunicationException {
                Communication communication = getOngoingCommunication();
                return communication.toString();
        }
}