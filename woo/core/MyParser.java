package woo.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import woo.core.exception.BadEntryException;

public class MyParser {
	/* Atributo */
	private Store _store;

	/* Construtor */
	MyParser(Store s) {
		_store = s;
	}

	/**
	* @param filename
	* @throws IOException
	* @throws BadEntryException
	*/
	void parseFile(String fileName) throws IOException, BadEntryException {

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
		 	String line;

			while ((line = reader.readLine()) != null){
				parseLine(line);
			}
		}
	}

	/**
	* @param line
	* @throws BadEntryException
	*/
  private void parseLine(String line) throws BadEntryException {

		String[] components = line.split("\\|");

		switch(components[0]) {
			case "SUPPLIER":
				parseSupplier(line, components);
				break;

			case "CLIENT":
				parseClient(line, components);
				break;

			case "BOX":
				parseBox(line, components);
				break;

			case "CONTAINER":
				parseContainer(line, components);
				break;

			case "BOOK":
				parseBook(line, components);
				break;

			default:
				throw new BadEntryException("Type of line not supported: " + line);
			}
  	}

  /**
	* @param SUPPLIER|id|nome|endereço
	* @throws BadEntryException
	*/
  private void parseSupplier(String line, String[] components)  throws BadEntryException {
		if (components.length != 4)
	  		throw new BadEntryException("Invalid number of fields in supplier description (4) " + line);

		String id = components[1];
		String name = components[2];
		String address = components[3];
		_store.registerSupplier(id, name, address);
  }

	/**
	* @param CLIENT|id|nome|endereço
	* @throws BadEntryException
	*/
	private void parseClient(String line, String[] components) throws BadEntryException {
		if (components.length != 4)
	  		throw new BadEntryException("Invalid number of fields (4) in client description: " + line);

		String id = components[1];
		String name = components[2];
		String address = components[3];
		Cliente cliente = new Cliente(id, name, address);
		_store.registerCliente(cliente);

  }

	/**
	* @param BOX|id|tipo-de-serviço|id-fornecedor|preço|valor-crítico|exemplares
	* @throws BadEntryException
	*/
	private void parseBox(String line, String[] components) throws BadEntryException {
		if (components.length != 7)
	  		throw new BadEntryException("wrong number of fields in box description  (7) " + line);

		String idProduto = components[1];
		ServiceType service = ServiceType.valueOf(components[2]);
		String idSupplier = components[3];
		int price = Integer.parseInt(components[4]);
		int criticalValue = Integer.parseInt(components[5]);
		int stock = Integer.parseInt(components[6]);

		Fornecedor f = _store.getFornecedor(idSupplier);

		Caixa caixa = new Caixa(idProduto, f, price, criticalValue, service);
		_store.addProduto(caixa, f);
		_store.setStockParser(idProduto, stock);


  }

	/**
	* @param BOOK|id|título|autor|isbn|id-fornecedor|preço|valor-crítico|exemplares
	* @throws BadEntryException
	*/
	private void parseBook(String line, String[] components) throws BadEntryException {
		if (components.length != 9)
			throw new BadEntryException("Invalid number of fields (9) in box description: " + line);

		String idProduto = components[1];
		String title = components[2];
		String author = components[3];
		String isbn = components[4];
		String idSupplier = components[5];
		int price = Integer.parseInt(components[6]);
		int criticalValue = Integer.parseInt(components[7]);
		int stock = Integer.parseInt(components[8]);

		Fornecedor f = _store.getFornecedor(idSupplier);

		Livro livro = new Livro(idProduto, f, price, criticalValue, title, author, isbn);
		_store.addProduto(livro, f);
		_store.setStockParser(idProduto, stock);
  }

	/**
	* @param CONTAINER|id|tipo-de-serviço|nível-de-serviço|id-fornecedor|preço|valor-crítico|exemplares
	* @throws BadEntryException
	*/
	private void parseContainer(String line, String[] components) throws BadEntryException {
		if (components.length != 8)
			throw new BadEntryException("Invalid number of fields (8) in container description: " + line);

		String idProduto = components[1];
		ServiceType serviceType = ServiceType.valueOf(components[2]);
		ServiceLevel serviceLevel = ServiceLevel.valueOf(components[3]);
		String idSupplier = components[4];
		int price = Integer.parseInt(components[5]);
		int criticalValue = Integer.parseInt(components[6]);
		int stock = Integer.parseInt(components[7]);

		Fornecedor f = _store.getFornecedor(idSupplier);

		Contentor contentor = new Contentor(idProduto, f, price, criticalValue, serviceType, serviceLevel);
		_store.addProduto(contentor, f);
		_store.setStockParser(idProduto, stock);
	}
}
