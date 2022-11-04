package prr.terminals;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.Network;
import prr.clients.Client;
import prr.notifications.Notification;
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
import prr.exceptions.InvalidCommunicationException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.NoOngoingCommunicationException;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

        /** Serial number for serialization. */
        private static final long serialVersionUID = 202208091753L;

        private String key;
        private Double debts = 0.0;
        private Double payments = 0.0;
        private Client client;
        private Map<String, Terminal> friends;
        private TerminalState state;
        private Map<Integer, Communication> communicationsMade;
        private Map<Integer, Communication> communicationsReceived;
        private Communication ongoingCommunication = null;
        private Map<String, Client> textNotifications;
        private Map<String, Client> interactiveNotifications;

        public Terminal(String key, Client client) {
                this.key = key;
                this.client = client;
                state = new StateIdle(this);
                communicationsMade = new TreeMap<Integer, Communication>();
                communicationsReceived = new TreeMap<Integer, Communication>();
                friends = new TreeMap<String, Terminal>();
                textNotifications = new TreeMap<String, Client>();
                interactiveNotifications = new TreeMap<String, Client>();
        }

        public abstract String getTypeName();

        public String getKey() {
                return key;
        }

        public long showDebts() {
                return Math.round(debts);
        }

        public long showPayments() {
                return Math.round(payments);
        }

        public Client getClient() {
                return client;
        }

        public void setState(TerminalState state) {
                this.state = state;
        }

        public void setOngoing(Communication communication) {
                ongoingCommunication = communication;
        }

        public TerminalState getState() {
                return state;
        }

        public Double getDebts() {
                return debts;
        }

        public Double getPayments() {
                return payments;
        }

        public boolean isUnused() {
                return communicationsMade.size() == 0;
        }

        public void addMadeCommunication(Communication comm) {
                communicationsMade.put(comm.getKey(), comm);
        }

        public void addReceiveCommunication(Communication comm) {
                communicationsReceived.put(comm.getKey(), comm);
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
                state.turnOff();
        }

        public void turnOnTerminal() throws TerminalAlreadyOnException {
                state.turnOn();
        }

        public void silenceTerminal() throws TerminalAlreadySilentException {
                state.silence();
        }

        public void sendTextCommunication(Network context, String destinationTerminalKey, String body)
                        throws UnknownTerminalKeyException, CommunicationDestinationIsOffException {

                Terminal destinationTerminal = context.getTerminalByKey(destinationTerminalKey);
                destinationTerminal.canReceiveTextCommunication(this);

                Communication communication = new TextCommunication(this, destinationTerminal,
                                context.getCommunicationsUUID(), body);

                context.addCommunication(communication);
                double price = communication.getPrice();
                debts += price;
                client.addDebt(price);
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

                if (destinationTerminal.getKey().equals(this.key))
                        throw new CommunicationDestinationIsBusyException(this.key);

                destinationTerminal.canReceiveInteractiveCommunication(this);

                if (type == "VIDEO")
                        supportedVideo(destinationTerminal);

                Communication communication = switch (type) {
                        case "VIDEO" ->
                                new VideoCommunication(this, destinationTerminal,
                                                context.getCommunicationsUUID());
                        case "VOICE" ->
                                new VoiceCommunication(this, destinationTerminal, context.getCommunicationsUUID());
                        default -> throw new UnrecognizedEntryException(type);
                };

                this.state.startInterativeCommunication();
                destinationTerminal.state.receiveInterativeCommunication();

                context.addCommunication(communication);
        }

        private void supportedVideo(Terminal destinationTerminal) throws CommunicationUnsupportedAtDestinationException,
                        CommunicationUnsupportedAtOriginException {
                if (!this.canDoVideoCommunication()) {
                        throw new CommunicationUnsupportedAtOriginException(this.getKey(), "VIDEO");
                }
                if (!destinationTerminal.canDoVideoCommunication()) {
                        throw new CommunicationUnsupportedAtDestinationException(
                                        destinationTerminal.getKey(),
                                        "VIDEO");
                }
        }

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive
         *         communication) and
         *         it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
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

        public boolean canReceiveInteractiveCommunication(Terminal origin)
                        throws CommunicationDestinationIsBusyException,
                        CommunicationDestinationIsOffException, CommunicationDestinationIsSilentException {
                return state.canReceiveInteractiveCommunication(origin);
        }

        public boolean canReceiveTextCommunication(Terminal origin) throws CommunicationDestinationIsOffException {
                return state.canReceiveTextCommunication(origin);
        }

        public boolean isFriend(Terminal friend) {
                return friends.containsKey(friend.getKey());
        }

        public abstract boolean canDoVideoCommunication();

        public String showOngoingCommunication() throws NoOngoingCommunicationException {
                if (ongoingCommunication == null)
                        throw new NoOngoingCommunicationException();
                return ongoingCommunication.toString();
        }

        public Collection<Communication> getAllMadeCommunications() {
                return communicationsMade.values();
        }

        public Collection<Communication> getAllReceivedCommunications() {
                return communicationsReceived.values();
        }

        public long endCurrentCommunication(int duration) throws NoOngoingCommunicationException {

                endCommunication();
                ongoingCommunication.getDestinationTerminal().endCommunication();

                double price = ongoingCommunication.end(duration);
                debts += price;
                client.addDebt(price);

                return Math.round(price);
        }

        public void endCommunication() {
                state.endCommunication();
        }

        public void performPayment(int communicationKey) throws InvalidCommunicationException {
                Communication communication = getCommunicationByKey(communicationKey);
                double price = communication.performPayment();
                payments += price;
                debts -= price;
                client.performPayment(price);
        }

        public Communication getCommunicationByKey(int key) throws InvalidCommunicationException {
                Communication communication = communicationsMade.get(key);
                if (communication == null)
                        throw new InvalidCommunicationException(key);
                return communication;
        }

        public boolean hasPositiveBalance() {
                return payments > debts;
        }

        public void addTextNotification(Client client) {
                textNotifications.put(client.getKey(), client);
        }

        public void addInteractiveNotification(Client client) {
                interactiveNotifications.put(client.getKey(), client);
        }

        public Map<String, Client> getTextNotifications() {
                return textNotifications;
        }

        public Map<String, Client> getInteractiveNotifications() {
                return interactiveNotifications;
        }

}