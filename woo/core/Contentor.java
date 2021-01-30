package woo.core;

public class Contentor extends Caixa {
	/* Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;
	private ServiceLevel _serviceLevel;

	/** 
	* @param idProduto
	* @param preco
	* @param valorCritico
	* @param serviceType
	* @param serviceLevel
	*/
	public Contentor(String idProduto, Fornecedor fornecedor, int preco, 
					int valorCritico, ServiceType serviceType, ServiceLevel serviceLevel){
		super(idProduto, fornecedor, preco, valorCritico, serviceType);
		_serviceLevel = serviceLevel;
	}

	/**
	* Devolve o contentor no formato string pretendido
	* @return CONTAINER|id|idFornecedor|preco|valorCritico|stock|serviceLevel
	*/
	@Override
	public String toString(){
		String[] s = super.toString().split("\\|");
		s[0] = "CONTAINER";
		return String.join("|", s) + "|" + _serviceLevel.name();
	}

	/**
	 * Obtem o nivel de servico
	 * @return
	 */
	ServiceLevel getServiceLevel(){
		return _serviceLevel;
	}
}
