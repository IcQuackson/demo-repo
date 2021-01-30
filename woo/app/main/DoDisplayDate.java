package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import woo.core.StoreManager;

/**
 * Show current date.
 */
public class DoDisplayDate extends Command<StoreManager> {

  /* Atributo */
  private Display _disp;

  /* Inicializa o atributo */
  public DoDisplayDate(StoreManager receiver) {
    super(Label.SHOW_DATE, receiver);
    _disp = new Display();
  }

  /**
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    _disp.addLine(Message.currentDate(_receiver.getDate()));
    _disp.display();
    _disp.clear();
  }
}
