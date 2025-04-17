package com.rahulc0dy.pokedex.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulc0dy.pokedex.api.PokemonApi
import com.rahulc0dy.pokedex.services.NamedAPIResource
import kotlinx.coroutines.launch

class PokedexViewModel : ViewModel() {
    var pokemons by mutableStateOf<List<NamedAPIResource>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            pokemons = PokemonApi.service.listPokemons().results
        }
    }
}
