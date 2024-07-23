package com.mindata.factory;

import com.mindata.domain.models.SpaceshipEntity;

import java.util.Date;

public class SpaceshipFactory {

    public static SpaceshipEntity withId(Integer id) {
        return SpaceshipEntity.builder()
                .id(id)
                .name("x-spaceship-" + id)
                .passengersAmount(10)
                .creationDate(new Date())
                .build();
    }
}
