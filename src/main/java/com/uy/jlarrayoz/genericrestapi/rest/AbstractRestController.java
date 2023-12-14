package com.uy.jlarrayoz.genericrestapi.rest;

import com.google.common.reflect.TypeToken;
import com.uy.jlarrayoz.genericrestapi.dto.BaseGenericDTO;
import com.uy.jlarrayoz.genericrestapi.entity.BaseGenericEntity;
import com.uy.jlarrayoz.genericrestapi.util.MyAnnotationUtils;
import org.modelmapper.ModelMapper;

import java.io.Serializable;

public abstract class AbstractRestController <T extends BaseGenericEntity, ID extends Serializable> {
    public static final String DEFAULT_CREATE_METHOD = "create";
    public static final String DEFAULT_UPDATE_MEHTOD = "update";

    protected static final ModelMapper modelMapper = new ModelMapper();

    //Clase asociada al tipo genérico T (Entity asociada)
    protected final Class<T> entityClass;

    public AbstractRestController() {
        this.entityClass = getEntityClass();
    }

    /**
     * Devuelve la Class del tipo generico T
     * @return Class del tipo T
     */
    protected Class<T> getEntityClass(){
        TypeToken<T> typeToken = new TypeToken<T>(getClass()) {};
        return (Class<T>) typeToken.getRawType();
    }

    protected Class<? extends BaseGenericDTO> getQueryDTO(){
        return MyAnnotationUtils.getGenericDtoToRestController(null, entityClass);
    }
}
