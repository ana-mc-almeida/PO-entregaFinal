package prr.communications;

import prr.exceptions.CommunicationUnsupportedAtOriginException;
import prr.exceptions.CommunicationUnsupportedAtDestinationException;
import prr.exceptions.CommunicationDestinationIsBusyException;
import prr.exceptions.CommunicationDestinationIsOffException;
import prr.exceptions.CommunicationDestinationIsSilentException;
import prr.terminals.StateBusy;
import prr.terminals.Terminal;

public class VideoCommunication extends Communication {

    private static final String name = "VIDEO";

    public VideoCommunication(Terminal origin, Terminal destination, int key)
            throws CommunicationDestinationIsBusyException,
            CommunicationDestinationIsOffException,
            CommunicationDestinationIsSilentException,
            CommunicationUnsupportedAtOriginException,
            CommunicationUnsupportedAtDestinationException {
        super(origin, destination, key);

        if (!origin.canDoVideoCommunication()) {
            throw new CommunicationUnsupportedAtOriginException(origin.getKey(), name);
        }
        if (!destination.canDoVideoCommunication()) {
            throw new CommunicationUnsupportedAtDestinationException(destination.getKey(), name);
        }

        destination.canReceiveInteractiveCommunication();
        origin.setState(new StateBusy(origin, true));
        destination.setState(new StateBusy(destination, false));
    }

    public double getPrice() {
        Terminal owner = getOriginTerminal();
        Terminal destination = getDestinationTerminal();
        return owner.getClient().getPriceVideoComm(getUnits(), owner.isFriend(destination));
    }

    public String getTypeName() {
        return name;
    }
};