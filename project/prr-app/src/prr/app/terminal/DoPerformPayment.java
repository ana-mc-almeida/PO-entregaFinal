package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

	DoPerformPayment(Network context, Terminal terminal) {
		super(Label.PERFORM_PAYMENT, context, terminal);
		addIntegerField("key", Prompt.commKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.performPayment(integerField("key"));
		} catch (prr.exceptions.InvalidCommunicationException e) {
			_display.popup(Message.invalidCommunication());
		}
	}
}
