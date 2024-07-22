package com.mindata.domain.inout;

import com.mindata.domain.models.SpaceshipEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpaceshipResponse {
    Integer id;
    String name;
    Integer passengersAmount;
    Date creationDate;

    public static SpaceshipResponse fromDomain(SpaceshipEntity entity) {
        return SpaceshipResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .passengersAmount(entity.getPassengersAmount())
                .creationDate(entity.getCreationDate()).build();
    }
}
