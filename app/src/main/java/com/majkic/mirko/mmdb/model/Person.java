package com.majkic.mirko.mmdb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hp on 08.01.2020.
 */

public class Person {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("profile_path")
    private String profilePath;
    @SerializedName("known_for_department")
    private String knownForDepartment;
    @SerializedName("known_for")
    private List<Movie> knownFor;

    public Person() {
    }

    public Person(int id, String name, float popularity, boolean adult, String profilePath, String knownForDepartment, List<Movie> knownFor) {
        this.id = id;
        this.name = name;
        this.popularity = popularity;
        this.adult = adult;
        this.profilePath = profilePath;
        this.knownForDepartment = knownForDepartment;
        this.knownFor = knownFor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public List<Movie> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(List<Movie> knownFor) {
        this.knownFor = knownFor;
    }
}
