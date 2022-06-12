import java.io.*;
import java.net.*;

public class Server {

    private static ServerSocket server;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void main(String[] args) throws IOException {

        try {
            server = new ServerSocket(5000);
            System.out.print("The server is running");

            try (Socket client = server.accept()) {
                System.out.print("\nConnecting is done");
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());

                while (client.isConnected()) {
                    System.out.println("\nThe server are waiting for the data of client");
                    String side = (String) in.readObject();
                    int matrixSize = (int) in.readObject();
                    int numOfThreads = (int) in.readObject();
                    processClientRequest(side, matrixSize, numOfThreads);
                    out.flush();
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("\nShutdown");
            server.close();
        }
    }

    private static void processClientRequest(String side, int matrixSize, int numOfThreads) throws IOException, ClassNotFoundException {
        if (side.equals("s")) {
            System.out.println("Calculation started (on the server)");

            double[][] matrixA = MatrixGenerator.lineElement(matrixSize);
            double[][] matrixB = MatrixGenerator.identity(matrixSize);
            BlockStripedMultiplication multiplication = new BlockStripedMultiplication(matrixA, matrixB, numOfThreads);
            Result result = multiplication.multiplyMatrixParallelNThreads();
            System.out.println("Calculation stopped (on the server)");
            out.writeObject(result.getMatrix());
        }

        if (side.equals("c")) {
            System.out.println("Calculation started (on the client)");
            double[][] matrixA = (double[][]) in.readObject();
            double[][] matrixB = (double[][]) in.readObject();
            BlockStripedMultiplication multiplication = new BlockStripedMultiplication(matrixA, matrixB, numOfThreads);
            Result result = multiplication.multiplyMatrixParallelNThreads();
            System.out.println("Calculation started (on the client)");
            out.writeObject(result.getMatrix());
        }
    }
}