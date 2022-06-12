public class MatrixGenerator {
    public static double[][] identity(int matrixSize){
        double[][] result = new double[matrixSize][matrixSize];

        for (int i=0; i<matrixSize; i++){
            for (int j=0; j<matrixSize; j++){
                if (i==j)
                    result[i][j]=1;
                else result[i][j]=0;
            }
        }
        return result;
    }
    public static double[][] lineElement(int matrixSize){
        double[][] result = new double[matrixSize][matrixSize];

        for (int i=0; i<matrixSize; i++){
            for (int j=0; j<matrixSize; j++){
                result[i][j] = (double) i+1;
            }
        }
        return result;
    }
}
