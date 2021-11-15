package com.klm.exercises.spring.locations;

import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klm.exercises.spring.paging.Pageable;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/airports")
@RequiredArgsConstructor
@Secured("USER")
public class AirportController {

    private final AirportRepository repository;

    @GetMapping
    public Callable<PagedModel<EntityModel<Location>>> list(
                    @RequestParam(value = "lang", defaultValue = "en") final String lang,
                    final Pageable<Location> pageable) {
        return () -> pageable.partition(repository.list(Locale.forLanguageTag(lang)));
    }

    @GetMapping("/{key}")
    public Callable<HttpEntity<Location>> show(@RequestParam(value = "lang", defaultValue = "en") final String lang,
                    @PathVariable("key") final String key) {
        return () -> {
            Thread.sleep(ThreadLocalRandom.current().nextLong(200, 800));
            return repository.get(Locale.forLanguageTag(lang), key)
                            .map(l -> new ResponseEntity<>(l, OK))
                            .orElse(new ResponseEntity<>(NOT_FOUND));
        };
    }

    @GetMapping(params = "term")
    public Callable<HttpEntity<PagedModel<EntityModel<Location>>>> find(
                    @RequestParam(value = "lang", defaultValue = "en") final String lang,
                    @RequestParam("term") final String term,
                    final Pageable<Location> pageable) {
        return () -> repository.find(Locale.forLanguageTag(lang), term)
                        .map(l -> new ResponseEntity<>(pageable.partition(l), OK))
                        .orElse(new ResponseEntity<>(NOT_FOUND));
    }

}
