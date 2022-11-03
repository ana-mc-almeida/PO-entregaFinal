package prr.app.terminal;

import javax.naming.Context;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
		addStringField("key", Prompt.terminalKey());
		addOptionField("type", Prompt.commType(), "VOICE", "VIDEO");
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.startInterativeCommunication(_network, stringField("key"), optionField("type"));
		} catch (prr.exceptions.UnknownTerminalKeyException e) {
			throw new UnknownTerminalKeyException(e.getKey());
		} catch (prr.exceptions.CommunicationDestinationIsOffException e) {
			_display.popup(Message.destinationIsOff(e.getKey()));
		} catch (prr.exceptions.CommunicationDestinationIsBusyException e) {
			_display.popup(Message.destinationIsBusy(e.getKey()));
		} catch (prr.exceptions.CommunicationDestinationIsSilentException e) {
			_display.popup(Message.destinationIsSilent(e.getKey()));
		} catch (prr.exceptions.CommunicationUnsupportedAtDestinationException e) {
			_display.popup(Message.unsupportedAtDestination(e.getKey(), e.getType()));
		} catch (prr.exceptions.CommunicationUnsupportedAtOriginException e) {
			_display.popup(Message.unsupportedAtOrigin(e.getKey(), e.getType()));
		} catch (prr.exceptions.UnrecognizedEntryException e) {

		}
	}
}
