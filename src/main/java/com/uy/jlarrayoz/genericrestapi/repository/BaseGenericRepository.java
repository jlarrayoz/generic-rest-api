package com.uy.jlarrayoz.genericrestapi.repository;

import com.uy.jlarrayoz.genericrestapi.entity.BaseGenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseGenericRepository<T extends BaseGenericEntity, ID extends Serializable> extends JpaRepository<T, ID> {
}
