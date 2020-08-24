package com.example.pokemon.pokeapi;

import com.example.pokemon.modelos.pokemonRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApiService {

    @GET("pokemon")
    Call<pokemonRespuesta> obtenerListaPokemon(@Query("limit")int limit, @Query("offset") int offset);

}
