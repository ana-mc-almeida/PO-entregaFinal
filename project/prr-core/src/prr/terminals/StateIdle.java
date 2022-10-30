package prr.terminals;

public class StateIdle extends TerminalState {

    private String name = "IDLE";

    public StateIdle(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return name;
    }

    public boolean canStartCommunication() {
        return true;
    }

    public boolean canEndCurrentCommunication() {
        return false;
    }

    public boolean canReceiveTextCommunication() {
        return true;
    }

    public boolean canReceiveInteractiveCommunication() {
        return true;
    }
}
