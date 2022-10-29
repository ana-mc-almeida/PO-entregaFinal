package prr.terminals;

public class StateOff extends TerminalState {

    private String name = "OFF";

    public StateOff(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return name;
    }

    public boolean canStartCommunication() {
        return false;
    }
}
