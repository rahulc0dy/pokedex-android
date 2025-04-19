package com.rahulc0dy.pokedex.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.rahulc0dy.pokedex.datamodels.ChainLink
import com.rahulc0dy.pokedex.datamodels.EvolutionChain
import com.rahulc0dy.pokedex.datamodels.NamedAPIResource

@Composable
fun EvolutionTimeline(
    evolutionChain: EvolutionChain,
    onSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RenderEvolutionChain(evolutionChain.chain, onSelect)
    }
}

@Composable
fun RenderEvolutionChain(
    chainLink: ChainLink,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Display current species
        val species = chainLink.species
        val pokemonId = extractPokemonIdFromUrl(species.url)
        EvolutionNode(
            species = species,
            pokemonId = pokemonId,
            onClick = { onSelect(species.name) })

        // Check if there are evolutions - list will be empty if no evolutions
        if (chainLink.evolvesTo.isNotEmpty()) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Evolves to",
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Handle branches - show each evolution path
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                chainLink.evolvesTo.forEach { evolution ->
                    RenderEvolutionChain(evolution, onSelect)
                }
            }
        }
    }
}

@Composable
fun EvolutionNode(species: NamedAPIResource, pokemonId: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        // Use the correct image URL format for official artwork
        SubcomposeAsyncImage(
            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png",
            contentDescription = species.name,
            modifier = Modifier.size(80.dp),
            loading = { CircularProgressIndicator(Modifier.size(24.dp)) }
        )
        Text(
            text = species.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

// Improved helper function to extract Pokémon ID from URL
fun extractPokemonIdFromUrl(url: String): String {
    // URL format: https://pokeapi.co/api/v2/pokemon-species/{id}/
    val segments = url.trim('/').split('/')
    // Find the last numeric segment which should be the ID
    return segments.last { it.all { char -> char.isDigit() } }
}
