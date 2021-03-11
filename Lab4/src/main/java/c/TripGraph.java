package c;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TripGraph {

    private final CustomReadWriteLock readWriteLock = new CustomReadWriteLock();

    private final List<Integer> cities = new ArrayList<>();
    private final List<Trip> trips = new ArrayList<>();
}
