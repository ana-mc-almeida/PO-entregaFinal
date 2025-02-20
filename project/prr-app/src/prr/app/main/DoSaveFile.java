package prr.app.main;

import prr.NetworkManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
	}

	@Override
	protected final void execute() {
		try {
			_receiver.save();
		} catch (prr.exceptions.MissingFileAssociationException ex) {
			try {
				_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
			} catch (prr.exceptions.MissingFileAssociationException e1) {

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
