package com.majkic.mirko.mmdb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.model.Person;

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
        View root = LayoutInflater.from(context).inflate(R.layout.people_list_item, viewGroup, false);
        return new PeopleViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder peopleViewHolder, int i) {
        Person p = people.get(i);
        Glide.with(context).load(p.getProfilePath()).into(peopleViewHolder.image);
        peopleViewHolder.name.setText(p.getName());
        peopleViewHolder.popularity.setText(String.valueOf(p.getPopularity()));
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    static class PeopleViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView popularity;
        TextView adult;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.person_image);
            name = itemView.findViewById(R.id.person_name);
            popularity = itemView.findViewById(R.id.person_popularity);
            adult = itemView.findViewById(R.id.person_adult);
        }
    }

}
