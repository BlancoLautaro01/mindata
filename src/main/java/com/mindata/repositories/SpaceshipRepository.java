package com.mindata.repositories;

import com.mindata.domain.models.SpaceshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceshipRepository extends JpaRepository<SpaceshipEntity, Integer> {

    @Query(value = " SELECT * FROM spaceship s " +
            " WHERE LOWER(s.name) LIKE LOWER('%' || :nameFilter || '%') ", nativeQuery = true)
    List<SpaceshipEntity> findAllFilteredByName(String nameFilter);
}
