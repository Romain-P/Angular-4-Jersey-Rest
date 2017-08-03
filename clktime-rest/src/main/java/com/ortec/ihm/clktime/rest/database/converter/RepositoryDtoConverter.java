package com.ortec.ihm.clktime.rest.database.converter;

import com.google.common.collect.Streams;
import lombok.AccessLevel;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Getter(AccessLevel.PROTECTED)
public abstract class RepositoryDtoConverter<E, D> implements CrudDTO<D> {
    private DTOConverter<E, D> converter;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;
    private final CrudRepository<E, Integer> repository;

    @Autowired
    ModelMapper mapper;

    public RepositoryDtoConverter(CrudRepository<E, Integer> repository, Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.repository = repository;
    }

    @PostConstruct
    public void initializeMapper() {
        this.converter = defineConverter(mapper, entityClass, dtoClass);
    }

    public Optional<D> findById(int id) {
        return Optional.ofNullable(converter.fromEntity(repository.findOne(id)));
    }

    public List<D> findAll(){
        return Streams.stream(repository.findAll())
                .map(x -> converter.fromEntity(x))
                .collect(Collectors.toList());
    }

    public void create(D dto){
        repository.save(converter.fromDto(dto));
    }

    public void update(D dto){
        repository.save(converter.fromDto(dto));
    }

    public void delete(D dto){
        repository.delete(converter.fromDto(dto));
    }

    public void deleteById(int id){
        repository.delete(id);
    }

    protected DTOConverter<E, D> defineConverter(ModelMapper mapper, Class<E> entityClass, Class<D> dtoClass) {
        return new DefaultConverterDTO<>(mapper, entityClass, dtoClass);
    }
}