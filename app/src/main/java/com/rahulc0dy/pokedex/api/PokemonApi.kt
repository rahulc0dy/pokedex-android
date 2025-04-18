package com.rahulc0dy.pokedex.api

import com.rahulc0dy.pokedex.datamodels.PokemonDetail
import com.rahulc0dy.pokedex.services.PokemonListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun listPokemons(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetail(@Path("id") id: Int): PokemonDetail
}

object PokemonApi {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    val service: PokemonService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokemonService::class.java)
}
