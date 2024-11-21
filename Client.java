import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("Enter shape type (Circle/Rectangle) or 'Q' to quit:");
                String shapeType = scanner.nextLine();

                if (shapeType.equalsIgnoreCase("Q")) {
                    out.writeObject("Q");
                    System.out.println("Closing client...");
                    break;
                }

                GeometricObject shape = null;

                if (shapeType.equalsIgnoreCase("Circle")) {
                    System.out.print("Enter radius: ");
                    double radius = scanner.nextDouble();
                    scanner.nextLine(); 
                    shape = new Circle(radius);
                } else if (shapeType.equalsIgnoreCase("Rectangle")) {
                    System.out.print("Enter length: ");
                    double length = scanner.nextDouble();
                    System.out.print("Enter width: ");
                    double width = scanner.nextDouble();
                    scanner.nextLine(); 
                    shape = new Rectangle(length, width);
                } else {
                    System.out.println("Invalid shape type.");
                    continue;
                }

                out.writeObject(shape);

                String response = (String) in.readObject();
                System.out.println("Server Response: " + response);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
