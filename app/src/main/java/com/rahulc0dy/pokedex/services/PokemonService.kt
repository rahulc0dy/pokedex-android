package com.rahulc0dy.pokedex.services

import com.rahulc0dy.pokedex.datamodels.NamedAPIResource
import com.rahulc0dy.pokedex.datamodels.PokemonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class PokemonListResponse(val results: List<NamedAPIResource>)


interface PokemonService {
    @GET("pokemon")
    suspend fun listPokemons(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetail(@Path("id") id: Int): PokemonDetail
}
