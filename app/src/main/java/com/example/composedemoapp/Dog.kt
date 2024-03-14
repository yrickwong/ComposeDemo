package com.example.composedemoapp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Dog(
    @Json(name = "id") val id: String,
    @Json(name = "url") val url: String,
    @Json(name = "breeds") val breed: List<Breed>
){
    val dogName = breed.first().name
    val lifeSpan = breed.first().lifeSpan
    val temperament = breed.first().temperament
}

@JsonClass(generateAdapter = true)
data class Breed(
    @Json(name = "name") val name: String,
    @Json(name = "life_span") val lifeSpan: String,
    @Json(name = "temperament") val temperament: String
)