package com.sander.usermanager.repository;

import java.util.List;

/**
 * Created by Sander on 10/01/2015.
 */

//TODO (Sander Paulus): abstracte repository voorzien?
public interface UserManagerRepository<T> {

    List<T> zoek(T t);

    T zoekMetId(Long id);

    boolean bestaat(T t);

    void verwijder(T t);

    void bewaarOfWerkBij(T t);
}
