package com.rahulc0dy.pokedex.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.rahulc0dy.pokedex.api.PokemonApi
import com.rahulc0dy.pokedex.services.PokemonDetail

@Composable
fun PokemonDetailScreen(pokemonId: Int) {
    var detail by remember { mutableStateOf<PokemonDetail?>(null) }
    LaunchedEffect(pokemonId) {
        detail = PokemonApi.service.fetchPokemonDetail(pokemonId)
    }

    if (detail == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val p = detail!!
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(p.name.replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))

            // 3×2 grid of sprites
            val spriteUrls = listOfNotNull(
                p.sprites.frontDefault to "Front",
                p.sprites.backDefault to "Back",
                p.sprites.frontShiny to "Shiny Front",
                p.sprites.backShiny to "Shiny Back"
            )
            LazyVerticalGrid (
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(250.dp)
            ) {
                items(spriteUrls) { (url, label) ->
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = url,
                            contentDescription = "${p.name} $label",
                            loading = { CircularProgressIndicator(modifier = Modifier.size(24.dp)) },
                            error = {
                                AsyncImage(
                                    model = "https://placehold.co/100",
                                    contentDescription = "placeholder"
                                )
                            },
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(label, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Other details
            Text("ID: ${p.id}", style = MaterialTheme.typography.bodyLarge)
            Text("Height: ${p.height / 10.0} m", style = MaterialTheme.typography.bodyLarge)
            Text("Weight: ${p.weight / 10.0} kg", style = MaterialTheme.typography.bodyLarge)

            Spacer(Modifier.height(12.dp))

            // Types
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                p.types.sortedBy { it.slot }.forEach { slot ->
                    Card(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(slot.type.name.replaceFirstChar { it.uppercase() },modifier = Modifier.padding(10.dp))
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Abilities
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Abilities:", style = MaterialTheme.typography.titleMedium)
                p.abilities.sortedBy { it.slot }.forEach { slot ->
                    val name = slot.ability.name.replace('-', ' ').replaceFirstChar { it.uppercase() }
                    val hiddenTag = if (slot.isHidden) "(hidden)" else ""
                    Text("• $name $hiddenTag", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
