import java.rmi.*;

public interface Interface extends Remote {
  Double chamaOperacao(String sinal, String x, String y) throws RemoteException;
};
