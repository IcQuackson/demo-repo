package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownClientKeyException;
import woo.core.Cliente;
import woo.core.StoreManager;
import woo.core.Venda;

/**
 * Lookup payments by given client.
 */
public class DoLookupPaymentsByClient extends Command<StoreManager> {

  private Input<String> _idCliente;
  private Form _f;
  private Display _disp;

  public DoLookupPaymentsByClient(StoreManager storefront) {
    super(Label.PAID_BY_CLIENT, storefront);
    _f = new Form();
    _disp = new Display();
    _idCliente = _f.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _f.parse();
    Cliente cliente = _receiver.getCliente(_idCliente.value());

    if (cliente == null){
      throw new UnknownClientKeyException(_idCliente.value());
    }
    
    for (Venda v : cliente.getTransacoes().values()){
        _disp.addLine(v.toString());
    }
    _disp.display();
    _disp.clear();
  }
}
