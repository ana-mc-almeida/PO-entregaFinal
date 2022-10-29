package prr.terminals;

public class StateSilent extends TerminalState {

    private String name = "SILENCE";

    public StateSilent(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return name;
    }

    public boolean canStartCommunication() {
        return true;
    }
}
