package com.mindata.repositories;

import com.mindata.domain.models.SpaceshipEntity;
import com.mindata.factory.SpaceshipFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpaceshipRepositoryTest {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @BeforeAll
    public void setUp() {
        spaceshipRepository.save(SpaceshipFactory.withName("x-wing"));
        spaceshipRepository.save(SpaceshipFactory.withName("x-space"));
        spaceshipRepository.save(SpaceshipFactory.withName("x-star"));
    }

    @Test
    void shouldGetAllPaginated() {
        Pageable pageable = Pageable.ofSize(5);

        Page<SpaceshipEntity> response = spaceshipRepository.findAll(pageable);

        assertEquals(3, response.getTotalElements());

        SpaceshipEntity firstSpaceship = response.getContent().get(0);
        assertEquals(1, firstSpaceship.getId());
        assertEquals(10, firstSpaceship.getPassengersAmount());
        assertEquals("x-wing", firstSpaceship.getName());
        assertNotNull(firstSpaceship.getCreationDate());
    }

    @Test
    void shouldGetByIdCorrectly() {
        Optional<SpaceshipEntity> entity1 = spaceshipRepository.findById(1);
        Optional<SpaceshipEntity> entityVoid = spaceshipRepository.findById(4);

        assert entity1.isPresent();
        assertEquals(1, entity1.get().getId());
        assertEquals(10, entity1.get().getPassengersAmount());
        assertEquals("x-wing", entity1.get().getName());

        assert entityVoid.isEmpty();
    }

    @Test
    void shouldGetAllByFilterName() {
        List<SpaceshipEntity> list = spaceshipRepository.findAllFilteredByName("x-");
        assertEquals(3, list.size());

        list = spaceshipRepository.findAllFilteredByName("wing");
        assertEquals(1, list.size());
    }

    @Test
    void shouldSaveEntity() {
        Optional<SpaceshipEntity> entityVoid = spaceshipRepository.findById(4);
        assert entityVoid.isEmpty();

        spaceshipRepository.save(SpaceshipFactory.withName("x-4"));
        Optional<SpaceshipEntity> entity4 = spaceshipRepository.findById(4);
        assert entity4.isPresent();
    }

    @Test
    void shouldUpdateEntity() {
        Optional<SpaceshipEntity> entity3 = spaceshipRepository.findById(3);
        assert entity3.isPresent();
        SpaceshipEntity entity = entity3.get();
        assertEquals("x-star", entity.getName());

        entity.setName("x-star v2");
        spaceshipRepository.save(entity);

        entity3 = spaceshipRepository.findById(3);
        assert entity3.isPresent();
        assertEquals("x-star v2", entity.getName());
    }

    @Test
    void shouldDeleteEntity() {
        List<SpaceshipEntity> list = spaceshipRepository.findAll();
        assertEquals(3, list.size());

        spaceshipRepository.deleteById(3);
        list = spaceshipRepository.findAll();
        assertEquals(2, list.size());
    }

    @AfterAll
    public void tearDown() {
        spaceshipRepository.deleteAll();
    }
}
