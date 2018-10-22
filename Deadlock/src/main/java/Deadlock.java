public class Deadlock {


    static class Friend {

        private final String name;

        private Friend(String name) {
            this.name = name;
        }

        private String getName() {
            return this.name;
        }

        private synchronized void bow(Friend bower) {
            System.out.format("%s: %s" + "  has bowed to me!%n", this.name, bower.getName());
            bower.bowBack(this);
        }

        private synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s" + " has bowed back to me!%n", this.name, bower.getName());
        }
    }

    public static void main(String[] args) {

        final Friend fiend1 = new Friend("Marat");
        final Friend friend2 = new Friend("Dima");

        new Thread(new Runnable() {

            public void run() {
                fiend1.bow(friend2);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                friend2.bow(fiend1);
            }
        }).start();
    }
}