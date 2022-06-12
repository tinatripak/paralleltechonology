
public class BlockStripedMultiplication {
    private final double[][] matrixA;
    private final double[][] matrixB;
    private final int numOfThreads;

    public BlockStripedMultiplication(double[][] matrixA, double[][] matrixB, int numOfThreads){
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.numOfThreads = numOfThreads;
    }

    public Result multiplyMatrixParallelNThreads(){
        int rows = matrixA.length;
        int columns = matrixB[0].length;
        double[][] resultMatrix = new double[rows][columns];
        Thread[] threads = new Thread[numOfThreads];
        int rowsForOneThread = rows / numOfThreads;
        int firstRowForThread = 0;

        long startTime = System.nanoTime();
        for (int i=0; i<numOfThreads; i++){
            int lastRowForThread = firstRowForThread + rowsForOneThread;
            if (lastRowForThread>rows){
                lastRowForThread = rows;
            }
            threads[i] = new Thread(new BlockStripedThreadN(matrixA, matrixB, resultMatrix, firstRowForThread, lastRowForThread));
            threads[i].start();
            firstRowForThread = lastRowForThread;
        }
        try {
            for (Thread thread : threads)
                thread.join();
        } catch (InterruptedException ignored) {}
        return new Result(resultMatrix);
    }
}
