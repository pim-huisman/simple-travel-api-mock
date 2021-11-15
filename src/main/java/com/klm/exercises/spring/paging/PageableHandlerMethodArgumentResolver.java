package com.klm.exercises.spring.paging;

import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(Pageable.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter methodParameter,
                    final ModelAndViewContainer modelAndViewContainer,
                    final NativeWebRequest nativeWebRequest,
                    final WebDataBinderFactory webDataBinderFactory) throws Exception {
        int page = Optional.ofNullable(nativeWebRequest.getParameter("page"))
                        .map(p -> Math.abs(Integer.parseInt(p))).orElse(1);
        int size = Optional.ofNullable(nativeWebRequest.getParameter("size"))
                        .map(s -> Math.abs(Integer.parseInt(s))).orElse(25);
        return new Pageable(page, size);
    }

}
