package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.DuplicateProductKeyException;
import woo.app.exception.UnknownServiceLevelException;
import woo.app.exception.UnknownServiceTypeException;
import woo.app.exception.UnknownSupplierKeyException;
import woo.core.ServiceLevel;
import woo.core.ServiceType;
import woo.core.StoreManager;

/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<StoreManager> {

  private Form _f;
  private Input<String> _idProduto;
  private Input<String> _idFornecedor;
  private Input<Integer> _preco;
  private Input<Integer> _valorCritico;
  private Input<String> _serviceType;
  private Input<String> _serviceLevel;

  public DoRegisterProductContainer(StoreManager receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    _f = new Form();
    _idProduto = _f.addStringInput(Message.requestProductKey());
    _preco = _f.addIntegerInput(Message.requestPrice());
    _valorCritico = _f.addIntegerInput(Message.requestStockCriticalValue());
    _idFornecedor = _f.addStringInput(Message.requestSupplierKey());
    _serviceType = _f.addStringInput(Message.requestServiceType());
    _serviceLevel = _f.addStringInput(Message.requestServiceLevel());
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

    if (!_receiver.isServiceType(_serviceType.value())){
      throw new UnknownServiceTypeException(_serviceType.value());
    }

    if (!_receiver.isServiceLevel(_serviceLevel.value())){
      throw new UnknownServiceLevelException(_serviceLevel.value());
    }

    _receiver.registerContentor(_idProduto.value(), _idFornecedor.value(),
                              _preco.value(), _valorCritico.value(),
                              ServiceType.valueOf(_serviceType.value()),
                              ServiceLevel.valueOf(_serviceLevel.value()));
  }
}
