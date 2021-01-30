package woo.core;

public class Venda extends Transaction{
	/* Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;
	/* Atributos */
	private double _costModified;
	private double _multa;
	private double _desconto;
	private int _paymentDate;
	private Item _item;
	private Cliente _cliente;
	private Produto _produto;

	/* Construtor */
	public Venda(Cliente cliente, int idCounter, int deadline, Produto produto, int quantidade){
		super(deadline, idCounter);
		_multa = 0.0;
		_desconto = 0.0;
		_costModified = 0.0;
		_paymentDate = 0;
		_cliente = cliente;
		_produto = produto;
		/* define os atributos do item */
		_item = new Item(produto, quantidade);
		updateCost();
	}

	/* Metodos */
	/* Getters dos atributos */
	public int getPaymentDate(){
		return _paymentDate;
	}

	public Cliente getCliente(){
		return _cliente;
	}

	public Produto getProduto(){
		return _produto;
	}

	public double getMulta(){
		return _multa;
	}

	public double getDesconto(){
		return _desconto;
	}

	public Item getItem(){
		return _item;
	}

	/** Calcula o range da multa de P1 a P4
	* @return periodo calculado consoante as datas
	*/
	public Periodo getPeriodo(){
		int n = 0;
		int periodo;

		/* Devolve o numero de dias restantes antes de haver multa consoante o produto */
		if (_item.getProduto() instanceof Caixa)
			n = 5;
		else if (_item.getProduto() instanceof Contentor)
			n = 8;
		else if (_item.getProduto() instanceof Livro)
			n = 3;

		/* Calcula a multa a partir do n e da data de "hoje" e a para pagar */
		periodo = Store.getDate() - super.getDate();
		if (periodo >= n)
			return  Periodo.P1;
		else if (periodo >= 0 && periodo < n)
			return Periodo.P2;
		else if (periodo > 1 && periodo <= n)
			return Periodo.P3;
		else if (periodo > n)
			return Periodo.P4;
		return null;
	}

	public double getBaseCost(){
		return super.getCost();
	}

	/**
	* @return custo aplicando desconto e multa
	*/
	@Override
	public double getCost(){
		return _costModified;
	}

	/**
	* Calcula o custo da venda aplicando desconto e multa
	*/
	double updateModifiedCost(){
		if (!isPaid()){
			double baseCost = super.getCost();
			double multaValor = baseCost * _multa;
			double descontoValor = baseCost * _desconto;
			_costModified = baseCost + multaValor - descontoValor;
		}
		return _costModified;
	}

	void updateCost(){
		int cost = 0;
		cost += _item.getCost();
		super.setCost(cost);
	}

	void setPaymentDate(int date){
		_paymentDate = date;
	}

	void setItem(Item item){
		_item = item;
		super.setCost(_item.getCost());
	}

	/** Da set no desconto/multa consoante o periodo e o estatuto do Cliente
	* @param periodo
	*/
	void setMulta(Periodo periodo){
		double multaDesconto = 0;
        int diasPassados = Store.getDate() - super.getDate();
        /* Filtra o estatuto do cliente e cria uma instancia que devolve o resultado correto */
        if (_cliente.getEstatuto() == Estatuto.NORMAL){
            ClienteNormal c = new ClienteNormal();
            multaDesconto = c.getMultaDesconto(periodo, _costModified, diasPassados);
        }
        else if (_cliente.getEstatuto() == Estatuto.SELECTION){
            ClienteSelection c = new ClienteSelection();
            multaDesconto = c.getMultaDesconto(periodo, _costModified, diasPassados);
        }
        else if (_cliente.getEstatuto() == Estatuto.ELITE){
            ClienteElite c = new ClienteElite();
            multaDesconto = c.getMultaDesconto(periodo, _costModified, diasPassados);
        }
				/* Se multaDesconto < 0, entao e o simetrico da multa, senao e desconto */
        if (multaDesconto < 0){
            _multa = -multaDesconto;
        }
        else {
            _desconto = multaDesconto;
        }
    }

	/** Faz o pagamento da venda
	* @param payment
	*/
	@Override
	void pay(){
		int diasPassados = Store.getDate() - super.getDate();
		this.updateModifiedCost();
		if(diasPassados <= 0){
			getCliente().addPontos((int) this.getCost() * 10);
		}
		else{
			getCliente().removePontos(diasPassados);
		}
		setPaid();
		_paymentDate = Store.getDate();
	}

	@Override
	public String toString(){

		String idCliente = this.getCliente().getId();
    String idProduto = this.getProduto().getId();
		String idTransaction = Integer.toString(super.getId());
    String quantidade = Integer.toString(this.getItem().getQuantidade());
    String valorBase = Integer.toString((int) Math.rint(this.getBaseCost()));
    String valorAPagar = Integer.toString((int) Math.rint(this.updateModifiedCost()));
		String deadline = Integer.toString(super.getDate());

		String resultado = idTransaction + "|" + idCliente + "|" + idProduto + "|" + quantidade + "|" + valorBase + "|" + valorAPagar + "|" + deadline;

		if (this.isPaid()){
			String dataPagamento = Integer.toString(this.getPaymentDate());
			return resultado + "|" + dataPagamento;
		}
		else{
			return resultado;
		}
    }
}
