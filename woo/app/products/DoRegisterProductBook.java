package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.DuplicateProductKeyException;
import woo.app.exception.UnknownSupplierKeyException;
import woo.core.StoreManager;

/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<StoreManager> {

  private Form _f;
  private Input<String> _idProduto;
  private Input<String> _idFornecedor;
  private Input<Integer> _preco;
  private Input<Integer> _valorCritico;
  private Input<String> _titulo;
  private Input<String> _autor;
  private Input<String> _isbn;

  public DoRegisterProductBook(StoreManager receiver) {
    super(Label.REGISTER_BOOK, receiver);
    _f = new Form();
    _idProduto = _f.addStringInput(Message.requestProductKey());
    _titulo = _f.addStringInput(Message.requestBookTitle());
    _autor = _f.addStringInput(Message.requestBookAuthor());
    _isbn = _f.addStringInput(Message.requestISBN());
    _preco = _f.addIntegerInput(Message.requestPrice());
    _valorCritico = _f.addIntegerInput(Message.requestStockCriticalValue());
    _idFornecedor = _f.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public final void execute() throws DialogException {
    _f.parse();
    if (_receiver.getProduto(_idProduto.value()) != null){
      throw new DuplicateProductKeyException(_idProduto.value());
    }
    if (_receiver.getFornecedor(_idFornecedor.value()) == null){
      throw new UnknownSupplierKeyException(_idFornecedor.value());
    }

    _receiver.registerLivro(_idProduto.value(), _idFornecedor.value(), _preco.value(),
                            _valorCritico.value(), _titulo.value(), _autor.value(), _isbn.value());
  }
}
