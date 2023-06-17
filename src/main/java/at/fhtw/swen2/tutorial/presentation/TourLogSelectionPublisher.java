package at.fhtw.swen2.tutorial.presentation;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;

import java.util.ArrayList;
import java.util.List;

public class TourLogSelectionPublisher {

    private static final TourLogSelectionPublisher INSTANCE = new TourLogSelectionPublisher();

    private final List<TourLogSelectionListener> listeners = new ArrayList<>();

    private TourLogSelectionPublisher() {
    }

    public static TourLogSelectionPublisher getInstance() {
        return INSTANCE;
    }

    public void register(TourLogSelectionListener listener) {
        listeners.add(listener);
    }

    public void unregister(TourLogSelectionListener listener) {
        listeners.remove(listener);
    }

    public void notify(TourLog tourLog) {
        System.out.println("www notify tour Log. " + "id : " +tourLog.getLogId()+tourLog.toString()); //TODO delete
        listeners.forEach(listener -> listener.tourLogSelected(tourLog));
    }

}
