package prr.app.main;

import prr.NetworkManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

import java.io.IOException;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
	}

	@Override
	protected final void execute() {
		// FIXME implement command and create a local Form
		try {
			try {
				_receiver.save();
			} catch (prr.exceptions.MissingFileAssociationException ex) {
				saveAs();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveAs() throws IOException {
		try {
			_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
		} catch (prr.exceptions.MissingFileAssociationException e) {
			saveAs();
		}
	}
}
