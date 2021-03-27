import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
  public static void main(String[] args) {
    try {
      Socket socket = new Socket("127.0.0.1", 9999);
      ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
      Scanner scanner = new Scanner(System.in);
      String num1, sinal, num2 = null;

      do {
        System.out.print((String) input.readObject());
        num1 = scanner.nextLine();
        output.writeObject(num1);

        System.out.print((String) input.readObject());
        sinal = scanner.nextLine();
        output.writeObject(sinal);

        System.out.print((String) input.readObject());
        num2 = scanner.nextLine();
        output.writeObject(num2);

        String resultado = (String) input.readObject();
        System.out.println(resultado);
      } while (!num1.equals("bye") || !sinal.equals("bye") || !num2.equals("bye"));

      output.close();
      input.close();
      socket.close();
    }
    catch (Exception err) {
      System.err.println(err);
    }
  }
}
