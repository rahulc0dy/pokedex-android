package com.rahulc0dy.pokedex.services

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class NamedAPIResource(val name: String, val url: String)
data class PokemonListResponse(val results: List<NamedAPIResource>)
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val abilities: List<AbilitySlot>,
    val sprites: Sprites
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("back_shiny")
    val backShiny: String?
    // add other variants if you like (e.g. “official-artwork.front_default” via nested objects)
)

// Helper classes for types & abilities
data class TypeSlot(
    val slot: Int,
    val type: NamedResource
)
data class AbilitySlot(
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    val slot: Int,
    val ability: NamedResource
)
data class NamedResource(
    val name: String,
    val url: String
)


interface PokemonService {
    @GET("pokemon")
    suspend fun listPokemons(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetail(@Path("id") id: Int): PokemonDetail
}
