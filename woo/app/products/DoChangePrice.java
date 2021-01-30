package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.core.StoreManager;

/**
 * Change product price.
 */
public class DoChangePrice extends Command<StoreManager> {

  private Form _f;
  private Input<String> _idProduto;
  private Input<Integer> _preco;

  public DoChangePrice(StoreManager receiver) {
    super(Label.CHANGE_PRICE, receiver);
    _f = new Form();
    _idProduto = _f.addStringInput(Message.requestProductKey());
    _preco = _f.addIntegerInput(Message.requestPrice());
  }

  @Override
  public final void execute() throws DialogException {
    _f.parse();
    /* Verifica se o id e preco sao validos */
    if (_receiver.getProduto(_idProduto.value()) != null && _preco.value() >= 0){
      _receiver.setPrice(_idProduto.value(), _preco.value());
    }
  }
}
