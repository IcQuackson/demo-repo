package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnavailableProductException;
import woo.app.exception.UnknownClientKeyException;
import woo.app.exception.UnknownProductKeyException;
import woo.core.StoreManager;

/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<StoreManager> {

  private Form _f;
  private Input<String> _idCliente;
  private Input<Integer> _deadline;
  private Input<String> _idProduto;
  private Input<Integer> _quantidade;

  public DoRegisterSaleTransaction(StoreManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _f = new Form();
    _idCliente = _f.addStringInput(Message.requestClientKey());
    _deadline = _f.addIntegerInput(Message.requestPaymentDeadline());
    _idProduto = _f.addStringInput(Message.requestProductKey());
    _quantidade = _f.addIntegerInput(Message.requestAmount());
  }

  @Override
  public final void execute() throws DialogException {
    _f.parse();
    int stock;

    if (_receiver.getCliente(_idCliente.value()) == null){
      throw new UnknownClientKeyException(_idCliente.value());
    }
    if (_receiver.getProduto(_idProduto.value()) == null){
      throw new UnknownProductKeyException(_idProduto.value());
    }
    if ((stock = _receiver.getProduto(_idProduto.value()).getStock()) < _quantidade.value()){
      throw new UnavailableProductException(_idProduto.value(), _quantidade.value(), stock);
    }
    _receiver.registerSale(_idCliente.value(), _deadline.value(), _idProduto.value(), _quantidade.value());
  }
}
