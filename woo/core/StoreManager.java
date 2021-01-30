package woo.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;

import woo.app.exception.FileOpenFailedException;
import woo.core.exception.BadEntryException;
import woo.core.exception.ImportFileException;
import woo.core.exception.MissingFileAssociationException;
/**
 * StoreManager: façade for the core classes.
 */
public class StoreManager {

  /** Current filename. */
  private String _filename = "";

  /** The actual store. */
  private Store _store;

  public StoreManager(){
    _store = new Store();
  }

  /**
   * Obtem nome do ficheiro de load
   * @return file name
   */
  public String getFileName(){
    return _filename;
  }

  /**
   * Obtem data atual da loja
   * @return data
   */
  public int getDate(){
    return Store.getDate();
  }

  /**
   * Obtem todos os produtos da loja
   * @return produtos
   */
  public Collection<Produto> getAllProducts(){
    return _store.getAllProducts();
  }

  /**
   * Obtem todos os clientes da loja
   * @return clientes
   */
  public Collection<Cliente> getAllClientes(){
    return _store.getAllClientes();
  }

  /**
   * Obtem todos os fornecedores
   * @return fornecedores
   */
  public Collection<Fornecedor> getAllFornecedores(){
    return _store.getAllFornecedores();
  }

  /**
   * Obtem todas as transacoes
   * @return transacoes
   */
  public Collection<Transaction> getAllTransacoes(){
    return _store.getAllTransacoes();
  }

  /**
   * Mostra o cliente
   * @param id
   * @return string cliente
   */
  public String showCliente(String id){
    return getCliente(id).toString();
  }

  /**
   * Mostra o produto
   * @param id
   * @return string produto
   */
  public String showProduto(String id){
    return getProduto(id).toString();
  }

  /**
   * Mostra o fornecedor
   * @param id
   * @return string fornecedor
   */
  public String showFornecedor(String id){
    return getFornecedor(id).toString();
  }

  /**
   * Mostra a transacao
   * @param id
   * @return string transacao
   */
  public String showTransaction(int id){
    return getTransaction(id).toString();
  }

  /**
   * Obtem cliente
   * @param id
   * @return Cliente
   */
  public Cliente getCliente(String id){
    return _store.getCliente(id);
  }

  /**
   * Obtem produto
   * @param id
   * @return Produto
   */
  public Produto getProduto(String id){
    return _store.getProduto(id);
  }

  /**
   * Obtem fornecedor
   * @param id
   * @return Fornecedor
   */
  public Fornecedor getFornecedor(String id){
    return _store.getFornecedor(id);
  }

  /**
   * Obtem transacao
   * @param id
   * @return Transacao
   */
  public Transaction getTransaction(int id){
    return _store.getTransaction(id);
  }

  /**
   * Desativa ou ativa transacoe sde um fornecedor
   * @param id
   * @return boolean
   */
  public boolean toggleTransactions(String id){
    return _store.toggleTransactions(id);
  }

  /**
   * Paga um Transacao
   * @param id
   */
  public void payTransaction(int id){
    _store.payTransaction(id);
  }

  /**
    *Verifica se a string corresponde ao um tipo de servico valido
   * 
   * @param s
   * @return
   */
  public boolean isServiceType(String s){
    for (ServiceType type : ServiceType.values()) {
        if (type.name().equals(s)) {
            return true;
        }
    }
    return false;
  }

  /**
   * Verifica se a string corresponde a um nivel de servico valido
   * @param s
   * @return
   */
  public boolean isServiceLevel(String s){
    for (ServiceLevel level : ServiceLevel.values()) {
        if (level.name().equals(s)) {
            return true;
        }
    }
    return false;
  }

  /**
   * Mostra saldo disponivel
   * @return saldo disponivel
   */
  public int showSaldoDisponivel(){
    return _store.showSaldoDisponivel();
  }

  /**
   * Mostr saldo contabilistico
   * @return saldo contabilistico
   */
  public int showSaldoContabilistico(){
    return _store.showSaldoContabilistico();
  }

  /**
   * Adiciona dias a data atual
   * @param days
   */
  public void addDays(int days){
    _store.addDays(days);
  }

