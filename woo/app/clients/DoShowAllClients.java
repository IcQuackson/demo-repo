package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import woo.core.Cliente;
import woo.core.StoreManager;

/**
 * Show all clients.
 */
public class DoShowAllClients extends Command<StoreManager> {

  /* Atributo */
  private Display _disp;

  /* Inicializa o atributo */
  public DoShowAllClients(StoreManager storefront) {
    super(Label.SHOW_ALL_CLIENTS, storefront);
    _disp = new Display();
  }

  /**
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {

    for (Cliente cliente : _receiver.getAllClientes()) {
        _disp.addLine(cliente.toString());
    }
    _disp.display();
    _disp.clear();
  }
}
