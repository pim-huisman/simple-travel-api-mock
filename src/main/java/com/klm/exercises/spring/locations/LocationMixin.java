package com.klm.exercises.spring.locations;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

abstract class LocationMixin {

    @JsonCreator LocationMixin(@JsonProperty("code") final String code,
                    @JsonProperty("name") final String name,
                    @JsonProperty("description") final String description,
                    @JsonProperty("coordinates") final Coordinates coordinates,
                    @JsonProperty("parent") final Location parent,
                    @JsonProperty("children") final Set<Location> children) {
    }

}
