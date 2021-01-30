package woo.core;

import java.io.Serializable;

public class Subscription implements Serializable{
    /* Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /* Atributo */
    private String _idProduto;

    /** Construtor
    * @param idProduto
    */
    public Subscription(String idProduto){
        _idProduto = idProduto;
    }

    /* Metodo */
    /**
    * @return _idProduto
    */
    public String getIdProduto() {
        return _idProduto;
    }
}
