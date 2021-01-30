package woo.core;

import java.io.Serializable;

public class Notificacao implements Serializable {

  /* Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  private String _descricao;
  private Produto _produto;

  /**
  * @param descricao
  * @param produto
  */
  public Notificacao(String descricao, Produto produto){
    _descricao = descricao;
    _produto = produto;
  }

  /* Metodos */
  /** Devolve a notificacao no formato string pretendido
  * @return descricao|idProduto|preco
  */
  @Override
  public String toString(){
      return _descricao + "|" + _produto.getId() + "|" + Integer.toString(_produto.getPreco());
  }

  /**
   * Obtem descricao da notificacao
   * @return descricao
   */
  public String getDescricao(){
    return _descricao;
  }

  /**
   * Obtem produto associado a notificacao
   * @return
   */
  Produto getProduto(){
    return _produto;
  }
}
