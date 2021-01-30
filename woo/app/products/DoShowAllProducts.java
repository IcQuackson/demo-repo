package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import woo.core.Produto;
import woo.core.StoreManager;

/**
 * Show all products.
 */
public class DoShowAllProducts extends Command<StoreManager> {

  /* Atributo */
  private Display _disp;

  /* Inicializa o atributo */
  public DoShowAllProducts(StoreManager receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
    _disp = new Display();
  }

  /**
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    
    for (Produto produto : _receiver.getAllProducts()) {
      _disp.addLine(produto.toString());
    }

    _disp.display();
    _disp.clear();
  }
}
