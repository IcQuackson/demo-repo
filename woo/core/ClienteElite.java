package woo.core;

public class ClienteElite implements EstatutoBehaviour{

  @Override
  public int atualizaPontos(int pontos, int diasPassados){
    if (diasPassados > 15){
      return (int) (pontos * 0.25);
    }
    return pontos;
  }

  @Override
  public Estatuto atualizaEstatuto(){
    return Estatuto.SELECTION;
  }

  @Override
  public double getMultaDesconto(Periodo p, double preco, int diasPassados){
    if (p == Periodo.P1){
      return preco * 0.1;
    }
    else if (p == Periodo.P2){
      return preco * 0.1;
    }
    else if (p == Periodo.P3 && diasPassados > 0){
      return preco * 0.05;
    }
    else if (p == Periodo.P4 && diasPassados > 0){
      return 0;
    }
    else{
      return 0;
    }
  }

}
