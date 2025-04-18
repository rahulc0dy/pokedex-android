package com.rahulc0dy.pokedex.ui.screens

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.rahulc0dy.pokedex.api.PokemonApi
import com.rahulc0dy.pokedex.datamodels.PokemonDetail
import com.rahulc0dy.pokedex.ui.theme.getColorByType

@Composable
fun PokemonDetailScreen(pokemonId: Int) {
    var detail by remember { mutableStateOf<PokemonDetail?>(null) }
    LaunchedEffect(pokemonId) {
        detail = PokemonApi.service.fetchPokemonDetail(pokemonId)
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        detail?.let { data -> PokemonProfileCard(data) }
            ?: CircularProgressIndicator()
    }
}

@Composable
fun PokemonProfileCard(detail: PokemonDetail) {
    val primaryColor = getColorByType(detail.types.firstOrNull()?.type?.name ?: "normal")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SubcomposeAsyncImage(
                model = detail.sprites.other.official.image,
                contentDescription = "${detail.name} banner",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }

        item {
            HorizontalDivider(thickness = 2.dp, color = primaryColor)
        }

        // 1. Header: name & XP
        item {
            Text(
                text = detail.name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.headlineSmall,
                color = primaryColor
            )
            Text(
                text = "No. ${detail.id} • XP ${detail.baseExperience}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }

        // Types
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                detail.types.forEach { typeSlot ->
                    AssistChip(
                        onClick = { /* maybe filter by type */ },
                        label = {
                            Text(
                                typeSlot.type.name.replaceFirstChar { it.uppercase() },
                                color = getColorByType(typeSlot.type.name)
                            )
                        }
                    )
                }
            }
        }

        // 2. Encounter link
        item {
            val ctx = LocalContext.current
            Text(
                text = "Encounters",
                color = primaryColor,
                modifier = Modifier
                    .clickable {
                        ctx.startActivity(
                            Intent(Intent.ACTION_VIEW, detail.encountersUrl.toUri())
                        )
                    }
                    .padding(vertical = 4.dp)
            )
        }

        // 3. Sprite grid (finite height)
        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .height(500.dp),
                userScrollEnabled = false
            ) {

                detail.sprites.let { s ->
                    listOfNotNull(
                        s.front, s.back, s.shinyFront, s.shinyBack
                    ).forEach { url ->
                        item {
                            SubcomposeAsyncImage(
                                model = url,
                                contentDescription = null,
                                loading = { CircularProgressIndicator(modifier = Modifier.size(24.dp)) },
                                modifier = Modifier
                                    .padding(4.dp)
                                    .border(1.dp, primaryColor, RoundedCornerShape(4.dp))
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                            )
                        }
                    }
                }
            }
        }

        // 4. Forms row

        item {
            Text("Forms")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(detail.forms) { form ->
                    AssistChip(
                        onClick = {},
                        label = { Text(form.name.replaceFirstChar { it.uppercase() }) }
                    )
                }
            }
        }

        // 5. Held items
        item {
            Text("Held Items")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(detail.heldItems) { hi ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${hi.item.name}.png",
                            contentDescription = hi.item.name,
                            modifier = Modifier.size(48.dp)
                        )

                        Text(
                            text = hi.item.name,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Rarity: ${hi.versionDetails.first().rarity}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // 6. Stats row
        item {
            Text("Stats")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                detail.stats.forEach { stat ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stat.stat.name.uppercase(),
                            style = MaterialTheme.typography.bodySmall,
                            color = primaryColor
                        )
                        Text(
                            text = "${stat.baseStat}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // 7. Moves list (static, no nested scrolling)
        item {
            Text("Moves", style = MaterialTheme.typography.titleMedium, color = primaryColor)
        }
        // Instead of nesting a LazyColumn here, use the parent’s items:
        items(detail.moves) { move ->
            val vgd = move.versionGroupDetails.first()
            Text(
                text = "• ${
                    move.move.name
                        .replace('-', ' ')
                        .replaceFirstChar { it.uppercase() }
                } " +
                        "(Lvl ${vgd.level} via ${
                            vgd.method.name
                                .replace('-', ' ')
                                .replaceFirstChar { it.uppercase() }
                        })",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }

        // 8. Past Types & Abilities (null-safe)
        item {
            Text("Types")
            if (detail.pastTypes.isNotEmpty()) {
                Text(
                    "Past Types:",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
                detail.pastTypes.forEach { past ->
                    Text(
                        text = "• Up to ${past.generation.name.replaceFirstChar { it.uppercase() }}: " +
                                past.types.joinToString { it.type.name },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Text(
                "Past Abilities:",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
            detail.pastAbilities
                .orEmpty()
                .forEach { past ->

                    // safely get a non‐null list
                    val rawNames = past.abilitySlots
                        .orEmpty()
                        .mapNotNull { it.ability?.name }
                        .map { name ->
                            name.replace('-', ' ')
                                .replaceFirstChar { it.uppercase() }
                        }
                    val displayNames =
                        if (rawNames.isNotEmpty()) rawNames.joinToString(", ") else "None"
                    Text(
                        text = "• Up to ${past.generation.name.replaceFirstChar { it.uppercase() }}: $displayNames",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
        }

        // 9. Species & Cry
        item {
            Text("Species: ${detail.species.name}", style = MaterialTheme.typography.bodySmall)
            detail.cries?.latest?.let { url ->
                Text(
                    text = "▶ Play Cry",
                    color = primaryColor,
                    modifier = Modifier.clickable { /* playSound(url) */ }
                )
            }
        }
    }
}
