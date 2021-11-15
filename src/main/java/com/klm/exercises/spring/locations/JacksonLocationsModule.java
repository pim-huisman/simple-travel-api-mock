package com.klm.exercises.spring.locations;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonLocationsModule extends SimpleModule {

    @Override
    public void setupModule(final SetupContext context) {
        context.setMixInAnnotations(Location.class, LocationMixin.class);
        context.setMixInAnnotations(Coordinates.class, CoordinatesMixin.class);
    }

}
