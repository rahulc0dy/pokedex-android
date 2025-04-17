package com.rahulc0dy.pokedex.api

import com.rahulc0dy.pokedex.services.PokemonService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object PokemonApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")        // PokeAPI v2 base URL :contentReference[oaicite:5]{index=5}
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: PokemonService = retrofit.create(PokemonService::class.java)
}
