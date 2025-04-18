package com.rahulc0dy.pokedex.datamodels

import com.google.gson.annotations.SerializedName

data class NamedAPIResource(val name: String, val url: String)

data class GameIndex(
    @SerializedName("game_index") val gameIndex: Int,
    val version: NamedAPIResource
)

data class VersionDetail(
    val rarity: Int,
    val version: NamedAPIResource
)

data class HeldItem(
    val item: NamedAPIResource,
    @SerializedName("version_details") val versionDetails: List<VersionDetail>
)

data class MoveVersionDetail(
    @SerializedName("level_learned_at") val level: Int,
    @SerializedName("move_learn_method") val method: NamedAPIResource,
    @SerializedName("version_group") val versionGroup: NamedAPIResource
)

data class PokemonMove(
    val move: NamedAPIResource,
    @SerializedName("version_group_details") val versionGroupDetails: List<MoveVersionDetail>
)

data class StatSlot(
    @SerializedName("base_stat") val baseStat: Int,
    val effort: Int,
    val stat: NamedAPIResource
)

data class TypeSlot(val slot: Int, val type: NamedAPIResource)
data class AbilitySlot(
    @SerializedName("is_hidden") val isHidden: Boolean,
    val slot: Int,
    val ability: NamedAPIResource
)

data class PokemonTypePast(
    val generation: NamedAPIResource,
    val types: List<TypeSlot>
)

// **UPDATED** for null-safe past abilities:
data class AbilitySlotPast(
    @SerializedName("is_hidden") val isHidden: Boolean,
    val slot: Int,
    val ability: NamedAPIResource?    // nullable!
)

data class PokemonAbilityPast(
    val generation: NamedAPIResource,
    @SerializedName("abilities") val abilitySlots: List<AbilitySlotPast>?
)

data class OtherSprites(
    @SerializedName("official-artwork") val official: ArtworkSprite
)

data class ArtworkSprite(@SerializedName("front_default") val image: String?)
data class ExtendedSprites(
    @SerializedName("front_default") val front: String?,
    @SerializedName("back_default") val back: String?,
    @SerializedName("front_shiny") val shinyFront: String?,
    @SerializedName("back_shiny") val shinyBack: String?,
    val other: OtherSprites
)

data class PokemonCries(val latest: String?, val legacy: String?)

data class PokemonDetail(
    val id: Int,
    val name: String,
    @SerializedName("base_experience") val baseExperience: Int,
    val height: Int,
    val weight: Int,
    @SerializedName("is_default") val isDefault: Boolean,
    val order: Int,
    val species: NamedAPIResource,
    val forms: List<NamedAPIResource>,
    @SerializedName("game_indices") val gameIndices: List<GameIndex>,
    @SerializedName("held_items") val heldItems: List<HeldItem>,
    @SerializedName("location_area_encounters") val encountersUrl: String,
    val moves: List<PokemonMove>,
    @SerializedName("past_types") val pastTypes: List<PokemonTypePast>,
    @SerializedName("past_abilities") val pastAbilities: List<PokemonAbilityPast>?,
    val abilities: List<AbilitySlot>,
    val sprites: ExtendedSprites,
    val stats: List<StatSlot>,
    val types: List<TypeSlot>,
    val cries: PokemonCries?
)
