package woo.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Encomenda extends Transaction{

	/* Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

	private Set<Item> _setItems;
	private Fornecedor _fornecedor;

	/**
	 * 
	 * @param date - data atual da loja
	 * @param fornecedor - fornecedor associado a encomenda
	 * @param idCounter
	 */
	public Encomenda(int date, Fornecedor fornecedor, int idCounter){
		super(date, idCounter);
		_setItems = new HashSet<Item>();
		_fornecedor = fornecedor;
	}

	/**
	 * Obtem o fornecedor associado a encomenda
	 * @return fornecedor
	 */
	Fornecedor getFornecedor(){
		return _fornecedor;
	}

	/**
	 * Obtem todos os Items (Produto, quantidade) contidos na encomenda
	 * @return items
	 */
	public Set<Item> getAllItems(){
		return Collections.unmodifiableSet(_setItems);
	}

	/**	Atualiza o custo total da encomenda */
	void updateCost(){
		int cost = 0;
		for (Item i : _setItems){
			cost += i.getCost();
		}
		super.setCost(cost);
	}

	/** Adiciona um produto a encomenda
	* @param item
	*/
	void addItem(Item item){
		_setItems.add(item);
		updateCost();
	}

	@Override
	public void pay(){
		return;
	}

	/**
	 * Retorna uma string no formato :id|idFornecedor|valor-base|data-pagamento
	 * listando tambem todos os produtos associados a encomenda no formato: idProduto|quantidade
	 */
	@Override
	public String toString(){
		String idTransaction = Integer.toString(super.getId());
		String idFornecedor = this.getFornecedor().getId();
		String valorBase = Integer.toString((int) Math.rint(super.getCost()));
		String dataPagamento = Integer.toString(super.getDate());

		String resultado = String.join("|", idTransaction, idFornecedor, valorBase, dataPagamento);

		for (Item i : _setItems){
			resultado += "\n" + i.getProduto().getId() + "|" + Integer.toString(i.getQuantidade());
		}
		return resultado;
	}
}
