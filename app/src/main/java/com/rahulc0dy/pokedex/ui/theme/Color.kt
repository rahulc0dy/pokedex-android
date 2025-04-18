package com.rahulc0dy.pokedex.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object PokemonTypeColor {
    val NormalType = Color(0xFFAAB09F)
    val FightingType = Color(0xFFCB5F48)
    val FlyingType = Color(0xFF7DA6DE)
    val PoisonType = Color(0xFFB468B7)
    val GroundType = Color(0xFFCC9F4F)
    val RockType = Color(0xFFB2A061)
    val BugType = Color(0xFF94BC4A)
    val GhostType = Color(0xFF846AB6)
    val SteelType = Color(0xFF89A1B0)
    val FireType = Color(0xFFEA7A3C)
    val WaterType = Color(0xFF539AE2)
    val GrassType = Color(0xFF71C558)
    val ElectricType = Color(0xFFE5C531)
    val PsychicType = Color(0xFFE5709B)
    val IceType = Color(0xFF70CBD4)
    val DragonType = Color(0xFF6A7BAF)
    val DarkType = Color(0xFF736C75)
    val FairyType = Color(0xFFE397D1)
    val StellarType = Color(0xFFCCCCCC)

    val UnknownType = Color(0xFF81A596)
}


fun getColorByType(type: String = "normal"): Color {
    return when (type.lowercase().trim()) {
        "normal" -> PokemonTypeColor.NormalType
        "fighting" -> PokemonTypeColor.FightingType
        "flying" -> PokemonTypeColor.FlyingType
        "poison" -> PokemonTypeColor.PoisonType
        "ground" -> PokemonTypeColor.GroundType
        "rock" -> PokemonTypeColor.RockType
        "bug" -> PokemonTypeColor.BugType
        "ghost" -> PokemonTypeColor.GhostType
        "steel" -> PokemonTypeColor.SteelType
        "fire" -> PokemonTypeColor.FireType
        "water" -> PokemonTypeColor.WaterType
        "grass" -> PokemonTypeColor.GrassType
        "electric" -> PokemonTypeColor.ElectricType
        "psychic" -> PokemonTypeColor.PsychicType
        "ice" -> PokemonTypeColor.IceType
        "dragon" -> PokemonTypeColor.DragonType
        "dark" -> PokemonTypeColor.DarkType
        "fairy" -> PokemonTypeColor.FairyType
        "stellar" -> PokemonTypeColor.StellarType
        "unknown" -> PokemonTypeColor.UnknownType
        else -> PokemonTypeColor.UnknownType
    }
}
