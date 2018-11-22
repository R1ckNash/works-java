package Additional_task;


public class Main {
    public static void main(String[] args) {
        Queen queen = new Queen();
        long start = System.nanoTime();
        System.out.println(queen.calcQueenNum(10,5));
        long finish = System.nanoTime();
        float timeConsumedMillis = (float) ((finish - start) * 1.0 / 1000000000);
        System.out.println("Multithreaded: " + timeConsumedMillis + "s");

        System.out.println(queen.calcQueenNum(10,1));
        finish = System.nanoTime();
        timeConsumedMillis = (float) ((finish - start) * 1.0 / 1000000000);
        System.out.println("One Thread: " + timeConsumedMillis + "s");
    }
}