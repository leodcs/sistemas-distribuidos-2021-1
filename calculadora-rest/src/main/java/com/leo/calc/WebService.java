package com.leo.calc;

import static spark.Spark.*;
import org.json.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public class WebService {
  public static void main(String[] args) {
    HashMap<String,String> endpoints = new HashMap<String,String>();

    endpoints.put("/soma", "+");
    endpoints.put("/subtracao", "-");
    endpoints.put("/multiplicacao", "*");
    endpoints.put("/divisao", "/");
    endpoints.put("/porcentagem", "%");
    endpoints.put("/raiz_quadrada", "√");
    endpoints.put("/potencia", "^");

    endpoints.forEach((endpoint, sinal) -> {
        post(endpoint, (request, response) -> {
            try {
              JSONObject obj = new JSONObject(request.body());
              String x = obj.get("x").toString();
              String y = "";

              if (!sinal.equals("√")) {
                y = obj.get("y").toString();
              }

              return chamaOperacao(sinal, x, y);
            } catch (Exception e) {
              System.out.println(e);
              return e;
            }
          });
      });
  }

  private static String chamaOperacao(String sinal, String x, String y) {
    try {
      Socket socketA = new Socket("127.0.0.1", 9998);
      Socket socketB = new Socket("127.0.0.1", 9999);
      ObjectOutputStream outputA = new ObjectOutputStream(socketA.getOutputStream());
      ObjectInputStream inputA = new ObjectInputStream(socketA.getInputStream());
      ObjectOutputStream outputB = new ObjectOutputStream(socketB.getOutputStream());
      ObjectInputStream inputB = new ObjectInputStream(socketB.getInputStream());
      List<String> sinais = Arrays.asList("+","-","*","/","%","√","^");
      String resultado;
      resultado = "";

      if (sinais.indexOf(sinal) <= 3) { // ServidorBasico
        resultado = callServer(sinal, x, y, inputA, outputA);
      } else { // ServidorEspecialista
        resultado = callServer(sinal, x, y, inputB, outputB);
      };

      return resultado;
    } catch (Exception err) {
      err.printStackTrace();

      return "";
    }
  }

  private static String callServer(String sinal, String x, String y, ObjectInputStream input, ObjectOutputStream output) {
    try {
      output.writeObject(sinal);
      output.writeObject(x);
      output.writeObject(y);

      String resultado = (String) input.readObject();

      return resultado;
    } catch (Exception err) {
      err.printStackTrace();

      return "";
    }
  }
}
