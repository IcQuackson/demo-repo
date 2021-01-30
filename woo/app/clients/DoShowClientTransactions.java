package woo.app.clients;

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
 * Show all transactions for a specific client.
 */
public class DoShowClientTransactions extends Command<StoreManager> {

  /* Atributos */
  private Display _disp;
  private Form _f;
  private Input<String> _idCliente;

  public DoShowClientTransactions(StoreManager storefront) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, storefront);
    _disp = new Display();
    _f = new Form();
    _idCliente = _f.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _f.parse();
    Cliente cliente = _receiver.getCliente(_idCliente.value());

    if (cliente == null){
      throw new UnknownClientKeyException(_idCliente.value());
    }
    
    for (Venda venda : cliente.getTransacoes().values()){
      _disp.addLine(venda.toString());
    }
    _disp.display();
    _disp.clear();
  }
}
