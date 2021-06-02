package com.leo.calc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorEspecialista {
  public static void main(String[] args) {
    try {
      ServerSocket socket = new ServerSocket(9999);

      while (true) {
        Socket client = socket.accept();
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());
        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        String num1, sinal, num2, erro = null;
        double double1, double2, resultado = 0;
        boolean sucesso = true;

        do {
          sinal = (String) input.readObject();

          output.writeObject("\nDigite o primeiro número: ");
          num1 = (String) input.readObject();

          double1 = 0;
          try {
            double1 = Double.parseDouble(num1);
          } catch (NumberFormatException e) {
            sucesso = false;
            erro = "\"" + num1 + "\"" + " não é um número";
          }

          double2 = 0;
          if (sinal.equals("√")) {
            num2 = "";
          } else {
            output.writeObject("Digite o segundo número: ");
            num2 = (String) input.readObject();
            try {
              double2 = Double.parseDouble(num2);
            } catch (NumberFormatException e) {
              sucesso = false;
              erro = "\"" + num2 + "\"" + " não é um número";
            }
          }

          if (sucesso) {
            switch(sinal) {
            case "%":
              resultado = (double1 / 100) * double2;
              break;
            case "√":
              resultado = Math.sqrt(double1);
              break;
            case "^":
              resultado = Math.pow(double1, double2);
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
        } while (!num1.equals("exit"));

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
