package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourLogRepository;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.mapper.TourMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TourLogServiceImpl implements TourLogService {

    @Autowired
    private TourLogRepository tourLogRepository;
    @Autowired
    private at.fhtw.swen2.tutorial.service.mapper.TourLogMapper TourLogMapper;

    @Override
    public List<TourLog> getTourLogList() {
        return TourLogMapper.fromEntity(tourLogRepository.findAll());
    }

    @Override
    public List<TourLog> getTourLogListByTour(Tour tour) {
        System.out.println("www getTourLogListByTour: tour="+tour.toString());
        if(tour == null || tour.getId() == null){
            System.out.println("www return empty List");
            return new ArrayList<>();
        }
        return TourLogMapper.fromEntity(tourLogRepository.findByTour(new TourMapper().toEntity(tour)));
    }

    @Override
    public TourLog addNew(TourLog tourLog) {
        if (tourLog == null || tourLog.getId() == null) {
            return null;
        }
        TourLogEntity entity = tourLogRepository.save(TourLogMapper.toEntity(tourLog));
        return TourLogMapper.fromEntity(entity);
    }

    @Override
    public Boolean delete(TourLog tourLog) {
        if (tourLog == null) {
            return null;
        }
        TourLogEntity entity = TourLogMapper.toEntity(tourLog);
        try {
            tourLogRepository.delete(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
