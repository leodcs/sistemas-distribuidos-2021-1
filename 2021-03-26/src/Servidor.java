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
        String num1, sinal, num2, erro = null;
        int teste, resultado = 0;
        boolean sucesso = true;

        do {
          output.writeObject("Digite o primeiro número: ");
          num1 = (String) input.readObject();
          try {
              teste = Integer.parseInt(num1);
          } catch (NumberFormatException e) {
              sucesso = false;
              erro = "\"" + num1 + "\"" + " não é um numero";
          }

          output.writeObject("Digite o sinal: ");
          sinal = (String) input.readObject();

          output.writeObject("Digite o segundo número: ");
          num2 = (String) input.readObject();
          try {
              teste = Integer.parseInt(num2);
          } catch (NumberFormatException e) {
              sucesso = false;
              erro = "\"" + num2 + "\"" + " não é um numero";
          }

          if (sucesso) {
              switch(sinal) {
              case "+":
                  resultado = Integer.parseInt(num1) + Integer.parseInt(num2);
                  break;
              case "-":
                  resultado = Integer.parseInt(num1) - Integer.parseInt(num2);
                  break;
              case "/":
                  resultado = Integer.parseInt(num1) / Integer.parseInt(num2);
                  break;
              case "*":
                  resultado = Integer.parseInt(num1) * Integer.parseInt(num2);
                  break;
              default:
                  sucesso = false;
                  erro = "\"" + sinal + "\"" + " não é um operador valido!";
              }
          }

          if (sucesso) {
              output.writeObject("Resultado: " + resultado + "\n--- FIM ---\n");
          } else {
            output.writeObject(erro);
            sucesso = true;
            erro = null;
          }
        } while (!num1.equals("bye") || !sinal.equals("bye") || !num2.equals("bye"));

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
