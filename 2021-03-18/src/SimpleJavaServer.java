import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleJavaServer {
	public static void main(String[] args) 	{
		try {
			ServerSocket s = new ServerSocket(9999);
			String str;
			while (true) {
				Socket c = s.accept();
				ObjectInputStream input = new ObjectInputStream(c.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(c.getOutputStream());
				String resposta = null;

				do {
					output.writeObject("Menu:\n1 - Calabresa\n2 - Baiana\n3 - Milho\n4 - Napolitana\n");

					resposta = (String) input.readObject();
					String opcao = "";
					switch(resposta) {
						case "1":
							opcao = "Calabresa";
							break;
						case "2":
							opcao = "Baiana";
							break;
						case "3":
							opcao = "Milho";
							break;
						case "4":
							opcao = "Napolitana";
							break;
					}

					if (!opcao.isBlank()) {
						output.writeObject("Você escolheu: " + opcao + "\n---\n");
					} else if (!resposta.equals("bye")) {
						output.writeObject("Você escolheu uma opcao invalida!\n---\n");
					}
				} while ( !resposta.equals("bye") );

				output.close();
				input.close();
				c.close();
			}
		}
		catch (Exception err){
			System.err.println(err);
		}
	}
}
