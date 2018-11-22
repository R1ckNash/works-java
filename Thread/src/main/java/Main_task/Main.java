package Main_task;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class Main {
    public static void main(String[] args) throws Exception {
        Logger logger = Logger.getLogger(Main.class.getName());
        int size1 = 500;
        int size2 = 1000;

        UsualMatrix matrix1 = new UsualMatrix(size1, size2);

        Random rnd = new Random();
        for (int i = 0; i < size1; i++)
            for (int j = 0; j < size2; j++)
                matrix1.setElement(i, j, rnd.nextInt(10));

        UsualMatrix matrix2 = new UsualMatrix(size2, size1);
        for (int i = 0; i < size2; i++)
            for (int j = 0; j < size1; j++)
                matrix2.setElement(i, j, rnd.nextInt(10));


        long start = System.nanoTime();
        ParallelMatrixProduct parallelMatrixProduct = new ParallelMatrixProduct(32);
        UsualMatrix par = parallelMatrixProduct.product(matrix1, matrix2);
        long finish = System.nanoTime();
        float timeConsumedMillis = (float) ((finish - start) * 1.0 / 1000000000);
        logger.log(Level.INFO,"Multithreaded: {0}.",  timeConsumedMillis + "s");


        start = System.nanoTime();
        matrix1.product(matrix2);
        finish = System.nanoTime();
        timeConsumedMillis = (float) ((finish - start) * 1.0 / 1000000000);
        logger.log(Level.INFO,"Single-flow: {0}.",  timeConsumedMillis + "s");
        logger.log(Level.INFO,"Matrix the same - {0}.",matrix1.equals(par));


    }
}

