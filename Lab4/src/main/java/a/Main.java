package a;

public class Main {
    public static void main(String[] args) {
        Thread[] threads = new Thread[12];
        threads[0] = new Thread(new NameReaderRunnable("input.txt", "979766"));
        threads[1] = new Thread(new NameReaderRunnable("input.txt", "878666"));
        threads[2] = new Thread(new NameReaderRunnable("input.txt", "456789"));
        threads[3] = new Thread(new PhoneNumberReaderRunnable("input.txt", "F1"));
        threads[4] = new Thread(new PhoneNumberReaderRunnable("input.txt", "F2"));
        threads[5] = new Thread(new PhoneNumberReaderRunnable("input.txt", "F6"));
        threads[6] = new Thread(new  AddRecordRunnable("input.txt","F7 I O","001122"));
        threads[7] = new Thread(new  AddRecordRunnable("input.txt","F8 I O","334455"));
        threads[8] = new Thread(new  AddRecordRunnable("input.txt","F9 I O","667788"));
        threads[9] = new Thread(new DeleteRecordRunnable("input.txt", "F2 I O"));
        threads[10] = new Thread(new DeleteRecordRunnable("input.txt", "F1 I O"));
        threads[11] = new Thread(new DeleteRecordRunnable("input.txt", "F3 I O"));


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
