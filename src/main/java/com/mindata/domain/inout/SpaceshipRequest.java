package com.mindata.domain.inout;

import com.mindata.domain.models.SpaceshipEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceshipRequest {
    String name;
    Integer passengersAmount;

    public static SpaceshipEntity toDomain(SpaceshipRequest request) {
        return SpaceshipEntity.builder()
                .name(request.name)
                .passengersAmount(request.passengersAmount)
                .creationDate(new Date()).build();
    }
}
