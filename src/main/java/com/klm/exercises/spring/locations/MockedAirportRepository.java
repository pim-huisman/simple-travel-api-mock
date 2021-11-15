package com.klm.exercises.spring.locations;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Slf4j
@Repository
public class MockedAirportRepository implements AirportRepository {

    private final Map<String, Location> nlAirports;
    private final Map<String, Location> enAirports;
    private final ObjectMapper mapper;

    @Autowired
    public MockedAirportRepository(final ObjectMapper mapper) {
        this.mapper = mapper;
        nlAirports = parseMockData("nl");
        enAirports = parseMockData("en");
    }

    @Override
    public Optional<Location> get(final Locale locale, final String key) {
        return locale.getLanguage().equals("nl") ?
                        Optional.ofNullable(nlAirports.get(key.toUpperCase())) :
                        Optional.ofNullable(enAirports.get(key.toUpperCase()));
    }

    @Override
    public Optional<Collection<Location>> find(final Locale locale, final String term) {
        final Predicate<Location> filter = l -> l.getCode().toLowerCase().contains(term.toLowerCase())
                        || l.getName().toLowerCase().contains(term.toLowerCase())
                        || l.getDescription().toLowerCase().contains(term.toLowerCase());
        final Collection<Location> results = locale.getLanguage().equals("nl") ?
                        nlAirports.values().parallelStream().filter(filter).collect(toList()) :
                        enAirports.values().parallelStream().filter(filter).collect(toList());
        return Optional.of(results);
    }

    @Override
    public Collection<Location> list(final Locale locale) {
        return locale.getLanguage().equals("nl") ? nlAirports.values() : enAirports.values();
    }

    private Map<String, Location> parseMockData(final String lang) {
        try {
            log.info("Loading static resources from classpath and populating mocks.");
            final List<Location> locations = mapper.readValue(
                            new ClassPathResource("locations_".concat(lang).concat(".json")).getInputStream(),
                            mapper.getTypeFactory().constructCollectionType(ArrayList.class, Location.class));
            return locations.parallelStream()
                            .map(l -> new SimpleEntry<>(l.getCode(), l))
                            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        } catch (final IOException e) {
            throw new IllegalStateException("Unable to initialize mock in-memory AirportRepository.", e);
        }
    }

}
