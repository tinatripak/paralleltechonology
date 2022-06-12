public class Result {
    public static double[][] matrix;
    public Result(double[][] matrix) {
        Result.matrix = matrix;
    }
    public double[][] getMatrix() {
        return matrix;
    }

    public void printMatrix(){
        for (double[] doubles : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(doubles[j] + " ");
            }
            System.out.println();
        }
    }
}
