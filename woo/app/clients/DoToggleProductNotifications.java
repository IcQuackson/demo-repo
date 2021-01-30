package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Display;
import woo.app.exception.UnknownClientKeyException;
import woo.app.exception.UnknownProductKeyException;
import woo.core.StoreManager;

/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<StoreManager> {

  /* Atributos */
  private Display _disp;
  private Form _f;
  private Input<String> _idCliente;
  private Input<String> _idProduto;


  public DoToggleProductNotifications(StoreManager storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    _disp = new Display();
    _f = new Form();
    _idCliente = _f.addStringInput(Message.requestClientKey());
    _idProduto = _f.addStringInput(Message.requestProductKey());
  }

  @Override
  public void execute() throws DialogException {
    _f.parse();

    if (_receiver.getCliente(_idCliente.value()) == null){
      throw new UnknownClientKeyException(_idCliente.value());
    }

    if (_receiver.getProduto(_idProduto.value()) == null){
      throw new UnknownProductKeyException(_idProduto.value());
    }
    //If true means toggled on, if false means toggled off
    if(_receiver.toggleNotification(_idCliente.value(), _idProduto.value())){
      _disp.addLine(Message.notificationsOn(_idCliente.value(), _idProduto.value()));
    }
    else {
      _disp.addLine(Message.notificationsOff(_idCliente.value(), _idProduto.value()));
    }
    _disp.display();
    _disp.clear();
  }
}
