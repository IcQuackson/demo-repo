package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.core.StoreManager;
import woo.core.exception.MissingFileAssociationException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<StoreManager> {

    /* Atributos */
    private Form _f;
    private Input<String> _filename;

    /** @param receiver */
    public DoSave(StoreManager receiver) {
        super(Label.SAVE, receiver);
        _f = new Form();
        _filename = _f.addStringInput(Message.newSaveAs());
    }

    /** @see pt.tecnico.po.ui.Command#execute()
    * @throws DialogException
    */
    public final void execute() throws DialogException{
      try{
        _receiver.save();
      }
      catch (MissingFileAssociationException e){
        _f.parse();
        _receiver.saveAs(_filename.value());
      }
    }
}
