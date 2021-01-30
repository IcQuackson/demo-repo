package woo.core;

public class Livro extends Produto{

	/* Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;
	/* Atributos */
	private String _titulo;
	private String _autor;
	private String _isbn;

	/** Construtor
	* @param id
	* @param preco
	* @param valorCritico
	* @param titulo
	* @param autor
	* @param isbn
	*/
	public Livro(String idProduto, Fornecedor fornecedor, int preco, 
				int valorCritico, String titulo, String autor, String isbn){
		super(idProduto, fornecedor, preco, valorCritico);
		this._titulo = titulo;
		this._autor = autor;
		this._isbn = isbn;
	}

	/** Devolve o livro no formato string pretendido
	* @return BOOK|id|idFornecedor|preco|valorCritico|stock|titulo|autor|isbn
	*/
	@Override
	public String toString(){
		return "BOOK" + super.toString() + "|" + _titulo + "|" +_autor + "|" + _isbn;
	}

	/**
	 * Obtem titulo do livro
	 * @return titulo
	 */
	public String getTitulo(){
		return _titulo;
	}

	/**
	 * Obtem autor do livro
	 * @return autor
	 */
	public String getAutor(){
		return _autor;
	}

	/**
	 * Obtem ISBN do livro
	 * @return isbn
	 */
	public String getIsbn(){
		return _isbn;
	}

	/**
	 * Atriubui um titulo ao livro
	 * @param titulo
	 */
	void setTitulo(String titulo){
		_titulo = titulo;
	}

	/**
	 * Atribui um autor ao livro
	 * @param autor
	 */
	void setAutor(String autor){
		_autor = autor;
	}

	/**
	 * Atribui um ISBN ao livro
	 * @param isbn
	 */
	void setISBN(String isbn){
		_isbn = isbn;
	}
}
