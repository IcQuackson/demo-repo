package woo.core;

/* Para java docs, ver EstatuoBehaviour */
public class ClienteSelection implements EstatutoBehaviour{

  /* Caso se tenham passado mais de 2 dias, os pontos sao reduzidos */
  @Override
  public int atualizaPontos(int pontos, int diasPassados){
    if (diasPassados > 2){
      return (int) (pontos * 0.1);
    }
    return pontos;
  }

  /* Devolve o estatuto abaixo do seu */
  @Override
  public Estatuto atualizaEstatuto(){
    return Estatuto.NORMAL;
  }

  /* Define a multa (negativo) ou desconto (positivo) */
  @Override
  public double getMultaDesconto(Periodo p, double preco, int diasPassados){
    /* Caso se encontre no periodo P1 */
    if (p == Periodo.P1){
      return preco * 0.1;
    }
    /* Caso se encontre no periodo P2 */
    else if (p == Periodo.P2){
      if (diasPassados > 2)
        return 0;
      else
        return preco * 0.05;
    }
    /* Caso se encontre no periodo P3 */
    else if (p == Periodo.P3 && diasPassados > 0){
      if (diasPassados > 1)
        return 0;
      else
        return - (preco * (0.02 * (diasPassados - 1)));
    }
    /* Caso se encontre no periodo P4 */
    else if (p == Periodo.P4 && diasPassados > 0){
      return - (preco * (0.05 * diasPassados));
    }
    return -1;
  }
}
