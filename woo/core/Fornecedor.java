package woo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import woo.app.suppliers.Message;

public class Fornecedor implements Serializable {

  /* Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /* Atributos */
  private String _id;
  private String _nome;
  private String _morada;
  private boolean _ativo;
  private Map<String, Produto> _produtosFornecidos;
  private Map<Integer, Encomenda> _encomendas;

  /**
  * @param id
  * @param nome
  * @param morada
  */
  public Fornecedor(String id, String nome, String morada){
    _id = id;
		_nome = nome;
		_morada = morada;
    _ativo = true;
    _produtosFornecidos = new TreeMap<String, Produto>();
    _encomendas = new TreeMap<Integer, Encomenda>();
  }

  /* Metodos */
	/** Devolve o fornecedor no formato string pretendido
  * @return id|nome|morada|ativo
  */
  @Override
  public String toString(){
    String string = _id + "|" + _nome + "|" + _morada + "|";
    if (_ativo){
      return string + Message.yes();
    }
    else{
      return string + Message.no();
    }
  }

  /**
   * Obtem id do fornecedor
   * @return id
   */
  public String getId(){
		return _id;
	}

  /**
   * Obtem nome do fornecedor
   * @return nome
   */
	public String getNome(){
		return _nome;
	}

  /**
   * Obtem morada do fornecedor
   * @return morada
   */
	public String getMorada(){
		return _morada;
	}

  /**
   * Verifica se o fornecedor tem as transacoes inibidas
   * @return estado das transacoes
   */
  public boolean isAtivo(){
    return _ativo;
  }

  /**
   * Verifica se o fornecedor fornece o produto pedido
   * @param idProduto
   * @return boolean
   */
  public boolean fornece(String idProduto){
    return _produtosFornecidos.containsKey(idProduto.toLowerCase());
  }

  /**
   * Obtem todos os produtos fornecidos
   * @return produtos
   */
  public Collection<Produto> getAllProdutos(){
    return new ArrayList<Produto>(_produtosFornecidos.values());
  }

  /**
   * Obtem todas as encomendas associadas ao fornecedor
   * @return encomendas
   */
  public Collection<Encomenda> getAllEncomendas(){
    return new ArrayList<Encomenda>(_encomendas.values());
  }

  /**
   * Faz toggle ao estado das transacoes do fornecedor
   * @return
   */
  boolean toggleEstado(){
    _ativo = !_ativo;
    return _ativo;
  }

  /**
   * Adiciona o produto pedido
   * @param produto
   */
  void addProdutoFornecido(Produto produto){
    _produtosFornecidos.put(produto.getId().toLowerCase(), produto);
  }

  /**
   * Adiciona uma encomenda
   * @param encomenda
   */
 void addEncomenda(Encomenda encomenda){
    _encomendas.put(encomenda.getId(), encomenda);
  }

}
