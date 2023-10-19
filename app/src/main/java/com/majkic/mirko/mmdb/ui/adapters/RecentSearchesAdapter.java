package com.majkic.mirko.mmdb.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majkic.mirko.mmdb.data.model.Search;
import com.majkic.mirko.mmdb.databinding.RecentSearchesItemBinding;

import java.util.List;

public class RecentSearchesAdapter extends RecyclerView.Adapter<RecentSearchesAdapter.RecentSearchesViewHolder> {

    private Context context;
    private List<Search> searchList;
    private ItemClickedListener listener;

    public RecentSearchesAdapter(Context context, List<Search> searchList, ItemClickedListener listener) {
        this.context = context;
        this.searchList = searchList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecentSearchesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecentSearchesItemBinding binding = RecentSearchesItemBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new RecentSearchesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentSearchesViewHolder recentSearchesViewHolder, int i) {
        final Search search = searchList.get(i);
        recentSearchesViewHolder.recentSearchText.setText(search.getSearchTerm());
        recentSearchesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onSearchClicked(search);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public void setSearchList(List<Search> searchList) {
        this.searchList = searchList;
        notifyDataSetChanged();
    }

    static class RecentSearchesViewHolder extends RecyclerView.ViewHolder {

        TextView recentSearchText;

        public RecentSearchesViewHolder(@NonNull RecentSearchesItemBinding binding) {
            super(binding.getRoot());
            recentSearchText = binding.recentSearchTextView;
        }
    }

    public interface ItemClickedListener {
        void onSearchClicked(Search search);
    }

}
