package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.mapper.TourMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Slf4j
public class
TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TourMapper TourMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tour> getTourList() {
        return TourMapper.fromEntity(tourRepository.findAll());
    }

    @Override
    public Tour addNew(Tour tour) {
        if (tour == null) {
            return null;
        }
        TourEntity entity = tourRepository.save(TourMapper.toEntity(tour));
        log.info("Save Tour Success. Tour.name = " + tour.getName());
        return TourMapper.fromEntity(entity);
    }


    @Override
    public Boolean delete(Tour tour) {
        if (tour == null) {
            return null;
        }

        try {
            TourEntity entity = entityManager.find(TourEntity.class, tour.getTourId());
            entityManager.remove(entity);
            log.info("Tour: " + tour.getName() + " is deleted successfully.");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean update(Tour tour) {
        if (tour == null) {
            return null;
        }
        try {
            log.info("tour.id = " + tour.getTourId());
            TourEntity entity = entityManager.find(TourEntity.class, tour.getTourId());
            entity.setTour_id(tour.getTourId());
            entity.setName(tour.getName());
            entity.setDescription(tour.getDescription());
            entity.setEstimatedTime(tour.getEstimatedTime());
            entity.setFromLocation(tour.getFrom());
            entity.setToLocation(tour.getTo());
            entity.setTourDistance(tour.getTourDistance());
            entity.setRouteInformation(tour.getRouteInformation());
            entity.setTransportType(entity.getTransportType());
            log.info("going to merge : " + entity.getName());
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
