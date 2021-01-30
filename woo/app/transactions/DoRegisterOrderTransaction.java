package woo.app.transactions;

import java.util.Map;
import java.util.TreeMap;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnauthorizedSupplierException;
import woo.app.exception.UnknownProductKeyException;
import woo.app.exception.UnknownSupplierKeyException;
import woo.app.exception.WrongSupplierException;
import woo.core.Fornecedor;
import woo.core.StoreManager;

/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<StoreManager> {

  private Form _f;
  private Input<String> _idFornecedor;
  private Input<String> _idProduto;
  private Input<Integer> _quantidade;
  private Map<String, Integer> _items;
  private Input<String> _more;

  public DoRegisterOrderTransaction(StoreManager receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);
    _f = new Form();
  }

  @Override
  public final void execute() throws DialogException {
    _items = new TreeMap<String, Integer>();
    _idFornecedor = _f.addStringInput(Message.requestSupplierKey());
    _f.parse();
    _f.clear();
    Fornecedor fornecedor = _receiver.getFornecedor(_idFornecedor.value());

    if (fornecedor == null){
      throw new UnknownSupplierKeyException(_idFornecedor.value());
    }

    if (!fornecedor.isAtivo()){
      throw new UnauthorizedSupplierException(_idFornecedor.value());
    }

    do{
      _f = new Form();
      /* Recebe o input */
      _idProduto = _f.addStringInput(Message.requestProductKey());
      _quantidade = _f.addIntegerInput(Message.requestAmount());
      _more = _f.addStringInput(Message.requestMore());
      _f.parse(true);
      _f.clear();

      if (_receiver.getProduto(_idProduto.value()) == null){
        throw new UnknownProductKeyException(_idProduto.value());
      }

      if (!fornecedor.fornece(_idProduto.value())){
        throw new WrongSupplierException(_idFornecedor.value(), _idProduto.value());
      }
      /* Guarda o par produto/quantidade */
      _items.put(_idProduto.value(), _quantidade.value());

    } while (_more.value().equals("s")); /* Continua a pedir se a resposta foi sim */

    _receiver.registerOrder(_idFornecedor.value(), _items);
  }
}
