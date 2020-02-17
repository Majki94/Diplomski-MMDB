package com.majkic.mirko.mmdb.people;

import com.majkic.mirko.mmdb.data.PeopleDataRepository;
import com.majkic.mirko.mmdb.data.PeopleDataRepositoryImplementation;
import com.majkic.mirko.mmdb.model.Person;

import java.util.List;

/**
 * Created by hp on 08.01.2020.
 */

public class PeoplePresenter implements PeopleContract.UserActionsListener {

    private PeopleContract.View view;
    private PeopleDataRepository repository;

    public PeoplePresenter(PeopleContract.View view) {
        this.view = view;
        this.repository = PeopleDataRepositoryImplementation.getInstance();
    }

    @Override
    public void getPeople() {
        view.showProgress();
        repository.getCachedPeople(new PeopleDataRepository.GetPeopleCallback() {
            @Override
            public void onPeopleGot(List<Person> people) {
                view.showPeople(people);
                view.hideProgress();
            }
        });
    }
}
