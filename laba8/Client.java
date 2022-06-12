import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("The client is connected to the server");
            System.out.println("Where is the data? Server(s) or client(c)?");

            while (!socket.isOutputShutdown()) {

                if (bufferedReader.ready()) {

                    String clientCommand = bufferedReader.readLine();
                    int matrixSize =  1400;
                    int numOfThreads =  8;

                    if (clientCommand.equals("s")) {
                        long startTime = System.nanoTime();

                        writerObjectString(output, clientCommand);
                        writerObjectInt(output,matrixSize);
                        writerObjectInt(output,numOfThreads);

                        processServerAnswer(startTime, input);
                    }

                    if (clientCommand.equals("c")) {
                        double[][] matrixA = MatrixGenerator.lineElement(matrixSize);
                        double[][] matrixB = MatrixGenerator.identity(matrixSize);

                        long startTime = System.nanoTime();

                        writerObjectString(output, clientCommand);
                        writerObjectInt(output,matrixSize);
                        writerObjectInt(output,numOfThreads);

                        writerObjectMatrix(output, matrixA);
                        writerObjectMatrix(output, matrixB);

                        processServerAnswer(startTime, input);
                    }

                    System.out.println();
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void processServerAnswer(long startTime, ObjectInputStream in) throws IOException, ClassNotFoundException {
        System.out.println("Waiting for the result from the server");
        double[][] matrix = (double[][]) in.readObject();

        long endTime = System.nanoTime();
        System.out.println("The result:");
//        Result result = new Result(matrix);
//        result.printMatrix();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Time: " + duration + " ms");
    }
    private static void writerObjectString(ObjectOutputStream out, String s) throws IOException {
        out.writeObject(s);
        out.flush();
    }
    private static void writerObjectInt(ObjectOutputStream out, Integer i) throws IOException {
        out.writeObject(i);
        out.flush();
    }
    private static void writerObjectMatrix(ObjectOutputStream out, double[][] matrix) throws IOException {
        out.writeObject(matrix);
        out.flush();
    }
}
