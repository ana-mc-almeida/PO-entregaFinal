package prr.communications;

import prr.exceptions.CommunicationDestinationIsBusyException;
import prr.exceptions.CommunicationDestinationIsOffException;
import prr.exceptions.CommunicationDestinationIsSilentException;
import prr.terminals.StateBusy;
import prr.terminals.Terminal;

public class VoiceCommunication extends Communication {

    private int duration;

    public VoiceCommunication(Terminal origin, Terminal destination, int key)
            throws CommunicationDestinationIsBusyException,
            CommunicationDestinationIsOffException, CommunicationDestinationIsSilentException {
        super(origin, destination, key);

        destination.canReceiveInteractiveCommunication();
        origin.setState(new StateBusy(origin, true));
        destination.setState(new StateBusy(destination, false));
    }

    public double getPrice(int time) {
        return 0.0; /* FIXME */
    }

    public String getTypeName() {
        return "VOICE";
    }
}
