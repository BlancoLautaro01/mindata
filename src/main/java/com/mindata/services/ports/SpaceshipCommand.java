package com.mindata.services.ports;

import com.mindata.domain.inout.SpaceshipRequest;
import com.mindata.domain.inout.SpaceshipResponse;
import com.mindata.exception.cases.SpaceshipNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpaceshipCommand {

    Page<SpaceshipResponse> findAll(Pageable pageable);
    SpaceshipResponse getSpaceshipById(Integer spaceshipId) throws SpaceshipNotFoundException;
    List<SpaceshipResponse> getSpaceshipByName(String nameFilter);
    SpaceshipResponse createSpaceship(SpaceshipRequest spaceshipRequest);
    SpaceshipResponse updateSpaceship(SpaceshipRequest spaceshipRequest, Integer spaceshipId);
    void deleteSpaceship(Integer spaceshipId);
}
