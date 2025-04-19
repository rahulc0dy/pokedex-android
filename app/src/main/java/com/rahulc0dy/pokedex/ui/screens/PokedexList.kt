package com.rahulc0dy.pokedex.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.rahulc0dy.pokedex.api.PokemonApi
import com.rahulc0dy.pokedex.api.PokemonService
import com.rahulc0dy.pokedex.composables.SearchBar
import com.rahulc0dy.pokedex.datamodels.PokemonBrief
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(FlowPreview::class)
@Composable
fun PokedexList(
    onSelect: (Int) -> Unit,
    apiService: PokemonService = PokemonApi.service
) {
    // 1) Full initial page
    var initialPage by remember { mutableStateOf<List<PokemonBrief>>(emptyList()) }
    // 2) What to display in the grid right now
    var displayList by remember { mutableStateOf<List<PokemonBrief>>(emptyList()) }
    // 3) The raw search query
    var query by remember { mutableStateOf("") }

    // Fetch your initial page once
    LaunchedEffect(Unit) {
        initialPage = apiService
            .listPokemons(limit = 50, offset = 0)
            .results
            .mapIndexed { idx, r -> PokemonBrief(id = idx + 1, name = r.name) }
        displayList = initialPage
    }


    LaunchedEffect(query) {
        snapshotFlow { query }
            .debounce(300)
            .distinctUntilChanged()
            .collectLatest { name ->
                displayList = if (name.isBlank()) {
                    initialPage
                } else {
                    try {
                        val detail = apiService.fetchPokemonByName(name.lowercase())
                        listOf(PokemonBrief(detail.id, detail.name))
                    } catch (e: Exception) {
                        emptyList()
                    }
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "WELCOME TO POKEDEX",
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        )

        SearchBar(
            query = query,
            onQueryChange = { query = it }
        )

        if (displayList.isEmpty()) {
            // No results placeholder
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No results", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {


                items(displayList) { pokemon ->
                    PokemonCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(pokemon.id) },
                        imageUrl = pokemon.imageUrl,
                        name = pokemon.name
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonCard(modifier: Modifier = Modifier, imageUrl: String, name: String) {
    Card(modifier = modifier.padding(10.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            SubcomposeAsyncImage(
                model = imageUrl,
                modifier = Modifier.size(100.dp),
                contentDescription = name,
                loading = { CircularProgressIndicator(Modifier.size(24.dp)) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = name.replaceFirstChar { it.uppercase() },
                fontSize = 20.sp,
            )
        }
    }
}
