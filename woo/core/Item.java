package woo.core;

import java.io.Serializable;

public class Item implements Serializable {
	/* Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;
	/* Atributos */
	private Produto _produto;
	private int _quantidade;

	/**
	 * 
	 * @param produto
	 * @param quantidade
	 */
	public Item(Produto produto, int quantidade){
		_produto = produto;
		_quantidade = quantidade;
	}

	/**
	 * Obtem o produto associado ao item
	 * @return produto
	 */
	public Produto getProduto(){
		return _produto;
	}

	/**
	 * Obtem a quantidade de elementos associados ao produto
	 * @return quantidade
	 */
	public int getQuantidade(){
		return _quantidade;
	}

	/**
	 * Obtem custo total do item
	 * @return custo
	 */
	public int getCost(){
		return _quantidade * _produto.getPreco();
	}

	/**
	 * Atribui um produto ao item
	 * @param produto
	 */
	void setProduto(Produto produto){
		_produto = produto;
	}

	/**
	 * Atribui uma quantidade de elementos do produto
	 * @param quantidade
	 */
	void setQuantidade(int quantidade){
		_quantidade = quantidade;
	}
}
