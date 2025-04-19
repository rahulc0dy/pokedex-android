package com.rahulc0dy.pokedex.datamodels

data class PokemonBrief(
    val id: Int,
    val name: String,
    val imageUrl: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
)
