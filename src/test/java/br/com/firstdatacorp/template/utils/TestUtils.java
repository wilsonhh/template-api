package br.com.firstdatacorp.template.utils;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class TestUtils {
    public static MockMvcBuilder prepareMockMvcForPageableArguments(Object... controllers) {
        return MockMvcBuilders.standaloneSetup(controllers)
                .setCustomArgumentResolvers(
                        new HandlerMethodArgumentResolver() {
                            @Override
                            public boolean supportsParameter(MethodParameter parameter) {
                                return parameter.getParameterType().equals(Pageable.class);
                            }
 
                            @Override
                            public Object resolveArgument(MethodParameter parameter,
                                    ModelAndViewContainer container,
                                    NativeWebRequest request,
                                    WebDataBinderFactory binderFactory) throws Exception {
                                return PageRequest.of(0, 50);
                            }
                        });
 
    }
}