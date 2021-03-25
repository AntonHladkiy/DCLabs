package a;

public class Main {
    public static void main(String[] args) {
        Thread[] threads = new Thread[12];
        threads[0] = new Thread(new NameReaderRunnable("input.txt", "9797663213"));
        threads[1] = new Thread(new NameReaderRunnable("input.txt", "5477553131"));
        threads[2] = new Thread(new NameReaderRunnable("input.txt", "1214121231"));
        threads[3] = new Thread(new PhoneNumberReaderRunnable("input.txt", "F1"));
        threads[4] = new Thread(new PhoneNumberReaderRunnable("input.txt", "F2"));
        threads[5] = new Thread(new PhoneNumberReaderRunnable("input.txt", "F6"));
        threads[6] = new Thread(new  AddRecordRunnable("input.txt","F7 I O","0011223344"));
        threads[7] = new Thread(new  AddRecordRunnable("input.txt","F8 I O","3344556677"));
        threads[8] = new Thread(new  AddRecordRunnable("input.txt","F9 I O","6677889900"));
        threads[9] = new Thread(new DeleteRecordRunnable("input.txt", "F2 I6 O3"));
        threads[10] = new Thread(new DeleteRecordRunnable("input.txt", "F1 I5 O9"));
        threads[11] = new Thread(new DeleteRecordRunnable("input.txt", "F3 I4 O5"));


        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Manager.deleteEmptyLines("input.txt");
    }
}
