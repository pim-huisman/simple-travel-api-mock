package com.klm.exercises.spring.fares;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klm.exercises.spring.locations.AirportRepository;
import com.klm.exercises.spring.locations.Location;

import lombok.RequiredArgsConstructor;

import static java.math.RoundingMode.HALF_UP;
import static java.util.Locale.ENGLISH;

@RestController
@RequestMapping("/fares/{origin}/{destination}")
@RequiredArgsConstructor
@Secured("USER")
public class FareController {

    private final AirportRepository repository;

    @GetMapping
    public Callable<Fare> calculateFare(@PathVariable("origin") final String origin,
                    @PathVariable("destination") final String destination,
                    @RequestParam(value = "currency", defaultValue = "EUR") final String currency) {
        return () -> {
            Thread.sleep(ThreadLocalRandom.current().nextLong(1000, 6000));
            final Location o = getLocation(ENGLISH, origin);
            final Location d = getLocation(ENGLISH, destination);
            final BigDecimal fare = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(100, 3500))
                            .setScale(2, HALF_UP);
            return new Fare(fare.doubleValue(), Currency.valueOf(currency.toUpperCase()), o.getCode(), d.getCode());
        };
    }

    private Location getLocation(final Locale locale, final String key) {
        return repository.get(locale, key).orElseThrow(IllegalArgumentException::new);
    }

}
