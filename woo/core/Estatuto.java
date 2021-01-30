package woo.core;

public enum Estatuto{
	NORMAL(0), SELECTION(2000), ELITE(2500);
	
	private final int _pontos;
  
	Estatuto(int pontos){
	  _pontos = pontos;
	}
	
	/**
	 * Obtem o numero de pontos associado a constante do enumerado
	 * @return
	 */
	public int getPontos(){
	  return this._pontos;
	}
  }