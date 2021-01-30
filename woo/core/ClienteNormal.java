package woo.core;

/* Para java docs, ver EstatuoBehaviour */
public class ClienteNormal implements EstatutoBehaviour{

  /* Como o cliente e normal, os pontos nao se reduzem */
  @Override
  public int atualizaPontos(int pontos, int diasPassados){
    return pontos;
  }

  /* Como ja e o estatuto mais baixo, devove-se a si mesmo */
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
      return 0;
    }
    /* Caso se encontre no periodo P3 */
    else if (p == Periodo.P3 && diasPassados > 0){
      return - (preco * (0.05 * diasPassados));
    }
    /* Caso se encontre no periodo P4 */
    else if (p == Periodo.P4 && diasPassados > 0){
      return - (preco * (0.1 * diasPassados));
    }
    return -1;
  }
}
