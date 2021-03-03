package a;

public class Main {
    public static void main(String[] args) {
        BeeRunnable.setForestTasksManager(new ForestTasksManager(100,100));
        int threadsNumber = 3;
        Thread[] threads = new Thread[threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            threads[i] = new Thread(new BeeRunnable());
            threads[i].start();
        }
        for (int i = 0; i < threadsNumber; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("X: " + BeeRunnable.getBearsX() + " Y: " + BeeRunnable.getBearsY());
    }
}
