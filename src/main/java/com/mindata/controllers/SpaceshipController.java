package com.mindata.controllers;

import com.mindata.domain.inout.SpaceshipFilterRequest;
import com.mindata.domain.inout.SpaceshipRequest;
import com.mindata.domain.inout.SpaceshipResponse;
import com.mindata.exception.cases.SpaceshipNotFoundException;
import com.mindata.services.ports.SpaceshipCommand;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spaceship")
@Api(value = "", tags={"Spaceship Controller"})
@Tag(name = "Spaceship Controller", description = "Spaceship Methods")
public class SpaceshipController {

    @Autowired
    private SpaceshipCommand spaceshipUsecase;

    @GetMapping
    public ResponseEntity<Page<SpaceshipResponse>> findAll(
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "5") @Positive int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return new ResponseEntity<>(spaceshipUsecase.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{spaceshipId}")
    public ResponseEntity<SpaceshipResponse> getSpaceshipById(@PathVariable Integer spaceshipId) throws SpaceshipNotFoundException {
        return new ResponseEntity<>(spaceshipUsecase.getSpaceshipById(spaceshipId), HttpStatus.OK);
    }

    @GetMapping("/filterByName")
    public ResponseEntity<List<SpaceshipResponse>> getSpaceshipByName(@RequestBody SpaceshipFilterRequest request) {
        return new ResponseEntity<>(spaceshipUsecase.getSpaceshipByName(request.getName()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SpaceshipResponse> createSpaceship(@RequestBody SpaceshipRequest spaceshipRequest) {
        return new ResponseEntity<>(spaceshipUsecase.createSpaceship(spaceshipRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{spaceshipId}")
    public ResponseEntity<SpaceshipResponse> updateSpaceship(
            @PathVariable Integer spaceshipId, @RequestBody SpaceshipRequest spaceshipRequest) {
        return new ResponseEntity<>(spaceshipUsecase.updateSpaceship(spaceshipRequest, spaceshipId), HttpStatus.OK);
    }

    @DeleteMapping("/{spaceshipId}")
    public ResponseEntity<?> deleteSpaceship(@PathVariable Integer spaceshipId) {
        spaceshipUsecase.deleteSpaceship(spaceshipId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
