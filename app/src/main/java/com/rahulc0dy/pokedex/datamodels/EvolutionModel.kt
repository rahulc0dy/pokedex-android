package com.rahulc0dy.pokedex.datamodels

import com.google.gson.annotations.SerializedName

data class EvolutionChain(
    val id: Int,
    @SerializedName("baby_trigger_item") val babyTriggerItem: NamedAPIResource? = null,
    val chain: ChainLink
)

data class ChainLink(
    @SerializedName("is_baby") val isBaby: Boolean,
    val species: NamedAPIResource,
    @SerializedName("evolution_details") val evolutionDetails: List<EvolutionDetail>,
    @SerializedName("evolves_to") val evolvesTo: List<ChainLink>
)

data class EvolutionDetail(
    val item: NamedAPIResource? = null,
    val trigger: NamedAPIResource? = null,
    val gender: Int? = null,
    @SerializedName("held_item") val heldItem: NamedAPIResource? = null,
    @SerializedName("known_move") val knownMove: NamedAPIResource? = null,
    @SerializedName("known_move_type") val knownMoveType: NamedAPIResource? = null,
    val location: NamedAPIResource? = null,
    @SerializedName("min_level") val minLevel: Int? = null,
    @SerializedName("min_happiness") val minHappiness: Int? = null,
    @SerializedName("min_beauty") val minBeauty: Int? = null,
    @SerializedName("min_affection") val minAffection: Int? = null,
    @SerializedName("party_species") val partySpecies: NamedAPIResource? = null,
    @SerializedName("party_type") val partyType: NamedAPIResource? = null,
    @SerializedName("relative_physical_stats") val relativePhysicalStats: Int? = null,
    @SerializedName("time_of_day") val timeOfDay: String? = null,
    @SerializedName("trade_species") val tradeSpecies: NamedAPIResource? = null,
    @SerializedName("needs_overworld_rain") val needsOverworldRain: Boolean = false,
    @SerializedName("turn_upside_down") val turnUpsideDown: Boolean = false
)
