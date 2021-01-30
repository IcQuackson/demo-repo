package woo.core;

/* Interface que estipula o comportamento de um cliente de determinado estatuto */
public interface EstatutoBehaviour{

  /** Atualiza os pontos dos clientes consoante o seu estatuto
  * @param pontos
  * @param diasPassados
  * @return pontos
  */
  public int atualizaPontos(int pontos, int diasPassados);

  /** Atualiza o estatuto dos clientes consoante o seu estatuto anterior
  * @return estatutoNovo
  */
  public Estatuto atualizaEstatuto();

  /** Devolve a multa/desconto dos clientes consoante o seu estatuto, periodo, e dias passados
  * @param periodo
  * @param preco
  * @param diasPassados
  * @return multaDesconto
  */
  public double getMultaDesconto(Periodo p, double preco, int diasPassados);
}
