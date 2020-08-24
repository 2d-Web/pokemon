package com.example.pokemon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokemon.modelos.pokemones;

import java.util.ArrayList;

public class listapokemonadapter extends RecyclerView.Adapter<listapokemonadapter.ViewHolder> {

    private ArrayList<pokemones> dataset;
    private Context context;

public listapokemonadapter(Context context){
    this.context = context;
    dataset = new ArrayList<>();

}

    @NonNull
    @Override
    public listapokemonadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listapokemonadapter.ViewHolder holder, int position) {
        pokemones p = dataset.get(position);
        holder.nombretexview.setText(p.getName());

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/" + p.getNumber() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoimagen);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<pokemones> listapokemones) {

    dataset.addAll(listapokemones);
    notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoimagen;
        private TextView nombretexview;

        public ViewHolder(View item_pokemon){
            super(item_pokemon);

            fotoimagen = (ImageView) item_pokemon.findViewById(R.id.fotoimagen);
            nombretexview = (TextView) item_pokemon.findViewById(R.id.nombretexview);
        }
    }
    }

