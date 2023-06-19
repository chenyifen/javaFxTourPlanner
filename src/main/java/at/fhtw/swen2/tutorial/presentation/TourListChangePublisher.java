package at.fhtw.swen2.tutorial.presentation;

import at.fhtw.swen2.tutorial.service.dto.TourLog;

import java.util.ArrayList;
import java.util.List;

public class TourListChangePublisher {

    private static final TourListChangePublisher INSTANCE = new TourListChangePublisher();

    private final List<TourListChangeListener> listeners = new ArrayList<>();

    private TourListChangePublisher() {
    }

    public static TourListChangePublisher getInstance() {
        return INSTANCE;
    }

    public void register(TourListChangeListener listener) {
        listeners.add(listener);
    }

    public void unregister(TourListChangeListener listener) {
        listeners.remove(listener);
    }

    public void notifyChange() {
        listeners.forEach(listener -> listener.newTourAdded());
    }

}
