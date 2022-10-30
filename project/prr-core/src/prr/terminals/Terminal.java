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
import prr.exceptions.TerminalAlreadyOffException;
import prr.exceptions.TerminalAlreadyOnException;
import prr.exceptions.TerminalAlreadySilentException;
import prr.exceptions.UnknownTerminalKeyException;

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
        private Map<String, Terminal> friends;
        // private List<String> friends; // stor nao quer list
        private List<Communication> communications; // verificar se é list ou map
        private TerminalState state;

        public Terminal(String key, Client client) {
                this.key = key;
                this.client = client;
                state = new StateIdle(this);
                communications = new ArrayList<Communication>();
                friends = new TreeMap<String, Terminal>();
                // friends = new ArrayList<String>();
        }

        public abstract String getTypeName();

        public String getKey() {
                return key;
        }

        public void setState(TerminalState state) {
                this.state = state;
        }

        public Double getDebts() {
                return debts;
        }

        public Double getPayments() {
                return debts;
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
                return false;
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
                // FIXME add implementation code
                // if (this.state.equals(new StateBusy(this)) || this.state.equals(new
                // StateOff(this)))
                // return false;
                // return true;
                return state.canStartCommunication();
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

        // public void addFriend(Network context, String friendKey) throws
        // UnknownTerminalKeyException {
        // context.getTerminalByKey(friendKey);
        // if (!friendKey.equals(this.key))
        // friends.add(friendKey);
        // }

        // public void removeFriend(Network context, String friendKey) throws
        // UnknownTerminalKeyException {
        // context.getTerminalByKey(friendKey);
        // if (!friendKey.equals(this.key))
        // friends.remove(friendKey);
        // }

        public String friendsToString() {
                List<String> friendsStrings = new ArrayList<String>();
                for (Terminal friend : friends.values()) {
                        friendsStrings.add(friend.getKey());
                }
                return String.join(",", friendsStrings);
        }

        // public String friendsToString() {
        // return String.join(",", friends);
        // }

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
}