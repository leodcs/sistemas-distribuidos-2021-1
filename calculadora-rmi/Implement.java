import java.rmi.*;
import java.rmi.server.*;

public class Implement extends UnicastRemoteObject implements Interface {
  public Implement() throws RemoteException {
    super();
  }

  public Double chamaOperacao(String sinal, String x, String y) {
    Double resultado, double1, double2;
    resultado = double1 = double2 = 0.0;

    try {
      double1 = Double.parseDouble(x);
      double2 = Double.parseDouble(y);
    } catch (NumberFormatException e) {
      System.out.print("\"" + x + "\"" + " não é um número!");
    }

    switch(sinal) {
    case "+":
      resultado = soma(double1, double2);
      break;
    case "-":
      resultado = subtrai(double1, double2);
      break;
    case "/":
      resultado = divide(double1, double2);
      break;
    case "*":
      resultado = multiplica(double1, double2);
      break;
    };

    return resultado;
  };

  private Double soma(Double x, Double y) {
    return x + y;
  };

  private Double subtrai(Double x, Double y) {
    return x - y;
  };

  private Double multiplica(Double x, Double y) {
    return x * y;
  };

  private Double divide(Double x, Double y) {
    return x / y;
  };
}
