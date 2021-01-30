package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Form;
import woo.core.StoreManager;
import woo.app.exception.InvalidDateException;

/**
 * Advance current date.
 */
public class DoAdvanceDate extends Command<StoreManager> {

  /* Atributos */
  private Form _f;
  private Input<Integer> _days;

  /* Inicializacoes dos atributos */
  public DoAdvanceDate(StoreManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    _f = new Form();
    _days = _f.addIntegerInput(Message.requestDaysToAdvance());
  }

  /**
   * @throws InvalidDateException
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    _f.parse();
    if (_days.value() <= 0){
      throw new InvalidDateException(_days.value());
    }
    _receiver.addDays(_days.value());
  }
}
