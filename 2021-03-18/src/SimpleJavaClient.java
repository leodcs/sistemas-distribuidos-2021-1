import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SimpleJavaClient {

    public static void main(String[] args) 	{
        try {
            Socket s = new Socket("127.0.0.1", 9999);
            InputStream i = s.getInputStream();
            ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(s.getInputStream());
            Scanner scanner = new Scanner(System.in);
            String str;

            do {
                String menu = (String) input.readObject();
                System.out.println(menu);

                str = scanner.nextLine();
                output.writeObject(str);

                String resposta = (String) input.readObject();
                System.out.println(resposta);
            } while ( !str.equals("bye") );

            output.close();
            input.close();
            s.close();
        }
        catch (Exception err) {
            System.err.println(err);
        }
    }

}
