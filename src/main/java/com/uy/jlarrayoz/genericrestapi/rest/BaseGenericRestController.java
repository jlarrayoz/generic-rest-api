package com.uy.jlarrayoz.genericrestapi.rest;

import com.uy.jlarrayoz.genericrestapi.annotation.GenericDTO;
import com.uy.jlarrayoz.genericrestapi.dto.BaseGenericDTO;
import com.uy.jlarrayoz.genericrestapi.entity.BaseGenericEntity;
import com.uy.jlarrayoz.genericrestapi.repository.BaseGenericRepository;
import com.uy.jlarrayoz.genericrestapi.service.BaseGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseGenericRestController<T extends BaseGenericEntity> extends AbstractRestController<T> {

    private final BaseGenericService<T> service;

    @Autowired
    public BaseGenericRestController(BaseGenericRepository<T> repository) {
        this.service = new BaseGenericService<T>(repository);
    }

    @Autowired
    public BaseGenericRestController(BaseGenericService<T> service) {
        this.service = service ;
    }


    @GetMapping(value = "{id}")
    public Optional<T> get(@PathVariable(value = "id", required = true) Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<? extends BaseGenericDTO> getAll() {
        return service.findAll().stream().map(e -> modelMapper.map(e, getQueryDTO())).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "size", "sort"})
    public Page<T> getAllPageable(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping(params = {"page", "size"})
    public Page<T> getAllPageableWithoutOrder(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping(params = {"sort"})
    public List<T> getAllSorted(Sort sort){
        return service.findAll(sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public T create(@GenericDTO T entity) {
        return service.save(entity);
    }

    @PutMapping
    public T update(@GenericDTO T entity) {
        return service.save(entity);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable(value = "id", required = true) Long id) {
        service.deleteById(id);
    }


}
