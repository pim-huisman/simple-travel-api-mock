package com.klm.exercises.spring.locations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

abstract class CoordinatesMixin {

    @JsonCreator CoordinatesMixin(@JsonProperty("latitude") final double latitude,
                    @JsonProperty("longitude") final double longitude) {
    }

}
