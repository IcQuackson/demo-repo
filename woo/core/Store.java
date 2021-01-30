package woo.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import woo.core.exception.BadEntryException;


/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

    /* Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /* Atributos */
    private static int _date;
    private int _vendasEfetuadas;
    private int _vendasPagas;
    private int _valorEncomendas;
    private int _transactionCounter;

    private TreeMap<String, Cliente> _clientes;
    private TreeMap<String, Fornecedor> _fornecedores;
    private TreeMap<String, Produto> _produtos;
    private TreeMap<Integer, Transaction> _transacoes;
    private List<ClientObserverSuper> _observers;


    public Store(){
      _date = 0;
      _vendasEfetuadas = 0;
      _vendasPagas = 0;
      _valorEncomendas = 0;
      _transactionCounter = 0;
      _clientes = new TreeMap<String, Cliente>();
      _fornecedores = new TreeMap<String, Fornecedor>();;
      _produtos = new TreeMap<String, Produto>();
      _transacoes = new TreeMap<Integer, Transaction>();
      _observers = new ArrayList<ClientObserverSuper>();
    }

    /**
     * Obtem a data atual da loja
     * @return date
     */
    static int getDate(){
      return _date;
    }

    /**
     * Obtem todos os produtos da loja
     * @return lista de produtos
     */
    public Collection<Produto> getAllProducts(){
      return new ArrayList<Produto>(_produtos.values());
    }

    /**
     * Obtem todos os clientes registados na loja
     * @return lista de clientes
     */
    public Collection<Cliente> getAllClientes(){
      return new ArrayList<Cliente>(_clientes.values());
    }

    /**
     * Obtem todos os fornecedores associados a loja
     * @return lista de fornecedores
     */
    public Collection<Fornecedor> getAllFornecedores(){
      return new ArrayList<Fornecedor>(_fornecedores.values());
    }

    /**
     * Obtem todas as transacoes registadas na loja
     * @return lista de transacoes
     */
    public Collection<Transaction> getAllTransacoes(){
      return new ArrayList<Transaction>(_transacoes.values());
    }

    /** Obtem o cliente associado ao id pedido
    * @param id
    * @return Cliente
    */
    Cliente getCliente(String id){
      return _clientes.get(id.toLowerCase());
    }

    /**
     * Obtem o produto associado ao id pedido
     * @param id
     * @return Produto
     */
    Produto getProduto(String id){
      return _produtos.get(id.toLowerCase());
    }

    /**
     * Obtem o fornecedor associado ao id pedido 
     * @param id
     * @return Fornecedor
     */
    Fornecedor getFornecedor(String id){
      return _fornecedores.get(id.toLowerCase());
    }

    /**
     * Obtem transacao associada ao id pedido
     * @param id
     * @return Transacao
     */
    Transaction getTransaction(int id){
      return _transacoes.get(id);
    }

    /**
     * Define uma data
     * @param date
     */
    public void setDate(int date){
      _date = date;
    }

    /**
     * Avanca a data atual
     * @param days
     */
    void addDays(int days){
      _date += days;
    }

    /**
     * Adiciona um produto a loja
     * @param produto
     * @param f
     */
    void addProduto(Produto produto, Fornecedor f){
      f.addProdutoFornecido(produto);
      _produtos.put(produto.getId().toLowerCase(), produto);
      /* Subscreve todos os clientes a este produto */
      addSubscriptionToAll(produto);
    }

    /** Adiciona um cliente a loja
    * @param id
    * @param nome
    * @param morada
    */
    void registerCliente(Cliente cliente){
      _clientes.put(cliente.getId().toLowerCase(), cliente);
      ClienteObserver c = new ClienteObserver(cliente);
      addObserver(c);
      startSubscriptionToAll(c);
    }

    /**
     * Regista fornecedor na loja
     * @param idFornecedor
     * @param nome
     * @param morada
     */
    void registerSupplier(String idFornecedor, String nome, String morada){
      Fornecedor fornecedor = new Fornecedor(idFornecedor, nome, morada);
      _fornecedores.put(idFornecedor.toLowerCase(), fornecedor);
    }

    /**
     * Regista venda na loja
     * @param idCliente
     * @param deadline
     * @param idProduto
     * @param quantidade
     */
    void registerSale(String idCliente, int deadline, String idProduto, int quantidade){
      Produto produto = getProduto(idProduto);
      Cliente cliente = getCliente(idCliente);
      Venda venda = new Venda(cliente, _transactionCounter++, deadline, produto, quantidade);

      /* Coloca venda no conjunto de transacoes */
      _transacoes.put(venda.getId(), venda);
      /* Atualiza stock do produto */
      produto.removeStock(quantidade);
      /* Atualiza o valor das compras efetuadas */
      addCompraEfetuada(venda.getCost());
    }

    /**
     * Regista encomenda na loja
   * @param idFornecedor id do fornecedor onde se quer adicionar a encomenda
   * @param items mapa com pares no formato <idProduto, quantidade>
   */
    void registerOrder(String idFornecedor, Map<String, Integer> items){
      /* Cria uma nova encomenda sem items */
      Encomenda encomenda = new Encomenda(getDate(), getFornecedor(idFornecedor), _transactionCounter++);

      /* Adiciona os items a nova encomenda */
      for (Map.Entry<String, Integer> i : items.entrySet()){
        /* Cria um novo item para ser adicionado */
        Item item = new Item(getProduto(i.getKey()), i.getValue());
        encomenda.addItem(item);
        /* Atualiza stock do produto */
        addStock(i.getKey(), i.getValue());
      }
      _transacoes.put(encomenda.getId(), encomenda);
      /* Atualiza o valor das compras efetuadas */
      addValorEncomendas(encomenda.getCost());
    }

    /**
     * Atribui um preco ao produto pedido
     * @param idProduto
     * @param newPrice
     */
    void setPrice(String idProduto, int newPrice){
      Produto produto = _produtos.get(idProduto.toLowerCase());
      int currentPrice = produto.getPreco();

      produto.setPrice(newPrice);
      /* Notifica os clientes se o preco baixar */
      if (currentPrice > newPrice){
        notifyAll(new Notificacao("BARGAIN", produto));
      }
    }

    /**
     * Adiciona uma quantidade ao stock do produto pedido
     * @param idProduto
     * @param quantidade
     */
    void addStock(String idProduto, int quantidade){
      Produto produto = getProduto(idProduto);
      int currentStock = produto.getStock();

      produto.setStock(currentStock + quantidade);
      /* Notifica os clientes caso o stock mude de 0 para um numero positivo*/
      if (currentStock == 0 && quantidade > 0){
        notifyAll(new Notificacao("NEW", produto));
      }
    }

    /**
     * Adiciona uma quantidade ao stock do produto pedido sem notificar o cliente caso
     * o stock varie de 0 para um numero positivo
     * @param idProduto
     * @param newStock
     */
    void setStockParser(String idProduto, int newStock){
      Produto produto = getProduto(idProduto);

      produto.setStock(newStock);
    }

    /**
     * Ativa ou desativa transacoes de um fornecedor
     * @param id
     * @return boolean
     */
    boolean toggleTransactions(String id){
      return getFornecedor(id).toggleEstado();
    }

    /**
     * Paga transacao
     * @param id
     */
    void payTransaction(int id){
      Transaction transaction = getTransaction(id);
      transaction.pay();
      addCompraPaga(transaction.getCost());
    }

    /**
     * Ativa ou desativa as notificacoes associadas a um produto de um cliente
     * @param idCliente
     * @param idProduto
     * @return
     */
    boolean toggleNotification(String idCliente, String idProduto){
      Cliente cliente = getCliente(idCliente);
      Produto produto = getProduto(idProduto);
      return cliente.toggleSubscription(produto);
    }

    /**
     * Adiciona a todos os observadores a subscricao associada ao produto
     * @param produto
     */
    void addSubscriptionToAll(Produto produto){
      for (ClientObserverSuper observer : _observers){
        observer.toggleSubscription(produto);
      }
    }

    /**
     * 
     * @param clienteObserver
     */
    void startSubscriptionToAll(ClienteObserver clienteObserver){
      for (Map.Entry<String, Produto> p : _produtos.entrySet()){
        clienteObserver.toggleSubscription(p.getValue());
      }
    }

    /** Adiciona uma notificacao a todos os clientes caso estejam subscritos*/
    void notifyAll(Notificacao notificacao){
      for (ClientObserverSuper observer: _observers){
        observer.notifyObserver(notificacao);
      }
    }

    /**
     * Adiciona observador a lista de observadores
     * @param observer
     */
    void addObserver(ClientObserverSuper observer){
      _observers.add(observer);
    }

    /**
     * Incrementa o valor das compras efetuadas
     * @param valor
     */
    private void addCompraEfetuada(double valor){
      _vendasEfetuadas += valor;
    }

    /**
     * Incrementa o valor das compras pagas
     * @param valor
     */
    private void addCompraPaga(double valor){
      _vendasPagas += valor;
    }

    /**Incrementa o valor total das encomendas */
    private void addValorEncomendas(double valor){
      _valorEncomendas += valor;
    }

    /**
     * Mostra o saldo Disponivel
     * @return saldo disponivel
     */
    public int showSaldoDisponivel(){
      return _vendasPagas - _valorEncomendas;
    }

    /**Mostra o saldo contabilistico
     * @return saldo contabilistico
     */
    public int showSaldoContabilistico(){
      return _vendasEfetuadas - _valorEncomendas;
    }

    /*
     * @param txtfile filename to be loaded.
     * @throws IOException
     * @throws BadEntryException
     */
    void importFile(String txtfile) throws IOException, BadEntryException{

      MyParser parse = new MyParser(this);
      parse.parseFile(txtfile);
    }
}
