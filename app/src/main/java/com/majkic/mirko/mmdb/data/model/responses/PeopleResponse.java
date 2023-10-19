package com.majkic.mirko.mmdb.data.model.responses;

import com.google.gson.annotations.SerializedName;
import com.majkic.mirko.mmdb.data.model.Person;

import java.util.List;

/**
 * Created by hp on 08.01.2020.
 */

public class PeopleResponse {

    @SerializedName("page")
    int page;
    @SerializedName("results")
    List<Person> people;

    public PeopleResponse() {
    }

    public PeopleResponse(int page, List<Person> people) {
        this.page = page;
        this.people = people;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
