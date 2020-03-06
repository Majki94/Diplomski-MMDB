package com.majkic.mirko.mmdb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.model.Search;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        View contactView = LayoutInflater.from(context).inflate(R.layout.recent_searches_item, viewGroup, false);
        return new RecentSearchesViewHolder(contactView);
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

        @BindView(R.id.recent_search_text_view)
        TextView recentSearchText;

        public RecentSearchesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ItemClickedListener {
        void onSearchClicked(Search search);
    }

}
