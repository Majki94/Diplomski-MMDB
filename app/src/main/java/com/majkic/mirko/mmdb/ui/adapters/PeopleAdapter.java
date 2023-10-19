package com.majkic.mirko.mmdb.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.data.model.Person;
import com.majkic.mirko.mmdb.databinding.PeopleListItemBinding;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    private Context context;
    private List<Person> people;

    public PeopleAdapter(Context context, List<Person> people) {
        this.context = context;
        this.people = people;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        PeopleListItemBinding binding = PeopleListItemBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new PeopleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder peopleViewHolder, int i) {
        Person p = people.get(i);
        Glide.with(context).load(Constants.BACKEND.TMDB_POSTER_BASE_URL + p.getProfilePath()).into(peopleViewHolder.image);
        peopleViewHolder.name.setText(p.getName());
        peopleViewHolder.popularity.setText(String.valueOf(p.getPopularity()));
        peopleViewHolder.department.setText(p.getKnownForDepartment());
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public void setPeople(List<Person> people) {
        this.people = people;
        notifyDataSetChanged();
    }

    public void appendPeople(List<Person> people) {
        this.people.addAll(people);
        notifyDataSetChanged();
    }

    static class PeopleViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView popularity;
        TextView adult;
        TextView department;

        public PeopleViewHolder(@NonNull PeopleListItemBinding binding) {
            super(binding.getRoot());
            image = binding.personImage;
            name = binding.personName;
            popularity = binding.personPopularity;
            adult = binding.personAdult;
            department = binding.personKnownForDepartment;
        }
    }

}
