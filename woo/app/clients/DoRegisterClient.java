package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import woo.app.exception.DuplicateClientKeyException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Form;
import woo.core.StoreManager;

/**
 * Register new client.
 */
public class DoRegisterClient extends Command<StoreManager> {

  /* Atributos */
  private Form _f;
  private Input<String> _idNovo;
  private Input<String> _nomeNovo;
  private Input<String> _moradaNova;

  /* Inicializa os atributos */
  public DoRegisterClient(StoreManager storefront) {
    super(Label.REGISTER_CLIENT, storefront);
    _f = new Form();
    _idNovo = _f.addStringInput(Message.requestClientKey());
    _nomeNovo = _f.addStringInput(Message.requestClientName());
    _moradaNova = _f.addStringInput(Message.requestClientAddress());
  }

  /**
   * @throws DuplicateClientKeyException
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {
    _f.parse();

    if (_receiver.getCliente(_idNovo.value()) != null){
      throw new DuplicateClientKeyException(_idNovo.value());
    }
    _receiver.registerCliente(_idNovo.value(), _nomeNovo.value(), _moradaNova.value());
  }

}
