package com.example.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import com.example.pokemon.modelos.pokemonRespuesta;
import com.example.pokemon.modelos.pokemones;
import com.example.pokemon.pokeapi.PokeApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Pokedex";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private listapokemonadapter listapokemonadapter;

    private int offset;

    private boolean cargapokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        listapokemonadapter = new listapokemonadapter(this);
        recyclerView.setAdapter(listapokemonadapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totallItemCount = layoutManager.getItemCount();
                    int pastVisubleItems = layoutManager.findFirstVisibleItemPosition();

                    if (cargapokemon){
                        if ((visibleItemCount + pastVisubleItems) >= totallItemCount){
            Log.i(TAG,"No hay mas pokemon registrados.");
            cargapokemon = false;
            offset += 20;
            obtenerDatos(offset);
                        }
                    }
                }
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cargapokemon = true;
        offset = 0;
        obtenerDatos(offset);

    }

    private void obtenerDatos(int offset) {

        PokeApiService service = retrofit.create(PokeApiService.class);

        Call<pokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon(20,40);

        pokemonRespuestaCall.enqueue(new Callback<pokemonRespuesta>() {
            @Override
            public void onResponse(Call<pokemonRespuesta> call, Response<pokemonRespuesta> response) {
cargapokemon = true;
                if (response.isSuccessful()){

                    pokemonRespuesta pokemonRespuesta = response.body();
                    ArrayList<pokemones> listapokemones = pokemonRespuesta.getResult();

                    listapokemonadapter.adicionarListaPokemon(listapokemones);

                }else{

                    Log.e(TAG, "onResponse: " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<pokemonRespuesta> call, Throwable t) {
cargapokemon = true;
                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });


    }
}