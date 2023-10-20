package com.majkic.mirko.mmdb.data.repository;

import androidx.lifecycle.LiveData;

import com.majkic.mirko.mmdb.data.model.Person;

import java.util.List;

public interface PeopleDataRepository {

    LiveData<List<Person>> getPeople();

    void getCachedPeople();

    void getNextPeople();

}
