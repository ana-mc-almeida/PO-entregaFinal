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
}
