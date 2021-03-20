import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
  public static void main(String[] args) {
    try {
      ServerSocket socket = new ServerSocket(9999);
      while (true) {
        Socket client = socket.accept();
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());
        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        String num1, sinal, num2 = null;

        do {
          output.writeObject("Digite o primeiro número: ");
          num1 = (String) input.readObject();

          output.writeObject("Digite o sinal: ");
          sinal = (String) input.readObject();

          output.writeObject("Digite o segundo número: ");
          num2 = (String) input.readObject();

          int soma = Integer.parseInt(num1) + Integer.parseInt(num2);
          output.writeObject("Resultado: " + soma + "\n--- FIM ---\n");
        } while (!num1.equals("-1"));

        output.close();
        input.close();
        client.close();
      }
    }
    catch (Exception err) {
      System.err.println(err);
    }
  }
}
