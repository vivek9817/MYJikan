package com.example.myjikan.Model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class AnimDetailsResponse(
    val data: AnimDetailsResponseObj,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class AnimDetailsResponseObj(
    val mal_id: Long,
    val url: String,
    val images: Images,
    val trailer: Trailers,
    val approved: Boolean,
    val titles: List<Titles>,
    val title: String,
    val title_english: String,
    val title_japanese: String,
    val title_synonyms: List<String>,
    val type: String,
    val source: String,
    val episodes: Long,
    val status: String,
    val airing: Boolean,
    val aired: Airedes,
    val duration: String,
    val rating: String,
    val score: Double,
    val scored_by: Long,
    val rank: Long,
    val popularity: Long,
    val members: Long,
    val favorites: Long,
    val synopsis: String,
    val background: String,
    val season: String,
    val year: Long,
    val broadcast: Broadcasted,
    val producers: List<Producer>,
    val licensors: List<Genre>,
    val studios: List<Genre>,
    val genres: List<Genre>,
    val explicit_genres:  List<Producer>,
    val themes:  List<Producer>,
    val demographics: List<Genre>,
):Parcelable

@kotlinx.parcelize.Parcelize
data class Webp(
    val image_url: String,
    val small_image_url: String,
    val large_image_url: String,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Trailers(
    val youtube_id: String,
    val url: String,
    val embed_url: String,
    val images: ImagesToo,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class ImagesToo(
    val image_url: String,
    val small_image_url: String,
    val medium_image_url: String,
    val large_image_url: String,
    val maximum_image_url: String,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Titles(
    val type: String,
    val title: String,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Airedes(
    val from: String,
    val to: String,
    val prop: Props,
    val string: String,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Props(
    val from: From,
    val to: To,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class FromTo(
    val day: Long,
    val month: Long,
    val year: Long,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Broadcasted(
    val day: String,
    val time: String,
    val timezone: String,
    val string: String,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Genre(
    val mal_id: Long,
    val type: String,
    val name: String,
    val url: String,
) : Parcelable
