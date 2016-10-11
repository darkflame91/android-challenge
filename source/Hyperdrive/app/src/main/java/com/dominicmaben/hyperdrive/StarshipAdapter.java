package com.dominicmaben.hyperdrive;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StarshipAdapter extends RecyclerView.Adapter<StarshipAdapter.ShipViewHolder> {

    private List<Starship> starshipList;

    public class ShipViewHolder extends RecyclerView.ViewHolder {
        public TextView name, cost, film;

        public ShipViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            cost = (TextView) view.findViewById(R.id.cost);
            film = (TextView) view.findViewById(R.id.film);
        }
    }


    public StarshipAdapter(List<Starship> starshipList) {
        this.starshipList = starshipList;
    }

    @Override
    public ShipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.starship_list_item, parent, false);

        return new ShipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShipViewHolder holder, int position) {
        Starship starship = starshipList.get(position);
        holder.name.setText(starship.getName());
        holder.cost.setText(starship.getCost());
        holder.film.setText(starship.getFilm());
    }

    @Override
    public int getItemCount() {
        return starshipList.size();
    }
}
