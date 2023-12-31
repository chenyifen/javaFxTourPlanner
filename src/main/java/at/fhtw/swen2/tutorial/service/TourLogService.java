package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;

import java.util.List;

public interface TourLogService {

    List<TourLog> getTourLogList();

    List<TourLog> getTourLogListByTour(Tour tour);

    TourLog addNew(Tour tour, TourLog tourLog);

    Boolean delete(TourLog tourLog);

    Boolean update(TourLog tourLog);


}
