package prr.terminals;

import java.io.Serializable;

/**
 * Abstract terminal state.
 */
public abstract class TerminalState implements Serializable {

    protected Terminal terminal;

    public TerminalState(Terminal terminal) {
        this.terminal = terminal;
    }

    public abstract String getName();

    public abstract boolean canStartCommunication();

    public String status() {
        return this.getName();
    }

    // @Override
    // public boolean equals(Object o) {
    // if (o instanceof TerminalState) {
    // TerminalState state = (TerminalState) o;
    // return getName().equals(o.getName); // nao funciona porque TerminalState tem
    // getName como abstract
    // }
    // return false;
    // }

}
