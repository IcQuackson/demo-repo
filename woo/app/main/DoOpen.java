package woo.app.main;

import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.FileOpenFailedException;
import woo.core.StoreManager;

/**
 * Open existing saved state.
 */
public class DoOpen extends Command<StoreManager> {

    private Form _f;
    private Input<String> _filename;

  /** @param receiver */
  public DoOpen(StoreManager receiver) {
    super(Label.OPEN, receiver);
    _f = new Form();
    _filename = _f.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute()
  * @throws DialogException
  */
  @Override
  public final void execute() throws DialogException{
    _f.parse();
    try{
        _receiver.load(_filename.value());
    }
    catch (ClassNotFoundException | IOException e){
      throw new FileOpenFailedException(_filename.value());
    }
  }
}