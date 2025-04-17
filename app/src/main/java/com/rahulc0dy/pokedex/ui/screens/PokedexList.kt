package com.rahulc0dy.pokedex.ui.screens

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rahulc0dy.pokedex.viewmodels.PokedexViewModel
import coil3.compose.SubcomposeAsyncImage

private fun extractId(url: String): Int =
    url.trimEnd('/').substringAfterLast('/').toInt()

@Composable
fun PokedexList(onSelect: (Int) -> Unit, viewModel: PokedexViewModel = viewModel()) {
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Welcome to Pokedex!",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.pokemons) { item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(extractId(item.url)) }
                        .padding(16.dp)
                ) {
                    Text(
                        item.name.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.weight(1f)
                    )
                    SubcomposeAsyncImage(
                        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
                            extractId(
                                item.url
                            )
                        }.png",
                        contentDescription = item.name,
                        loading = { CircularProgressIndicator(Modifier.size(24.dp)) }
                    )
                }
            }
        }
    }
}
