package a;

public class CustomCyclicBarrier {
    private final int initialParties;
    private int partiesAwait;

    public CustomCyclicBarrier(int parties) {
        initialParties = parties;
        partiesAwait = parties;
    }

    public synchronized void await() throws InterruptedException {
        partiesAwait--;
        if (partiesAwait > 0) {
            this.wait();
        }

        //This makes it cyclic
        partiesAwait = initialParties;
        notifyAll();
    }
}