  /**
   * Regista cliente na loja
   * @param id
   * @param nome
   * @param morada
   */
  public void registerCliente(String id, String nome, String morada){
    _store.registerCliente(new Cliente(id, nome, morada)); //FIXME
  }

  /**Regista Venda na loja */
  public void registerSale(String idCliente, int deadline, String idProduto, int quantidade){
    _store.registerSale(idCliente, deadline, idProduto, quantidade);
  }

  /**Regista Caixa na loja */
  public void registerCaixa(String idProduto, String idFornecedor, int preco, int valorCritico,
                            ServiceType serviceType){
    Fornecedor f = getFornecedor(idFornecedor);
    Caixa caixa = new Caixa(idProduto, f, preco, valorCritico, serviceType);
    _store.addProduto(caixa, f);
  }

  /**
   * Regista Contentor na loja
   * @param idProduto
   * @param idFornecedor
   * @param preco
   * @param valorCritico
   * @param serviceType
   * @param serviceLevel
   */
  public void registerContentor(String idProduto, String idFornecedor, int preco,
                                int valorCritico, ServiceType serviceType, ServiceLevel serviceLevel){
    Fornecedor f = getFornecedor(idFornecedor);
    Contentor contentor = new Contentor(idProduto, f, preco, valorCritico, serviceType, serviceLevel);
    _store.addProduto(contentor, f);
  }

  /**
   * Regista Livro na loja
   * @param idProduto
   * @param idFornecedor
   * @param preco
   * @param valorCritico
   * @param titulo
   * @param autor
   * @param isbn
   */
  public void registerLivro(String idProduto, String idFornecedor, int preco,
                            int valorCritico, String titulo, String autor, String isbn){
    Fornecedor f = getFornecedor(idFornecedor);
     Livro livro = new Livro(idProduto, f, preco, valorCritico, titulo, autor, isbn);
    _store.addProduto(livro, f);
  }

  /**
   * Regista fornecedor na loja
   * @param idFornecedor
   * @param nome
   * @param morada
   */
  public void registerSupplier(String idFornecedor, String nome, String morada){
    _store.registerSupplier(idFornecedor, nome, morada);
  }

  /**
   * Regista Encomenda na loja
   * @param idFornecedor
   * @param items
   */
  public void registerOrder(String idFornecedor, Map<String, Integer> items){
    _store.registerOrder(idFornecedor, items);
  }

  /**
   * Atribui um preco a um produto
   * @param idProduto
   * @param newPrice
   */
  public void setPrice(String idProduto, int newPrice){
    _store.setPrice(idProduto, newPrice);
  }

  /**
   * Desliga as notificacoes de um produto de um cliente
   * @param idCliente
   * @param idProduto
   * @return
   */
  public boolean toggleNotification(String idCliente, String idProduto){
    return _store.toggleNotification(idCliente, idProduto);
  }

  /**
   * @throws IOException
   * @throws MissingFileAssociationException
   */
  public void save() throws MissingFileAssociationException{
    try {
      if (_filename == ""){
        throw new MissingFileAssociationException();
      }
      ObjectOutputStream storeOut = new ObjectOutputStream(new FileOutputStream(_filename));
      storeOut.writeObject(_store);
      storeOut.close();
    }
    catch (IOException e){
      return;
    }
  }

  /**
   * @param filename
   * @throws MissingFileAssociationException
   */
  public void saveAs(String filename){
    try{
      _filename = filename;
      save();
    }
    catch (MissingFileAssociationException e){
      return;
    }
  }

  /**
   * @param filename
   * @throws FileOpenFailedException
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void load(String filename) throws FileOpenFailedException, IOException, ClassNotFoundException{
    ObjectInputStream storeIn = null;
    /* Le ficheiro e atualiza a store*/
    try (FileInputStream fpin = new FileInputStream(filename)){
        storeIn = new ObjectInputStream(fpin);
        _store = (Store)storeIn.readObject();
    }
}

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
        _store.importFile(textfile);
    } catch (IOException | BadEntryException e) {
        throw new ImportFileException(textfile);
    }
  }
}
