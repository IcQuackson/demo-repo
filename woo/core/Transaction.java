package woo.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable {

  /* Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

	private boolean _paid;
	private double _baseCost;
	private int _id;
	private int _date;


	/**
	* @param date
	* @param _idCounter
	*/
	public Transaction(int date, int _idCounter){
		_paid = false;
		_baseCost = 0.0;
		_id = _idCounter;
		_date = date;
	}

	/* Metodos */
	/* Getters dos atributos */
	/**
	* @return _id
	*/
	public int getId(){
		return _id;
	}


	/**
	* @return _baseCost
	*/
	public double getCost(){
		return  _baseCost;
	}



	/**
	* @return _date
	*/
	public int getDate(){
		return _date;
	}


	/**
	* @param newCost
	*/
	void setCost(int newCost){
		_baseCost = newCost;
	}


	/**
	* @param newDate
	*/
	void setDate(int newDate){
		_date = newDate;
	}


	/* Funcao que trata do pagamento da transacao */
	abstract void pay();


	/**
	* @return _paid
	*/
	public boolean isPaid(){
		return _paid;
	}


	/* Assinala a transacao como paga */
	public void setPaid(){
		_paid = true;
	}

	/** Funcao que retorna a transacao no seu formato preferido
	* @return transacaoString
	*/
	public abstract String toString();
}
