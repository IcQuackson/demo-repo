package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;
import woo.core.StoreManager;

/**
 * Show global balance.
 */
public class DoShowGlobalBalance extends Command<StoreManager> {

  private Display _disp;

  public DoShowGlobalBalance(StoreManager receiver) {
    super(Label.SHOW_BALANCE, receiver);
    _disp = new Display();
  }

  @Override
  public final void execute() {
    int saldoDisponivel = _receiver.showSaldoDisponivel();
    int saldoContabilistico = _receiver.showSaldoContabilistico();

    _disp.addLine(Message.currentBalance(saldoDisponivel, saldoContabilistico));
    _disp.display();
    _disp.clear();    
  }
}
