package com.rahulc0dy.pokedex.datamodels

import com.google.gson.annotations.SerializedName

data class TypeEffectivenessResponse(
    val id: Int,
    val name: String,
    @SerializedName("damage_relations") val damageRelations: DamageRelations
)

data class DamageRelations(
    @SerializedName("double_damage_from") val doubleDamageFrom: List<NamedAPIResource>,
    @SerializedName("double_damage_to") val doubleDamageTo: List<NamedAPIResource>,
    @SerializedName("half_damage_from") val halfDamageFrom: List<NamedAPIResource>,
    @SerializedName("half_damage_to") val halfDamageTo: List<NamedAPIResource>,
    @SerializedName("no_damage_from") val noDamageFrom: List<NamedAPIResource>,
    @SerializedName("no_damage_to") val noDamageTo: List<NamedAPIResource>
)
