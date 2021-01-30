package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownTransactionKeyException;
import woo.core.StoreManager;

/**
 * Pay transaction (sale).
 */
public class DoPay extends Command<StoreManager> {

  private Form _f;
  private Input<Integer> _idVenda;
  
  public DoPay(StoreManager storefront) {
    super(Label.PAY, storefront);
    _f = new Form();
    _idVenda = _f.addIntegerInput(Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException {
    _f.parse();
    /* verifies transaction id */
    if (_receiver.getTransaction(_idVenda.value()) == null){
      throw new UnknownTransactionKeyException(_idVenda.value());
    }
    _receiver.payTransaction(_idVenda.value());
  }
}
