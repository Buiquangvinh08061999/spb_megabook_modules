package com.cg.service;

import java.util.*;

public interface IGeneralService <T>{

    List<T> findAll();

    Optional<T> findById(Long id);

    T getById(Long id);

    T save(T t);

    void remove(Long id);


}
