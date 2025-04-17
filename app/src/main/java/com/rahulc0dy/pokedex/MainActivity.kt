package com.rahulc0dy.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rahulc0dy.pokedex.ui.screens.PokedexList
import com.rahulc0dy.pokedex.ui.screens.PokemonDetailScreen
import com.rahulc0dy.pokedex.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val navController = rememberNavController()
                        NavHost(navController, startDestination = "list") {
                            composable("list") {
                                PokedexList(onSelect = { id ->
                                    navController.navigate("detail/$id")
                                })
                            }
                            composable("detail/{id}") { back ->
                                val id =
                                    back.arguments?.getString("id")?.toInt() ?: return@composable
                                PokemonDetailScreen(pokemonId = id)
                            }
                        }
                    }
                }

            }
        }
    }
}
