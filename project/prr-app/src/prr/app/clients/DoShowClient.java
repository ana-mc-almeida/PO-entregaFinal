package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

	DoShowClient(Network receiver) {
		super(Label.SHOW_CLIENT, receiver);
		// FIXME add command fields
		addStringField("key", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		// FIXME implement command
		try {
			_display.popup(_receiver.showClient(stringField("key")));
		} catch (prr.exceptions.UnknownClientKeyException e) {
			throw new UnknownClientKeyException(e.getKey());
		}
	}
}
