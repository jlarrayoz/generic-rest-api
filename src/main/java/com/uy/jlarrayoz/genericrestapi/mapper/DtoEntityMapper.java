package com.uy.jlarrayoz.genericrestapi.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uy.jlarrayoz.genericrestapi.annotation.DTO;
import com.uy.jlarrayoz.genericrestapi.annotation.GenericDTO;
import com.uy.jlarrayoz.genericrestapi.util.MyAnnotationUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Optional;

public class DtoEntityMapper extends RequestResponseBodyMethodProcessor {

    private static final ModelMapper modelMapper = new ModelMapper();

    private EntityManager entityManager;

    public DtoEntityMapper(ObjectMapper objectMapper, EntityManager entityManager) {
        super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
        this.entityManager = entityManager;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(DTO.class) || parameter.hasParameterAnnotation(GenericDTO.class);
    }

    /**
     * Se sobreescribe este método para forzar a que siempre se disparan las validaciones sin necesidad de @Valid o @Validated
     * @param binder
     * @param parameter
     */
    @Override
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        binder.validate();
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object dto = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        Optional<Object> id = getEntityId(dto);
        if (id.isEmpty()) {
            return modelMapper.map(dto, parameter.getParameterType());
        } else {
            Object persistedObject = entityManager.find(parameter.getParameterType(), id.get());
            modelMapper.map(dto, persistedObject);
            return persistedObject;
        }
    }

    @Override
    protected Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
        for (Annotation ann : parameter.getParameterAnnotations()) {
            DTO dtoAnn = AnnotationUtils.getAnnotation(ann, DTO.class);
            if (dtoAnn != null) {
                return super.readWithMessageConverters(inputMessage, parameter, dtoAnn.value());
            }
            else{
                GenericDTO genericDtoAnn = AnnotationUtils.getAnnotation(ann, GenericDTO.class);

                if (genericDtoAnn != null){

                    //Obtengo el tipo del param (Debería ser una entity que extienda de BaseEntity)
                    Class<?> parameterType = parameter.getParameterType();

                    //Obtengo el método del BaseRestController que se esta ejecutando
                    String restConstrollerMethodName = parameter.getExecutable().getName();

                    return super.readWithMessageConverters(inputMessage, parameter, MyAnnotationUtils.getGenericDtoToRestController(restConstrollerMethodName, parameterType));
                }
            }
        }
        throw new RuntimeException();
    }


    /**
     * Devuelve el campo anotado con @Id (si es que existe)
     * @param dto
     * @return
     */
    private Optional<Object> getEntityId(@NotNull Object dto) {
        for (Field field : dto.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Id.class) != null) {
                try {
                    field.setAccessible(true);
                    return Optional.ofNullable(field.get(dto));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return Optional.empty();
    }
}
