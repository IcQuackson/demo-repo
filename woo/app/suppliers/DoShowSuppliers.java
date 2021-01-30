package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import woo.core.Fornecedor;
import woo.core.StoreManager;

/**
 * Show all suppliers.
 */
public class DoShowSuppliers extends Command<StoreManager> {

  /* Atributo */
  private Display _disp;

  /* Inicializa o atributo */
  public DoShowSuppliers(StoreManager receiver) {
    super(Label.SHOW_ALL_SUPPLIERS, receiver);
    _disp = new Display();
  }

  /**
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {
    for (Fornecedor f : _receiver.getAllFornecedores()){
      _disp.addLine(f.toString());
    }
    _disp.display();
    _disp.clear();
  }
}
