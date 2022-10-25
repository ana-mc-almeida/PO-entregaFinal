package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
		// FIXME add command fields
		addStringField("terminalKey", Prompt.terminalKey());
		addOptionField("terminalType", Prompt.terminalType(), "BASIC", "FANCY");
		addStringField("clientKey", Prompt.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
		// FIXME implement command
		try {
			_receiver.registerTerminal(
					optionField("terminalType"),
					stringField("terminalKey"),
					stringField("clientKey"));
		} catch (prr.exceptions.UnrecognizedEntryException e) {

		} catch (prr.exceptions.UnknownClientKeyException e) {
			throw new UnknownClientKeyException(e.getKey());
		} catch (prr.exceptions.InvalidTerminalKeyException e) {
			throw new InvalidTerminalKeyException(e.getKey());
		} catch (prr.exceptions.DuplicateTerminalKeyException e) {
			throw new DuplicateTerminalKeyException(e.getKey());
		}
	}
}
