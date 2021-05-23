import java.rmi.*;
import java.util.Scanner;
import java.util.Arrays;

public class Client {
  public static void main(String args[]) {
    try {
      Interface obj = (Interface) Naming.lookup("rmi://localhost:10999/Server");
      String sinal, x, y = null;
      Scanner scanner = new Scanner(System.in);
      String[] opcoes = {"+", "-", "*", "/"};
      String menu = "\n -> Soma (+)\n -> Subtração (-)\n -> Multiplicação (*)\n -> Divisão (/)\n\nDigite o sinal referente à operação: ";

      while (true) {
        System.out.print("Digite o primeiro número: ");
        x = scanner.nextLine();

        System.out.print(menu);
        sinal = scanner.nextLine();

        if (Arrays.asList(opcoes).contains(sinal)) {
          System.out.print("\nDigite o segundo número: ");
          y = scanner.nextLine();

          System.out.println("---------");
          System.out.println(x + " " + sinal + " " + y + " = " + obj.chamaOperacao(sinal, x, y) + "\n");
        } else {
          System.out.println("ERRO: \""+sinal+"\""+" não é uma opção válida. Digite o número referente a uma das opções acima.\n-------\n");
        }
      }

    } catch(Exception e) {
      System.out.println("Client error: " + e.getMessage());
    }

    System.exit(0);
  }
}
