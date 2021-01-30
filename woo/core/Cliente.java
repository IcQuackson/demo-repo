package woo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Cliente implements Serializable {

    /* Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

	private String _id;
	private String _nome;
	private String _morada;
	private Estatuto _estatuto;
	private int _pontos;
	private Map<String, Venda> _vendas;
	private List<Notificacao> _notificacoes;
	private Map<String, Produto> _subscriptions;

	/**
	* @param id
	* @param nome
	* @param morada
	*/
	public Cliente(String id, String nome, String morada){
		_id = id;
		_nome = nome;
		_morada = morada;
		_estatuto = Estatuto.NORMAL;
		_pontos = 0;
		_vendas = new TreeMap<String, Venda>();
		_notificacoes = new ArrayList<Notificacao>();
		_subscriptions = new TreeMap<String, Produto>();
	}

	/** Devolve o cliente no formato string pretendido
	* @return id|nome|morada|estatuto|comprasEfetuadas|comprasPagas
	*/
	@Override
	public String toString(){

		String cliente[] =
		new String[] {_id, _nome, _morada, _estatuto.name(), Integer.toString(getComprasEfetuadas()), Integer.toString(getComprasPagas())};
		return String.join("|", cliente);
	}

	/**
	 * 
	 * @return id do cliente
	 */
	public String getId(){
		return _id;
	}

	/**
	 * 
	 * @return nome do cliente
	 */
	public String getNome(){
		return _nome;
	}

	/**
	 * 
	 * @return morada do cliente
	 */
	public String getMorada(){
		return _morada;
	}

	/**
	 * 
	 * @return estatuto do cliente
	 */
	public Estatuto getEstatuto(){
		return _estatuto;
	}

	/**
	 * 
	 * @return quantidade pontos que o cliente tem
	 */
	public int getPontos(){
		return _pontos;
  	}
	
	/**
	 * Adiciona pontos ao cliente
	 * @param pontos
	 */
	void addPontos(int pontos){
		this._pontos += pontos;
		updateEstatuto();
	}

	/**
	 * removePontos com base nos dias que passaram depois da deadline e no estatuto
	 * @param diasPassados
	 */
	void removePontos(int diasPassados){
        if (_estatuto == Estatuto.SELECTION){
			ClienteSelection cliente = new ClienteSelection();
			_pontos = cliente.atualizaPontos(_pontos, diasPassados);
			_estatuto = cliente.atualizaEstatuto();
        }
        else if (_estatuto == Estatuto.ELITE){
			ClienteElite cliente = new ClienteElite();
			_pontos = cliente.atualizaPontos(_pontos, diasPassados);
			_estatuto = cliente.atualizaEstatuto();
          }
    }

	/**
	 * Atualiza o estatuto
	 */
	private void updateEstatuto(){
		if (_pontos < Estatuto.SELECTION.getPontos()){
			_estatuto = Estatuto.NORMAL;
		}
		else if (_pontos < Estatuto.ELITE.getPontos()){
			_estatuto = Estatuto.SELECTION;
		}
		else{
			_estatuto = Estatuto.ELITE;
		}
	}

	/**
	 * Retorna todas as transacoes associadas ao cliente
	 * @return vendas
	 */
	public Map<String, Venda> getTransacoes(){
		return Collections.unmodifiableMap(_vendas);
	}

	/**
	 * Retorna todas as subscricoes atuais do cliente
	 * @return subscricoes
	 */
	public Map<String, Produto> getSubscriptions(){
		return Collections.unmodifiableMap(_subscriptions);
	}

	/**
	 * Obtem o valor das compras do cliente que foram efetivamente pagas
	 * @return compras pagas
	 */
	public int getComprasPagas(){
		int comprasPagas = 0;
		for (Map.Entry<String, Venda> venda : _vendas.entrySet()){
			if ((venda.getValue()).isPaid()){
				comprasPagas += (venda.getValue()).getCost();
			}
		}
		return comprasPagas;
	}

	/**
	 * Obtem o valor das compras efetuadas(sejam pagas ou nao) pelo cliente
	 * @return compras efetuadas
	 */
	public int getComprasEfetuadas(){
		int comprasEfetuadas = 0;
		for (Map.Entry<String, Venda> venda : _vendas.entrySet()){
			comprasEfetuadas += (venda.getValue()).getCost();
		}
		return comprasEfetuadas;
	}

	/**
	 * Obtem todas as notificacoes do cliente e depois apaga-as
	 * @return notificacoes
	 */
	public List<Notificacao> getAllNotificacoes(){
		List<Notificacao> notificacoes = new ArrayList<Notificacao>(_notificacoes);
		/* Apos mostrar as notificacoes, apaga-as */
		clearNotificacoes();
		return notificacoes;
	}

	/**
	 * Adiciona uma notificacao ao cliente
	 * @param notificacao
	 */
	void addNotificacao(Notificacao notificacao){
		_notificacoes.add(notificacao);
	}

	/**
	 * Adiciona uma subscricao ao cliente
	 * Cada subscricao esta composta pelo produto associado e pela sua descricao
	 * @param produto
	 */
	void addSubscription(Produto produto){
		_subscriptions.put(produto.getId().toLowerCase(), produto);
	}

	/**
	 * Informa se existe alguma subscricao associada ao produto pedido
	 * @param produto
	 * @return boolean
	 */
	boolean containsSubscription(Produto produto){
		return _subscriptions.containsKey(produto.getId().toLowerCase());
	}

	/**
	 * Remove a subcricao associada ao produto pedido
	 * @param produto
	 */
	void removeSubscription(Produto produto){
		_subscriptions.remove(produto.getId().toLowerCase());
	}

	/**
	 * Elimina todas as notificacoes
	 */
	void clearNotificacoes(){
		_notificacoes.clear();
	}

	/**
	 * Faz toggle da subscricao associada ao produto pedido
	 * @param produto
	 * @return boolean que indica se a subscricao foi removida ou adicionada
	 */
	boolean toggleSubscription(Produto produto){
		/* Adiciona uma nova subscricao do produto caso nao exista
		e elimina-a caso exista */
		if (!_subscriptions.containsKey(produto.getId().toLowerCase())){
			addSubscription(produto);
			return true;
		}
		else {
			removeSubscription(produto);
			return false;
		}
	}

}
