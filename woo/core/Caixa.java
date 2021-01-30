package woo.core;

public class Caixa extends Produto {
	/* Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;
	/* Atributos */
	private ServiceType _serviceType;

	/**
	* @param idProduto
	* @param preco
	* @param valorCritico
	* @param serviceType
	*/
	public Caixa(String idProduto, Fornecedor fornecedor, int preco, int valorCritico, ServiceType service){
		super(idProduto, fornecedor, preco, valorCritico);
		_serviceType = service;
	}

	/* Metodos */
	/** Devolve a caixa no formato string pretendido
	* BOX|id|idFornecedor|preco|valorCritico|stock|serviceType
	*@return string
	*/
	@Override
	public String toString(){
		return "BOX" + super.toString() + "|" + _serviceType.name();
	}

	/* Getter do nivel de servico */
	public ServiceType getServiceType(){
		return _serviceType;
	}

	/* Setter do nivel de servico */
	protected void setServiceType(ServiceType service){
		_serviceType = service;
	}
}
