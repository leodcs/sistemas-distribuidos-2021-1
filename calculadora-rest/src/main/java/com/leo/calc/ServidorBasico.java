package com.leo.calc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorBasico {
  public static void main(String[] args) {
    try {
      ServerSocket socket = new ServerSocket(9998);

      while (true) {
        Socket client = socket.accept();
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());
        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        String num1, sinal, num2, erro;
        num1 = sinal = num2 = erro = "";
        double double1, double2, resultado = 0;
        boolean sucesso = true;

        sinal = (String) input.readObject();
        num1 = (String) input.readObject();

        double1 = 0;
        try {
          double1 = Double.parseDouble(num1);
        } catch (NumberFormatException e) {
          sucesso = false;
          erro = "\"" + num1 + "\"" + " não é um numero";
        }

        num2 = (String) input.readObject();

        double2 = 0;
        try {
          double2 = Double.parseDouble(num2);
        } catch (NumberFormatException e) {
          sucesso = false;
          erro = "\"" + num2 + "\"" + " não é um numero";
        }

        if (sucesso) {
          switch(sinal) {
          case "+":
            resultado = double1 + double2;
            break;
          case "-":
            resultado = double1 - double2;
            break;
          case "/":
            if (double2 != 0.0) {
              resultado = double1 / double2;
            } else {
              sucesso = false;
              erro = "ERRO: divisão por zero!";
            }
            break;
          case "*":
            resultado = double1 * double2;
            break;
          default:
            sucesso = false;
            erro = "\"" + sinal + "\"" + " não é um operador valido!";
          }
        }

        if (sucesso) {
          output.writeObject("Resultado: " + resultado);
        } else {
          output.writeObject(erro);
          sucesso = true;
          erro = null;
        }
      }
    }
    catch (Exception err) {
      err.printStackTrace();
    }
  }
}
