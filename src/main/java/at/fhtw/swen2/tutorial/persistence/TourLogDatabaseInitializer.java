package at.fhtw.swen2.tutorial.persistence;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TourLogDatabaseInitializer implements InitializingBean {

    @Autowired
    private TourRepository tourRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        tourRepository.saveAll(getInitialDemoData());
    }

    public static List<TourEntity> getInitialDemoData() {
        List<TourEntity> tourList = new ArrayList<>();
        //TODO
        tourList.add(TourEntity.builder().id(5L).name("Tour 4").build());
        tourList.add(TourEntity.builder().id(7L).name("Tour 5").build());
        tourList.add(TourEntity.builder().id(11L).name("Tour 6").build());
        return tourList;
    }
    public static List<Tour> getInitialDemoDataDtos() {
        List<Tour> tourList = new ArrayList<>();
//        tourList.add(Tour.builder().id(5L).name("John").build());
//        tourList.add(Tour.builder().id(7L).name("Albert").build());
//        tourList.add(Tour.builder().id(11L).name("Monica").build());
        return tourList;
    }
}
