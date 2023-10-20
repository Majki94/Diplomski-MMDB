package com.majkic.mirko.mmdb.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.majkic.mirko.mmdb.data.model.Person;
import com.majkic.mirko.mmdb.data.retrofit.Communicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PeopleDataRepositoryImplementation implements PeopleDataRepository {

    private static PeopleDataRepository INSTANCE;
    private MutableLiveData<List<Person>> cachedPeople;
    private int page;

    public static PeopleDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PeopleDataRepositoryImplementation();
        }
        return INSTANCE;
    }

    private PeopleDataRepositoryImplementation() {
        cachedPeople = new MutableLiveData<>(Collections.emptyList());
        page = 1;
    }

    @Override
    public LiveData<List<Person>> getPeople() {
        return cachedPeople;
    }

    @Override
    public void getCachedPeople() {
        if (cachedPeople.getValue().size() > 0) {
            cachedPeople.setValue(cachedPeople.getValue());
        } else {
            getNextPeople();
        }
    }

    @Override
    public void getNextPeople() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Communicator.getPopularPeople(page, new Communicator.GetPeopleCallback() {
                    @Override
                    public void onPeopleGot(List<Person> people) {
                        List<Person> allPeople = new ArrayList<>();
                        allPeople.addAll(cachedPeople.getValue());
                        allPeople.addAll(people);
                        cachedPeople.setValue(allPeople);
                        page++;
                    }
                });
            }
        }).start();
    }
}
