package woo.core;

import java.io.Serializable;

public class ClienteObserver extends ClientObserverSuper implements Serializable {
    /* Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

    private Cliente _cliente;

    /**
     * 
     * @param cliente
     */
    ClienteObserver(Cliente cliente){
        _cliente = cliente;
    }
    
    void notifyObserver(Notificacao notificacao){
        if (_cliente.containsSubscription(notificacao.getProduto())){
            _cliente.addNotificacao(notificacao);
        }
    }

    void toggleSubscription(Produto produto){
        _cliente.toggleSubscription(produto);
    }
}
