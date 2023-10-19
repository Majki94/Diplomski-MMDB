package com.majkic.mirko.mmdb.ui.people;

import com.majkic.mirko.mmdb.data.model.Person;

import java.util.List;

/**
 * Created by hp on 08.01.2020.
 */

public interface PeopleContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showPeople(List<Person> people);

        void appendPeople(List<Person> people);
    }

    interface UserActionsListener {
        void getPeople();

        void getNextPeople();
    }

}
