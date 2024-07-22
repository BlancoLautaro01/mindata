package com.mindata.services.usecase;

import com.mindata.domain.inout.SpaceshipRequest;
import com.mindata.domain.inout.SpaceshipResponse;
import com.mindata.domain.models.SpaceshipEntity;
import com.mindata.exception.cases.SpaceshipNotFoundException;
import com.mindata.repositories.SpaceshipRepository;
import com.mindata.services.ports.SpaceshipCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceshipUsecase implements SpaceshipCommand {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @Override
    public Page<SpaceshipResponse> findAll(Pageable pageable) {
        return spaceshipRepository.findAll(pageable).map(SpaceshipResponse::fromDomain);
    }

    @Override
    public SpaceshipResponse getSpaceshipById(Integer spaceshipId) throws SpaceshipNotFoundException {
        SpaceshipEntity entity = spaceshipRepository.findById(spaceshipId).orElseThrow(
                () -> new SpaceshipNotFoundException("Invalid spaceshipId: " + spaceshipId));
        return SpaceshipResponse.fromDomain(entity);
    }

    @Override
    public List<SpaceshipResponse> getSpaceshipByName(String nameFilter) {
        return spaceshipRepository.findAllFilteredByName(nameFilter)
                .stream().map(SpaceshipResponse::fromDomain).toList();
    }

    @Override
    public SpaceshipResponse createSpaceship(SpaceshipRequest spaceshipRequest) {
        SpaceshipEntity entity = spaceshipRepository.save(SpaceshipRequest.toDomain(spaceshipRequest));
        return SpaceshipResponse.fromDomain(entity);
    }

    @Override
    public SpaceshipResponse updateSpaceship(SpaceshipRequest spaceshipRequest, Integer spaceshipId) {
        SpaceshipEntity entity = SpaceshipRequest.toDomain(spaceshipRequest);
        entity.setId(spaceshipId);

        spaceshipRepository.save(entity);
        return SpaceshipResponse.fromDomain(entity);
    }

    @Override
    public void deleteSpaceship(Integer spaceshipId) {
        spaceshipRepository.deleteById(spaceshipId);
    }
}
