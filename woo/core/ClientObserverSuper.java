package woo.core;

public abstract class ClientObserverSuper {

    /**
     * Adiciona uma notificacao ao observador caso tenha uma subscricao associada ao produto
     * @param notificacao
     */
    abstract void notifyObserver(Notificacao notificacao);

    /**
     * Faz toggle da subscricao associada ao produto 
     * @param produto
     */
    abstract void toggleSubscription(Produto produto);
}
