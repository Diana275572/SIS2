import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is running...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

                    Object obj = in.readObject();

                    if (obj instanceof String && obj.equals("Q")) {
                        System.out.println("Closing server...");
                        break;
                    }

                    if (obj instanceof GeometricObject) {
                        GeometricObject shape = (GeometricObject) obj;
                        double area = shape.area();
                        out.writeObject("Area: " + area);
                    }
                } catch (EOFException e) {
                    System.out.println("Client disconnected.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
