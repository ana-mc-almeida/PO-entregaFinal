package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

	DoEndInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal,
				receiver -> receiver.canEndCurrentCommunication());
		addIntegerField("duration", Prompt.duration());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_display.popup(Message.communicationCost(_receiver.endCurrentCommunication(integerField("duration"))));
		} catch (prr.exceptions.NoOngoingCommunicationException e) {
			_display.popup(Message.noOngoingCommunication());
		}

	}
}
