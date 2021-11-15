package com.klm.exercises.spring.locations;

import com.klm.exercises.spring.paging.PageableHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.core.EvoInflectorLinkRelationProvider;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;

@Configuration
@EnableWebMvc
public class LocationsApiWebConfiguration implements WebMvcConfigurer {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToDisable(WRITE_DATES_AS_TIMESTAMPS);
        builder.failOnUnknownProperties(false);
        builder.serializationInclusion(NON_NULL);
        builder.modules(new JacksonLocationsModule(), new Jackson2HalModule());
        return builder;
    }

    @Bean
    public LinkRelationProvider jsonRelProvider() {
        return new EvoInflectorLinkRelationProvider();
    }

    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(true).defaultContentType(HAL_JSON);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

}
