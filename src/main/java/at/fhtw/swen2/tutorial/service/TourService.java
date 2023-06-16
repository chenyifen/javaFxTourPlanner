package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.service.dto.Tour;

import java.util.List;

public interface TourService {

    List<Tour> getTourList();

    Tour addNew(Tour tour);

    Boolean delete(Tour tour);

    Boolean update(Tour tour);

    // erweitern mit parameter create new service

}
