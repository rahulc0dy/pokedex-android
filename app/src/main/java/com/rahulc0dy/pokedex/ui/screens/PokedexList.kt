package com.rahulc0dy.pokedex.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.SubcomposeAsyncImage
import com.rahulc0dy.pokedex.viewmodels.PokedexViewModel

private fun extractId(url: String): Int =
    url.trimEnd('/').substringAfterLast('/').toInt()

@Composable
fun PokedexList(onSelect: (Int) -> Unit, viewModel: PokedexViewModel = viewModel()) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Welcome to Pokedex!",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(2)
        ) {
            items(viewModel.pokemons) { item ->
                PokemonCard(
                    name = item.name,
                    imageUrl = item.url,
                    modifier = Modifier.clickable { onSelect(extractId(item.url)) }
                )
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
                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
                    extractId(
                        imageUrl
                    )
                }.png",
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
