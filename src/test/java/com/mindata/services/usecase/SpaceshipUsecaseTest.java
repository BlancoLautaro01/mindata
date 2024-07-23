package com.mindata.services.usecase;

import com.mindata.domain.inout.SpaceshipRequest;
import com.mindata.domain.inout.SpaceshipResponse;
import com.mindata.exception.cases.SpaceshipNotFoundException;
import com.mindata.factory.SpaceshipFactory;
import com.mindata.domain.models.SpaceshipEntity;
import com.mindata.repositories.SpaceshipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SpaceshipUsecaseTest {

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @InjectMocks
    private SpaceshipUsecase spaceshipUsecase;

    @Test
    void shouldFindAllAndParseToResponse() {
        Pageable pageable = Pageable.ofSize(5);

        final Page<SpaceshipEntity> page = new PageImpl<>(List.of(
                SpaceshipFactory.withId(1), SpaceshipFactory.withId(2), SpaceshipFactory.withId(3)
        ));

        Mockito.when(spaceshipRepository.findAll(pageable)).thenReturn(page);

        Page<SpaceshipResponse> response = spaceshipUsecase.findAll(pageable);
        assertEquals(3, response.getTotalElements());

        SpaceshipResponse firstSpaceship = response.getContent().get(0);
        assertEquals(1, firstSpaceship.getId());
        assertEquals(10, firstSpaceship.getPassengersAmount());
        assertEquals("x-spaceship-1", firstSpaceship.getName());
        assertNotNull(firstSpaceship.getCreationDate());

        verify(spaceshipRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldFindByIdAndParseToResponse() throws SpaceshipNotFoundException {
        int spaceshipId = 1;
        Mockito.when(spaceshipRepository.findById(spaceshipId)).thenReturn(
                Optional.of(SpaceshipFactory.withId(spaceshipId)));

        SpaceshipResponse response = spaceshipUsecase.getSpaceshipById(spaceshipId);

        assertEquals(1, response.getId());
        assertEquals(10, response.getPassengersAmount());
        assertEquals("x-spaceship-1", response.getName());
        assertNotNull(response.getCreationDate());
        verify(spaceshipRepository, times(1)).findById(spaceshipId);
    }

    @Test
    void shouldNOTFindByIdAndThrowException() {
        int spaceshipId = 1;
        Mockito.when(spaceshipRepository.findById(spaceshipId)).thenReturn(Optional.empty());

        assertThrows(SpaceshipNotFoundException.class, () -> spaceshipUsecase.getSpaceshipById(spaceshipId));
        verify(spaceshipRepository, times(1)).findById(spaceshipId);
    }

    @Test
    void shouldFindAllByFilterAndParseToResponse() {
        final List<SpaceshipEntity> list = List.of(
                SpaceshipFactory.withId(1), SpaceshipFactory.withId(2), SpaceshipFactory.withId(3)
        );

        Mockito.when(spaceshipRepository.findAllFilteredByName("filter")).thenReturn(
                list
        );

        List<SpaceshipResponse> response = spaceshipUsecase.getSpaceshipByName("filter");
        assertEquals(3, response.size());

        SpaceshipResponse firstSpaceship = response.get(0);
        assertEquals(1, firstSpaceship.getId());
        assertEquals(10, firstSpaceship.getPassengersAmount());
        assertEquals("x-spaceship-1", firstSpaceship.getName());
        assertNotNull(firstSpaceship.getCreationDate());

        verify(spaceshipRepository, times(1)).findAllFilteredByName("filter");
    }

    @Test
    void shouldSaveParseToResponse() {
        int spaceshipId = 1;
        SpaceshipRequest request = new SpaceshipRequest("x-spaceship-1", 10);
        SpaceshipEntity entity = SpaceshipRequest.toDomain(request);
        entity.setId(spaceshipId);

        Mockito.when(spaceshipRepository.save(any())).thenReturn(entity);

        SpaceshipResponse response = spaceshipUsecase.createSpaceship(request);

        assertEquals(1, response.getId());
        assertEquals(10, response.getPassengersAmount());
        assertEquals("x-spaceship-1", response.getName());
        assertNotNull(response.getCreationDate());

        verify(spaceshipRepository, times(1)).save(any());
    }

    @Test
    void shouldUpdateParseToResponse() {
        int spaceshipId = 1;
        SpaceshipRequest request = new SpaceshipRequest("x-spaceship-1", 10);
        SpaceshipEntity entity = SpaceshipRequest.toDomain(request);
        entity.setId(spaceshipId);

        Mockito.when(spaceshipRepository.save(any())).thenReturn(entity);

        SpaceshipResponse response = spaceshipUsecase.updateSpaceship(request, spaceshipId);

        assertEquals(1, response.getId());
        assertEquals(10, response.getPassengersAmount());
        assertEquals("x-spaceship-1", response.getName());
        assertNotNull(response.getCreationDate());

        verify(spaceshipRepository, times(1)).save(any());
    }

    @Test
    void shouldDeleteWithNoErrors() {
        int spaceshipId = 1;
        assertDoesNotThrow(() -> spaceshipUsecase.deleteSpaceship(spaceshipId));

        verify(spaceshipRepository, times(1)).deleteById(spaceshipId);
    }
}
