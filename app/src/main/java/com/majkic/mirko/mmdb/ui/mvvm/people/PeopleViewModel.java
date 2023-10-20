package com.majkic.mirko.mmdb.ui.mvvm.people;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.majkic.mirko.mmdb.data.model.Person;
import com.majkic.mirko.mmdb.data.repository.PeopleDataRepository;
import com.majkic.mirko.mmdb.data.repository.PeopleDataRepositoryImplementation;

import java.util.List;

/**
 * Created by hp on 08.01.2020.
 */

public class PeopleViewModel extends ViewModel {

    private PeopleDataRepository repository = PeopleDataRepositoryImplementation.getInstance();
    MutableLiveData<Boolean> showProgress = new MutableLiveData<>(false);
    LiveData<List<Person>> people = repository.getPeople();

    public void getPeople() {
        showProgress.setValue(true);
        repository.getCachedPeople();
    }

    public void getNextPeople() {
        showProgress.setValue(true);
        repository.getNextPeople();
    }
}
