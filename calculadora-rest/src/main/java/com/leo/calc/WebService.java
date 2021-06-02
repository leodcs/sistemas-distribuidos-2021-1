package com.leo.calc;

import static spark.Spark.*;
import org.json.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class WebService {
  public static void main(String[] args) {
    // String[] sinais = {"+","-","*","/","%","√","^"};
    HashMap<String,String> endpoints = new HashMap<String,String>();

    endpoints.put("/soma", "+");
    endpoints.put("/subtracao", "-");
    endpoints.put("/multiplicacao", "*");
    endpoints.put("/divisao", "/");

    endpoints.forEach((endpoint, sinal) -> {
        post(endpoint, (request, response) -> {
            try {
              JSONObject obj = new JSONObject(request.body());
              String x = obj.get("x").toString();
              String y = obj.get("y").toString();

              return chamaOperacao(sinal, x, y, "serverBasico");
            } catch (Exception e) {
              System.out.println(e);
              return e;
            }
          });
      });
  }

  private static String chamaOperacao(String sinal, String x, String y, String tipoServer) {
    try {
      Socket socketA = new Socket("127.0.0.1", 9998);
      // Socket socketB = new Socket("127.0.0.1", 9999);
      ObjectOutputStream outputA = new ObjectOutputStream(socketA.getOutputStream());
      ObjectInputStream inputA = new ObjectInputStream(socketA.getInputStream());
      // ObjectOutputStream outputB = new ObjectOutputStream(socketB.getOutputStream());
      // ObjectInputStream inputB = new ObjectInputStream(socketB.getInputStream());
      String resultado;
      resultado = "";

      if (tipoServer.equals("serverBasico")) {
        resultado = callServer(sinal, x, y, inputA, outputA);
      } else {
        // resultado = callServer(sinal, x, y, inputB, outputB);
      };

      return resultado;
    } catch (Exception err) {
      System.err.println(err);

      return "";
    }
  }

  private static String callServer(String sinal, String x, String y, ObjectInputStream input, ObjectOutputStream output) {
    try {
      output.writeObject(sinal);
      output.writeObject(x);
      output.writeObject(y);

      // if (!sinal.equals("√")) {
      //   System.out.print((String) input.readObject());
      //   num2 = scanner.nextLine();
      //   output.writeObject(num2);
      // }

      String resultado = (String) input.readObject();

      return resultado;
    } catch (Exception err) {
      err.printStackTrace();

      return "";
    }
  }
}
