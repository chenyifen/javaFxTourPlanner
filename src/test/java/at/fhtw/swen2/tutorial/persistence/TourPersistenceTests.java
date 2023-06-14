package at.fhtw.swen2.tutorial.persistence;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TourPersistenceTests {

	@Autowired
	private TourRepository tourRepository;

	@Test
	void testTourRepository() {
		TourEntity maxi = TourEntity.builder()
				.name("Maxi")
				.build();
		//TODO
		tourRepository.save(maxi);
		tourRepository.findAll().forEach(System.out::println);
	}

}
