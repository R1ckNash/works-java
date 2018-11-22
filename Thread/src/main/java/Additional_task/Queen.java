package Additional_task;


public class Queen {
    private class CombinationCalc extends Thread{
        private int firstRow, lastRow;
        private int sizeBoard;
        private int combNum;
        private int board[][];

        public CombinationCalc(int N, int first, int second){
            sizeBoard = N;
            firstRow = first;
            lastRow = second;
            combNum = 0;
            board = new int [N][N];
        }

        private int getCombNum(){
            return combNum;
        }

        public boolean tryQueen(int a, int b){
            for (int i = 0; i < a; ++i)
                if (board[i][b] == 1)
                    return false;
            for (int i = 1; i <= a && b - i >= 0; ++i)
                if (board[a - i][b - i] == 1)
                    return false;

            for (int i = 1; i <= a && b + i < sizeBoard; i++)
                if (board[a - i][b + i] == 1)
                    return false;

            return true;
        }

        public void setQueen(int a) throws InterruptedException {
            int j = 0;
            int lastj = 0;
            if(a == sizeBoard){
                combNum++;
            }
            if(a == 0){
                j = firstRow;
                lastj = lastRow;
            }else {
                j = 0;
                lastj = sizeBoard;
            }

            for (; j < lastj; j++) {
                if (tryQueen(a, j)) {
                    board[a][j] = 1;
                    setQueen(a + 1);
                    board[a][j] = 0;
                }
            }
        }

        @Override
        public void run(){
            try{
                setQueen(0);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public int calcQueenNum(int N, int threadNum){
        int num = 0;
        if(threadNum > N){
            threadNum = N;
        }
        int count = N/threadNum;
        int additional = N % threadNum;

        CombinationCalc [] threads = new CombinationCalc[threadNum];
        int start = 0;
        for(int i = 0; i < threadNum; i++){
            int cnt = ((i == 0) ? count + additional : count);
            threads[i] = new CombinationCalc(N, start, start + cnt);
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

        for(CombinationCalc thread : threads){
            num += thread.getCombNum();
        }
        return num;
    }
}