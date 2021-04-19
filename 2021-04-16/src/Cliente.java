import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Arrays;

public class Cliente {
  public static void main(String[] args) {
    try {
      Socket socketA = new Socket("127.0.0.1", 9998);
      Socket socketB = new Socket("127.0.0.1", 9999);

      ObjectOutputStream outputA = new ObjectOutputStream(socketA.getOutputStream());
      ObjectInputStream inputA = new ObjectInputStream(socketA.getInputStream());
      ObjectOutputStream outputB = new ObjectOutputStream(socketB.getOutputStream());
      ObjectInputStream inputB = new ObjectInputStream(socketB.getInputStream());
      Socket connectedSocket;
      Scanner scanner = new Scanner(System.in);
      String sinal = null;
      Boolean exit = false;
      String menu = "\n 1. Soma (+)\n 2. Subtração (-)\n 3. Multiplicação (*)\n 4. Divisão (/)\n 5. Porcentagem (%)\n 6. Raiz quadrada (√)\n 7. Potenciação (^)\n\nDigite o número referente à operação: ";
      String[] opcoesBasicas = {"1","2","3","4"};
      String[] opcoesAvancadas = {"5","6","7"};

      do {
          System.out.print(menu);
          sinal = scanner.nextLine();

          if (Arrays.asList(opcoesBasicas).contains(sinal)) {
            callServer(sinal, inputA, outputA);
          } else if (Arrays.asList(opcoesAvancadas).contains(sinal)) {
            callServer(sinal, inputB, outputB);
          } else {
            System.out.println("ERRO: \""+sinal+"\""+" não é uma opção válida. Digite o número referente a uma das opções acima.\n-------\n");
          }
      } while (!sinal.equals("sair"));

      outputA.close();
      inputA.close();
      socketA.close();
    }
    catch (Exception err) {
      System.err.println(err);
    }
  }

    public static void callServer(String opcao, ObjectInputStream input, ObjectOutputStream output) {
        Scanner scanner = new Scanner(System.in);
        String num1, num2 = null;
        String[] sinais = {"+","-","*","/","%","√","^"};

        try {
            int index = Integer.parseInt(opcao) - 1;
            String sinal = sinais[index];
            System.out.print("\nOperação escolhida: " + sinal);
            output.writeObject(sinal);

            System.out.print((String) input.readObject());
            num1 = scanner.nextLine();
            output.writeObject(num1);

            if (!sinal.equals("√")) {
              System.out.print((String) input.readObject());
              num2 = scanner.nextLine();
              output.writeObject(num2);
            }

            String resultado = (String) input.readObject();
            System.out.println(resultado);
        } catch (Exception err) {
            System.err.println(err);
        }
    }
}
