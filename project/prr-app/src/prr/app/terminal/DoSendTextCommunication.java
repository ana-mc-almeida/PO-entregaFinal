package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
                addStringField("key", Prompt.terminalKey());
                addStringField("body", Prompt.textMessage());
        }

        @Override
        protected final void execute() throws CommandException {
                try {
                        _receiver.sendTextCommunication(_network, stringField("key"), stringField("body"));
                } catch (prr.exceptions.UnknownTerminalKeyException e) {
                        throw new UnknownTerminalKeyException(e.getKey());
                } catch (prr.exceptions.CommunicationDestinationIsOffException e) {
                        _display.popup(Message.destinationIsOff(e.getKey()));
                }
        }
}
