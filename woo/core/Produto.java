package woo.core;

import java.io.Serializable;

public abstract class Produto implements Serializable {

    /* Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

	private String _id;
	private Fornecedor _fornecedor;
	private int _preco;
	private int _valorCritico;
	private int _stock;

 /**
 * @param id
 * @param preco
 * @param valorCritico
 */
	public Produto(String id, Fornecedor fornecedor, int preco, int valorCritico){
		_id = id;
		_preco = preco;
		_valorCritico = valorCritico;
		_stock = 0;
		_fornecedor = fornecedor;
	}

	/** Devolve o produto no formato string pretendido
	*@return |id|idFornecedor|preco|valorCritico|stock
	*/
	@Override
	public String toString(){
		String[] string =
		{_id, _fornecedor.getId(), Integer.toString(_preco), Integer.toString(_valorCritico), Integer.toString(_stock)};
		return "|" + String.join("|", string);
	}

	/**
	 * Obtem id do produto
	 * @return id
	 */
	public String getId(){
		return _id;
	}

	/**
	 * Obtem preco do produto
	 * @return preco
	 */
	public int getPreco(){
		return _preco;
	}

	/**
	 * Obtem valor critico das existencias do produto
	 * @return valor critico
	 */
	public int getValorCritico(){
		return _valorCritico;
	}

	/**
	 * Obtem stock atual do cliente
	 * @return stock
	 */
	public int getStock(){
		return _stock;
	}

	/**
	 * Obtem fornecedor associado ao produto
	 * @return
	 */
	Fornecedor getFornecedor(){
		return _fornecedor;
	}

	/**
	 * Atribui preco ao produto
	 * @param newPrice
	 */
	void setPrice(int newPrice){
		_preco = newPrice;
	}

	/**
	 * Atribui um valor critico das existencias ao produto
	 * @param newValor
	 */
	void setValorCritico(int newValor){
		_valorCritico = newValor;
	}

	/**
	 * Atribui um stock ao produto
	 * @param newStock
	 */
	void setStock(int newStock){
		_stock = newStock;
	}

	/**
	 * Atribui um fornecedor ao produto
	 * @param fornecedor
	 */
	void setFornecedor(Fornecedor fornecedor){
		_fornecedor = fornecedor;
	}

	/**
	 * Remove stock do produto
	 * @param quantidade
	 */
	void removeStock(int quantidade){
		_stock -= quantidade;
	}
}
