package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.core.Produto;
import woo.core.StoreManager;

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductsUnderTopPrice extends Command<StoreManager> {

  private Input<Integer> _topPreco;
  private Form _f;
  private Display _disp;

  public DoLookupProductsUnderTopPrice(StoreManager storefront) {
    super(Label.PRODUCTS_UNDER_PRICE, storefront);
    _f = new Form();
    _disp = new Display();
    _topPreco = _f.addIntegerInput(Message.requestPriceLimit());
  }

  @Override
  public void execute() throws DialogException {
    _f.parse();

    for(Produto p : _receiver.getAllProducts()){
      if (p.getPreco() < _topPreco.value())
        _disp.addLine(p.toString());
    }
    _disp.display();
    _disp.clear();
  }
}
