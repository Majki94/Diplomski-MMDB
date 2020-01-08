package com.majkic.mirko.mmdb.people;

import com.majkic.mirko.mmdb.model.Person;

import java.util.List;

/**
 * Created by hp on 08.01.2020.
 */

public interface PeopleContract {

    interface View{
        void showProgress();

        void hideProgress();

        void showPeople(List<Person> people);
    }

    interface UserActionsListener{
        void getPeople();
    }

}
