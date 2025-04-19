package com.rahulc0dy.pokedex.datamodels

import com.google.gson.annotations.SerializedName

data class PokemonSpecies(
    val id: Int,
    val name: String,
    val order: Int,
    @SerializedName("gender_rate") val genderRate: Int,
    @SerializedName("capture_rate") val captureRate: Int,
    @SerializedName("base_happiness") val baseHappiness: Int,
    @SerializedName("is_baby") val isBaby: Boolean,
    @SerializedName("is_legendary") val isLegendary: Boolean,
    @SerializedName("is_mythical") val isMythical: Boolean,
    @SerializedName("hatch_counter") val hatchCounter: Int,
    @SerializedName("has_gender_differences") val hasGenderDifferences: Boolean,
    @SerializedName("forms_switchable") val formsSwitchable: Boolean,
    val generation: NamedAPIResource,
    @SerializedName("growth_rate") val growthRate: NamedAPIResource,
    @SerializedName("evolution_chain") val evolutionChain: ChainReference,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorText>?,
    @SerializedName("habitat") val habitat: NamedAPIResource?
)

data class ChainReference(
    val url: String
)

data class FlavorText(
    @SerializedName("flavor_text") val text: String,
    val language: NamedAPIResource,
    val version: NamedAPIResource
)
