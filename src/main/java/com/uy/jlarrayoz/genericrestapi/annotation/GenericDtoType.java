package com.uy.jlarrayoz.genericrestapi.annotation;

import com.uy.jlarrayoz.genericrestapi.dto.BaseGenericDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenericDtoType {

    Class<? extends BaseGenericDTO> queryDTO();
    Class<? extends BaseGenericDTO> insertDTO();
    Class<? extends BaseGenericDTO> updateDTO();
}
