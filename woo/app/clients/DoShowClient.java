package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownClientKeyException;
import woo.core.Cliente;
import woo.core.Notificacao;
import woo.core.StoreManager;

/**
 * Show client.
 */
public class DoShowClient extends Command<StoreManager> {

  /* Atributos */
  private Input<String> _idCliente;
  private Form _f;
  private Display _disp;

  /* Inicializa os atributos */
  public DoShowClient(StoreManager storefront) {
    super(Label.SHOW_CLIENT, storefront);
    _f = new Form();
    _disp = new Display();
    _idCliente = _f.addStringInput(Message.requestClientKey());
  }

  /**
   * @throws UnknownClientKeyException
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException{
    _f.parse();
    Cliente cliente = _receiver.getCliente(_idCliente.value());
    if (cliente == null){
      throw new UnknownClientKeyException(_idCliente.value());
    }
    /* Mostra as info do cliente */
    _disp.addLine(cliente.toString());
    /* Mostra as notificacoes do cliente */
    for (Notificacao notificacao: cliente.getAllNotificacoes()){
      _disp.addLine(notificacao.toString());
    } 
  
    _disp.display();
    _disp.clear();
    }

}
