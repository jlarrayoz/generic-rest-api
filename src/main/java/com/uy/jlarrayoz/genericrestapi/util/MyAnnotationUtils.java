package com.uy.jlarrayoz.genericrestapi.util;

import com.uy.jlarrayoz.genericrestapi.annotation.GenericDtoType;
import com.uy.jlarrayoz.genericrestapi.dto.BaseGenericDTO;
import com.uy.jlarrayoz.genericrestapi.rest.AbstractRestController;
import org.springframework.core.annotation.AnnotationUtils;

public abstract class MyAnnotationUtils {

    public static GenericDtoType getGenericDtoTypeAnnotation(Class<?> clazz) {
        return AnnotationUtils.getAnnotation(clazz, GenericDtoType.class);
    }

    public static Class<? extends BaseGenericDTO> getGenericDtoToRestController(String restConstrollerMethodName, Class<?> clazz) {
        GenericDtoType genericDtoTypeAnnotation = getGenericDtoTypeAnnotation(clazz);

        if (genericDtoTypeAnnotation != null){
            if (restConstrollerMethodName == null){
                return genericDtoTypeAnnotation.queryDTO();
            }
            else if (restConstrollerMethodName.equals(AbstractRestController.DEFAULT_CREATE_METHOD)){
                return genericDtoTypeAnnotation.insertDTO();
            }
            else if (restConstrollerMethodName.equals(AbstractRestController.DEFAULT_UPDATE_MEHTOD)){
                return genericDtoTypeAnnotation.updateDTO();
            }
        }
        throw new RuntimeException("No fue posible determinar el DTO a utilizar");
    }
}
