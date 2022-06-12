import mpi.*;

import java.util.ArrayList;

public class laba7 {
    final static int MASTER = 0;

    public static void main(String[] args) {
//        MPI.Init(args);
//        CollectiveMultiplication(false, 400);
//        MPI.Finalize();
        int[] matrixSizes = {200, 400, 600, 800, 1000, 1200};
        double[] serialResults = new double[matrixSizes.length];
        for (int i=0; i<matrixSizes.length; i++){
            ArrayList<Double> results = new ArrayList<>();
            for (int j=0; j<5; j++){
                results.add(simpleMultiplication(matrixSizes[i]));
            }
            serialResults[i] = results.stream().mapToDouble(a -> a).sum() / results.size();
        }
        for (double result: serialResults)
            System.out.println(result);
    }

    public static void CollectiveMultiplication(boolean ifPrint, int size){
        double[][] a = new double[size][size];
        double[][] b = new double[size][size];
        double[][] c = new double[size][size];
        double[] buffer_a = new double[size * size];
        double[] buffer_b = new double[size * size];
        double[] buffer_c = new double[size * size];
        int[] _sizes = new int[1];
        int index =0;
        _sizes[0] = size;
        final int rank = MPI.COMM_WORLD.Rank();
        final int num_workers = MPI.COMM_WORLD.Size();
        double start;
        double timeResult;
        if (rank == MASTER) {
            for (int i = 0; i< size; i++){
                for (int j = 0; j< size; j++){
                    a[i][j]= j+1;
                }
            }
            for (int i = 0; i< size; i++){
                for (int j = 0; j< size; j++) {
                    b[i][j] = (i==j) ? 1 : 0;
                }
            }
            for(int i = 0; i< size; i++){
                for (int j = 0; j< size; j++){
                    buffer_a[index] = a[i][j];
                    buffer_b[index] = b[i][j];
                    index++;
                }
            }
            index =0;
        }

        start = System.currentTimeMillis();
        MPI.COMM_WORLD.Bcast(_sizes, 0, 1, MPI.INT, 0);
        double[] _receiveA = new double[(_sizes[0]/ num_workers)*(_sizes[0])];
        double[] _receiveC = new double[(_sizes[0]/ num_workers)*(_sizes[0])];
        int count = _sizes[0]/ num_workers;

        MPI.COMM_WORLD.Scatter(buffer_a, 0, count* _sizes[0], MPI.DOUBLE,
                _receiveA, 0, count* _sizes[0], MPI.DOUBLE, 0);
        MPI.COMM_WORLD.Bcast(buffer_b, 0, _sizes[0]* _sizes[0], MPI.DOUBLE, 0);
        for (int i = 0; i< _sizes[0]/ num_workers; i++){
            for (int j = 0; j< _sizes[0] ; j++ ){
                _receiveC[i* _sizes[0]+j] = 0;
                for (int k = 0; k< _sizes[0] ; k++ )
                    _receiveC[i* _sizes[0]+j] += _receiveA[i* _sizes[0]+k]*buffer_b[k* _sizes[0]+j];

            }
        }
        MPI.COMM_WORLD.Gather(_receiveC, 0, count* _sizes[0],
                MPI.DOUBLE, buffer_c, 0, count* _sizes[0], MPI.DOUBLE, 0);
        timeResult = System.currentTimeMillis() - start;
        if (rank == MASTER) {
            for(int i = 0; i< _sizes[0]; i++){
                for (int j = 0; j< _sizes[0]; j++){
                    c[i][j] = buffer_c[index];
                    index++;
                }
            }
//            if (ifPrint)
//                ifPrint(c, _matrixSize[0]);
            System.out.println(timeResult);
        }
    }

    public static void ifPrint(double[][] c, int matrixSize){
        for (int k = 0; k < matrixSize; k++){
            System.out.println("\n");
            for (int t = 0; t < matrixSize; t++)
                System.out.printf("%6.5f ", c[k][t]);
        }
    }

    public static double simpleMultiplication(int matrixSize){
        double[][] a = new double[matrixSize][matrixSize];
        double[][] b = new double[matrixSize][matrixSize];
        double[][] c = new double[matrixSize][matrixSize];
        long start;
        long timeResult;
        for (int i = 0; i< matrixSize; i++){
            for (int j = 0; j< matrixSize; j++){
                a[i][j]= 10;
            }
        }
        for (int i = 0; i< matrixSize; i++){
            for (int j = 0; j< matrixSize; j++) {
                b[i][j] = (i == j) ? 1 : 0;
            }
        }
        start = System.currentTimeMillis();
        for(int i = 0; i < matrixSize; i++)
        {
            for(int j = 0; j < matrixSize; j++)
            {
                for (int k = 0; k < matrixSize; k++)
                {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        timeResult = System.currentTimeMillis() - start;
        return (double) timeResult;
    }
}