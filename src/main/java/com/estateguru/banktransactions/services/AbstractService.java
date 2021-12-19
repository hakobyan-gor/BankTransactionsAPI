package com.estateguru.banktransactions.services;

import com.estateguru.banktransactions.exceptions.EntityNotFoundException;
import com.estateguru.banktransactions.models.entities.AbstractEntity;
import com.estateguru.banktransactions.repositories.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>> implements CommonService<E> {

    private final R mRepository;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public AbstractService(R repository) {
        this.mRepository = repository;
    }

    @Override
    public E save(E model) throws EntityNotFoundException {
        if (model.getHidden() == null)
            model.setHidden(false);
        return mRepository.save(model);
    }

    @Override
    public Iterable<E> saveAll(Iterable<E> iterable) {
        return mRepository.saveAll(iterable);
    }

    @Override
    public Iterable<E> listAll() {
        return mRepository.findAllByHiddenFalse();
    }

    @Override
    public Page<E> listAll(Pageable pageable) {
        return mRepository.findAllByHiddenFalse(pageable);
    }

    @Override
    public E getById(Long id) throws EntityNotFoundException {
        Optional<E> optionalE = mRepository.findById(id);
        if (optionalE.isEmpty() || optionalE.get().getHidden().equals(true))
            throw new EntityNotFoundException(mRepository.findById(id).getClass(), "id", String.valueOf(id));

        return optionalE.get();
    }

    @Override
    public E getByIdWithHidden(Long id) {
        return mRepository.findById(id).orElse(null);
    }

    @Override
    public void remove(E entity) {
        mRepository.delete(entity);
    }

    @Override
    public Long count() {
        return mRepository.count();
    }

    @Override
    public boolean delete(Long id) throws EntityNotFoundException {
        if (mRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(mRepository.findById(id).getClass(), "id", String.valueOf(id));
        mRepository.deleteById(id);
        return true;
    }

}