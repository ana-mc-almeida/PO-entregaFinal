package prr.terminals;

public class StateBusy extends TerminalState {

    private String name = "BUSY";

    public StateBusy(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return name;
    }

    public boolean canStartCommunication() {
        return false;
    }
}
