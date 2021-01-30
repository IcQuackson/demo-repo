package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.DuplicateSupplierKeyException;
import woo.core.StoreManager;


/**
 * Register supplier.
 */
public class DoRegisterSupplier extends Command<StoreManager> {

  private Form _f;
  private Input<String> _idFornecedor;
  private Input<String> _nome;
  private Input<String> _morada;


  public DoRegisterSupplier(StoreManager receiver) {
    super(Label.REGISTER_SUPPLIER, receiver);
    _f = new Form();
    _idFornecedor = _f.addStringInput(Message.requestSupplierKey());
    _nome = _f.addStringInput(Message.requestSupplierName());
    _morada = _f.addStringInput(Message.requestSupplierAddress());
  }

  @Override
  public void execute() throws DialogException {
      _f.parse();
      if (_receiver.getFornecedor(_idFornecedor.value()) != null){
        throw new DuplicateSupplierKeyException(_idFornecedor.value());
      }

      _receiver.registerSupplier(_idFornecedor.value(), _nome.value(), _morada.value());
    }
}
