package com.klm.exercises.spring.paging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.client.HttpServerErrorException;

import com.google.common.collect.Lists;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@EqualsAndHashCode
public class Pageable<T> {

    private final int page, size;

    public Pageable(final int page, final int size) {
        this.page = page;
        this.size = size;
    }

    public PagedModel<EntityModel<T>> partition(final Collection<T> original) {
        if (original == null || original.isEmpty()) {
            throw new HttpServerErrorException(NOT_FOUND,
                            "Unable to partition results, no results available.");
        }
        final List<List<T>> partitionedList = Lists.partition(
                        original instanceof List ? (List) original : new ArrayList<>(original), size);
        try {
            final List<T> result = partitionedList.get(page == 0 ? page : page - 1);
            final PagedModel.PageMetadata metadata =
                            new PagedModel.PageMetadata(size, page, original.size(),
                                            partitionedList.size());
            return PagedModel.wrap(result, metadata);
        } catch (final IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Requested page is out of bounds.");
        }
    }

}
