package com.majkic.mirko.mmdb.data;

import com.majkic.mirko.mmdb.model.Person;

import java.util.List;

public interface PeopleDataRepository {

    void getCachedPeople(GetPeopleCallback callback);

    void getNextPeople(GetPeopleCallback callback);

    interface GetPeopleCallback {
        void onPeopleGot(List<Person> people);
    }

}
