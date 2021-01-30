package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownSupplierKeyException;
import woo.core.StoreManager;

/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<StoreManager> {

  private Display _disp;
  private Form _f;
  private Input<String> _idFornecedor;

  public DoToggleTransactions(StoreManager receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    _disp = new Display();
    _f = new Form();
    _idFornecedor = _f.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    _f.parse();

    if (_receiver.getFornecedor(_idFornecedor.value()) == null){
      throw new UnknownSupplierKeyException(_idFornecedor.value());
    }
    /* The output message depends if the transactions are toggled on or off */
    boolean isON = _receiver.toggleTransactions(_idFornecedor.value());

    if (isON){
      _disp.addLine(Message.transactionsOn(_idFornecedor.value()));
    }
    else{
      _disp.addLine(Message.transactionsOff(_idFornecedor.value()));
    }
    _disp.display();
    _disp.clear();
  }
}
