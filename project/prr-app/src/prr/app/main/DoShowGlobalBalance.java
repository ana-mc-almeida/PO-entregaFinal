package prr.app.main;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show global balance.
 */
class DoShowGlobalBalance extends Command<Network> {

	DoShowGlobalBalance(Network receiver) {
		super(Label.SHOW_GLOBAL_BALANCE, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		_display.popup(Message.globalPaymentsAndDebts(_receiver.showGlobalPayments(), _receiver.showGlobalDebts()));
	}
}
