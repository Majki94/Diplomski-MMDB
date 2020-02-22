package com.majkic.mirko.mmdb.data;

import com.majkic.mirko.mmdb.model.Person;
import com.majkic.mirko.mmdb.retrofit.Communicator;

import java.util.ArrayList;
import java.util.List;

public class PeopleDataRepositoryImplementation implements PeopleDataRepository {

    private static PeopleDataRepository INSTANCE;
    private List<Person> cachedPeople;
    private int page;

    public static PeopleDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PeopleDataRepositoryImplementation();
        }
        return INSTANCE;
    }

    private PeopleDataRepositoryImplementation() {
        cachedPeople = new ArrayList<>();
        page = 1;
    }

    @Override
    public void getCachedPeople(GetPeopleCallback callback) {
        if (cachedPeople.size() > 0) {
            callback.onPeopleGot(cachedPeople);
        } else {
            getNextPeople(callback);
        }
    }

    @Override
    public void getNextPeople(final GetPeopleCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Communicator.getPopularPeople(page, new Communicator.GetPeopleCallback() {
                    @Override
                    public void onPeopleGot(List<Person> people) {
                        cachedPeople.addAll(people);
                        callback.onPeopleGot(people);
                        page++;
                    }
                });
            }
        }).start();
    }
}
