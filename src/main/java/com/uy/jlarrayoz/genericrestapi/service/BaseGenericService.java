package com.uy.jlarrayoz.genericrestapi.service;

import com.uy.jlarrayoz.genericrestapi.entity.BaseGenericEntity;
import com.uy.jlarrayoz.genericrestapi.repository.BaseGenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public class BaseGenericService<T extends BaseGenericEntity> {

    private final BaseGenericRepository<T> repository;

    public BaseGenericService(BaseGenericRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> findAll(){
        return repository.findAll();
    }

    public List<T> findAll(Sort sort){
        return repository.findAll(sort);
    }

    public Page<T> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Optional<T> findById(Long id){
        return repository.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public T save(T entity){
        return repository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<T> save(Iterable<T> entities){
        return repository.saveAll(entities);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
