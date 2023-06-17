package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourLogRepository;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
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
    @Autowired
    private at.fhtw.swen2.tutorial.service.mapper.TourLogMapper TourLogMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TourLog> getTourLogList() {
        return TourLogMapper.fromEntity(tourLogRepository.findAll());
    }

    @Override
    public List<TourLog> getTourLogListByTour(Tour tour) {
        if (tour == null || tour.getTourId() == null) {
            return new ArrayList<>();
        }
        return TourLogMapper.fromEntity(tourLogRepository.findByTour(new TourMapper().toEntity(tour)));
    }


    @Override
    public TourLog addNew(TourLog tourLog) {
        if (tourLog == null || tourLog.getTour() == null) {
            return null;
        }
        TourEntity tour = tourRepository.getOne(tourLog.getTour().getTourId()); // 获取对应的TourEntity
        if (tour == null) {
            return null;
        }
        TourLogEntity entity = TourLogMapper.toEntity(tourLog);
        entity.setTour(tour);
        List<TourLogEntity> tourLogs = tour.getTourLogs();
        tourLogs.add(entity);
        tour.setTourLogs(tourLogs);
        tourLogRepository.save(entity);
        tourRepository.save(tour);
        log.info("Add New Tour Log:" + entity.getName() + ", To Tour_ID:" + tour.getTour_id());
        return tourLog;
    }

    @Override
    public Boolean delete(TourLog tourLog) {
        if (tourLog == null) {
            return null;
        }
        try {
            TourLogEntity entity = entityManager.find(TourLogEntity.class, tourLog.getLogId());
            entityManager.remove(entity);
            log.info("Tour: " + tourLog.getName() + " is deleted successfully.");
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
