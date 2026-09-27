package com.example.musicstream.model

import com.google.gson.annotations.SerializedName

// Muundo wa data ya wimbo kama inavyorudishwa na Audius API
data class AudiusSearchResponse(
    val data: List<AudiusTrack> = emptyList()
)

data class AudiusTrack(
    val id: String,
    val title: String,
    val user: AudiusUser,
    val artwork: AudiusArtwork? = null,
    val duration: Int = 0
)

data class AudiusUser(
    val name: String
)

data class AudiusArtwork(
    @SerializedName("150x150") val small: String? = null,
    @SerializedName("480x480") val medium: String? = null
)

// Muundo rahisi tunaotumia kwenye UI
data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val artworkUrl: String?,
    val durationSec: Int
)

fun AudiusTrack.toTrack(): Track = Track(
    id = id,
    title = title,
    artist = user.name,
    artworkUrl = artwork?.medium ?: artwork?.small,
    durationSec = duration
)
