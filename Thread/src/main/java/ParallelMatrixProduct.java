public class ParallelMatrixProduct {

    private class Multiply extends Thread {
        private int firstRow, lastRow, sizeRow;
        private UsualMatrix first, second, result;

        Multiply(int firstIndexRow, int secondIndexRow, UsualMatrix first, UsualMatrix second, UsualMatrix result) {
            this.firstRow = firstIndexRow;
            this.lastRow = secondIndexRow;
            this.first = first;
            this.second = second;
            this.result = result;
            this.sizeRow = second.getSizeRow();
        }

        private int calcSingleValue(int row, int column) {
            int sum = 0;
            for (int i = 0; i < sizeRow; i++)
                sum += first.getElement(row, i) * second.getElement(i, column);
            return sum;
        }

        @Override
        public void run() {
            int sizeColumn = result.getSizeColumn();
            for (int row = firstRow; row <= lastRow; row++)
                for (int col = 0; col < sizeColumn; col++)
                    result.setElement(row, col, calcSingleValue(row, col));
        }
    }


    private int numberThreads;

    ParallelMatrixProduct(int numberOfThreads) {
        numberThreads = numberOfThreads;
    }


    public UsualMatrix product(final UsualMatrix first, final UsualMatrix second) {
        int row = first.getSizeRow();
        int column = second.getSizeColumn();
        UsualMatrix result = new UsualMatrix(row, column);
        if (numberThreads > row) {
            numberThreads = row;
        }

        int count = row / numberThreads;
        int additional = row % numberThreads;

        Thread[] threads = new Thread[numberThreads];
        int start = 0;
        for (int i = 0; i < numberThreads; i++) {
            int cnt = ((i == 0) ? count + additional : count);
            threads[i] = new Multiply(start, start + cnt - 1, first, second, result);
            start += cnt;
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        return result;
    }
}
