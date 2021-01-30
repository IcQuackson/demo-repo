package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownSupplierKeyException;
import woo.core.Fornecedor;
import woo.core.StoreManager;
import woo.core.Transaction;

/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<StoreManager> {

  private Display _disp;
  private Form _f;
  private Input<String> _idFornecedor;

  public DoShowSupplierTransactions(StoreManager receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    _disp = new Display();
    _f = new Form();
    _idFornecedor = _f.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    _f.parse();
    Fornecedor fornecedor = _receiver.getFornecedor(_idFornecedor.value());
    
    if (fornecedor == null){
      throw new UnknownSupplierKeyException(_idFornecedor.value());
    }

    for (Transaction e : fornecedor.getAllEncomendas()){
      _disp.addLine(e.toString());
    }
    _disp.display();
    _disp.clear();
  }
}
