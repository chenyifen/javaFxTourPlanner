package at.fhtw.swen2.tutorial.presentation;

import at.fhtw.swen2.tutorial.service.dto.Tour;

import java.util.ArrayList;
import java.util.List;

public class TourSelectionPublisher {

    private static final TourSelectionPublisher INSTANCE = new TourSelectionPublisher();

    private final List<TourSelectionListener> listeners = new ArrayList<>();

    private TourSelectionPublisher() {
    }

    public static TourSelectionPublisher getInstance() {
        return INSTANCE;
    }

    public void register(TourSelectionListener listener) {
        listeners.add(listener);
    }

    public void unregister(TourSelectionListener listener) {
        listeners.remove(listener);
    }

    public void notify(Tour tour) {
        System.out.println("www notify tour "); //TODO delete
        listeners.forEach(listener -> listener.tourSelected(tour));
    }

}
