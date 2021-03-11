package c;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveCityRunnable implements Runnable{
    private final TripGraph tripGraph;
    private final int city;

    @Override
    public void run() {
        removeCity(city);
    }

    private void removeCity(int city) {
        try {
            tripGraph.getReadWriteLock().writeLock();
            if (tripGraph.getCities().contains(city)){
                tripGraph.getCities().remove((Object) city);
                tripGraph.getTrips().removeIf(trip -> trip.getA() == city || trip.getB() == city);
            }
            tripGraph.getReadWriteLock().writeUnlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
