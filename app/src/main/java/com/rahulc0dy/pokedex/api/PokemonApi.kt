package com.rahulc0dy.pokedex.api

import com.rahulc0dy.pokedex.datamodels.EvolutionChain
import com.rahulc0dy.pokedex.datamodels.PokemonDetail
import com.rahulc0dy.pokedex.datamodels.PokemonListResponse
import com.rahulc0dy.pokedex.datamodels.PokemonSpecies
import com.rahulc0dy.pokedex.datamodels.TypeEffectivenessResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonService {
    @GET("pokemon")
    suspend fun listPokemons(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    // existing by‑id lookup—keep if you still want it
    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetail(@Path("id") id: Int): PokemonDetail

    // new: lookup by name
    @GET("pokemon/{name}")
    suspend fun fetchPokemonByName(@Path("name") name: String): PokemonDetail

    // Add new method to fetch species details
    @GET("pokemon-species/{id}")
    suspend fun fetchPokemonSpecies(@Path("id") id: Int): PokemonSpecies

    // Add method to fetch from a complete URL
    @GET
    suspend fun fetchFromUrl(@Url url: String): PokemonSpecies

    @GET("evolution-chain/{id}")
    suspend fun fetchEvolutionChain(@Path("id") id: Int): EvolutionChain

    // New endpoint to fetch type effectiveness data
    @GET("type/{name}")
    suspend fun fetchTypeEffectiveness(@Path("name") typeName: String): TypeEffectivenessResponse
}


object PokemonApi {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    val service: PokemonService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokemonService::class.java)
}
