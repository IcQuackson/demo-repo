package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownTransactionKeyException;
import woo.core.StoreManager;
import woo.core.Transaction;

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<StoreManager> {

  private Display _disp;
  private Form _f;
  private Input<Integer> _id;

  public DoShowTransaction(StoreManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    _disp = new Display();
    _f = new Form();

    _id = _f.addIntegerInput(Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException {
    _f.parse();
    Transaction transaction = _receiver.getTransaction(_id.value());
    if (transaction == null){
      throw new UnknownTransactionKeyException(_id.value());
    }
    _disp.addLine(transaction.toString());
    _disp.display();
    _disp.clear();
  }
}
