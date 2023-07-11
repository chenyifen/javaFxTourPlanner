package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourLogRepository;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.mapper.TourLogMapper;
import at.fhtw.swen2.tutorial.service.mapper.TourMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TourLogServiceImpl implements TourLogService {

    @Autowired
    private TourLogRepository tourLogRepository;
    @Autowired
    private TourRepository tourRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TourLog> getTourLogList() {
        return new TourLogMapper().fromEntity(tourLogRepository.findAll());
    }

    @Override
    public List<TourLog> getTourLogListByTour(Tour tour) {
        if (tour == null || tour.getTourId() == null) {
            return new ArrayList<>();
        }
        return new TourLogMapper().fromEntity(tourLogRepository.findByTour(new TourMapper().toEntity(tour)));
    }


    @Transactional
    @Override
    public TourLog addNew(Tour tour, TourLog tourLog) {
        if (tour == null || tourLog == null || tourLog.getTour() == null) {
            log.error("Add tour log fail. tour  or tour log is null");
            return null;
        }
        TourLogEntity tourLogEntity = new TourLogMapper().toEntity(tourLog);
        TourEntity tourEntity = new TourMapper().toEntity(tour);
        log.info("Map to Tour: " +tour.toString());
        tourLogEntity.setTour(tourEntity);
        List<TourLogEntity> tourLogs = tour.getTourLogs();
        if(tourLog  == null){
            tourLogs = new ArrayList<>();
        }
        tourLogs.add(tourLogEntity);
        tour.setTourLogs(tourLogs);
        tourLogRepository.save(tourLogEntity);

        log.info("Add New Tour Log:" + tourLog.getName() + ", To Tour_ID:" + tour.getTourId());
        return tourLog;
    }

    @Override
    public Boolean delete(TourLog tourLog) {
        if (tourLog == null) {
            return null;
        }
        try {
            TourLogEntity entity = entityManager.find(TourLogEntity.class, tourLog.getLogId());
//            entityManager.remove(entity);
            tourLogRepository.deleteTourLogById(tourLog.getLogId().toString());
            log.info("Tour: " + tourLog.getLogId().toString() +"  , tour Name:"+ tourLog.getName());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean update(TourLog tourLog) {
        if (tourLog == null) {
            return null;
        }
        try {
            TourEntity tour = tourRepository.getOne(tourLog.getTour().getTourId()); // 获取对应的TourEntity
            if (tour == null) {
                return null;
            }
            TourLogEntity entity = entityManager.find(TourLogEntity.class, tourLog.getLogId());
            entity.setName(tourLog.getName());
            entity.setComment(tourLog.getComment());
            entity.setDifficulty(tourLog.getDifficulty());
            entity.setTotalTime(tourLog.getTotalTime());
            entity.setRating(tourLog.getRating());
            entity.setTour(tour);
            log.info("going to update log : " + entity.getName());
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
