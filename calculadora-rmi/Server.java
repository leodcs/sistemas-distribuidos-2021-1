import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
  public static void main (String args []) {
    try {
      Registry registry = LocateRegistry.createRegistry(10999);
      Implement obj = new Implement();

      registry.rebind("Server", obj);
      System.out.println("Server pronto.");
    } catch(Exception e) {
      System.out.println("Server error: " + e.getMessage());
    }
  }
}
