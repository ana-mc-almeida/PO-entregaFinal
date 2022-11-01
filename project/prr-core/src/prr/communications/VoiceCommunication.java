package prr.communications;

import prr.exceptions.CommunicationDestinationIsBusyException;
import prr.exceptions.CommunicationDestinationIsOffException;
import prr.exceptions.CommunicationDestinationIsSilentException;
import prr.terminals.StateBusy;
import prr.terminals.Terminal;

public class VoiceCommunication extends Communication {

    public VoiceCommunication(Terminal origin, Terminal destination, int key)
            throws CommunicationDestinationIsBusyException,
            CommunicationDestinationIsOffException, CommunicationDestinationIsSilentException {
        super(origin, destination, key);

        origin.setState(new StateBusy(origin, true));
        destination.setState(new StateBusy(destination, false));
    }

    public double getPrice() {
        Terminal owner = getOriginTerminal();
        Terminal destination = getDestinationTerminal();
        return owner.getClient().getPriceVoiceComm(getUnits(), owner.isFriend(destination));
    }

    public String getTypeName() {
        return "VOICE";
    }
}
