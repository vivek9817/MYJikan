package com.example.myjikan.Model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class MovieResponse(
    val pagination: Pagination,
    val data: List<Daum>,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Pagination(
    val last_visible_page: Long,
    val has_next_page: Boolean,
    val current_page: Long,
    val items: Items,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Items(
    val count: Long,
    val total: Long,
    val per_page: Long,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Daum(
    val mal_id: Long,
    val url: String,
    val images: Images,
    val trailer: Trailer,
    val approved: Boolean,
    val titles: List<Title>,
    val title: String,
    val title_english: String?,
    val title_japanese: String,
    val title_synonyms: List<String>,
    val type: String,
    val source: String,
    val episodes: Long,
    val status: String,
    val airing: Boolean,
    val aired: Aired,
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
    val season: String?,
    val year: Long?,
    val broadcast: Broadcast,
    val producers: List<Producer>,
    val licensors: List<Producer>,
    val studios: List<Producer>,
    val genres: List<Producer>,
    val explicit_genres: String? = null,
    val themes: List<Producer>,
    val demographics: List<Producer>,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Images(
    val jpg: Jpg,
    val webp: Jpg,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Jpg(
    val image_url: String,
    val small_image_url: String,
    val large_image_url: String,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Trailer(
    val youtube_id: String?,
    val url: String?,
    val embed_url: String?,
    val images: Images2,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Images2(
    val image_url: String?,
    val small_image_url: String?,
    val medium_image_url: String?,
    val large_image_url: String?,
    val maximum_image_url: String?,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Title(
    val type: String,
    val title: String,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Aired(
    val from: String,
    val to: String?,
    val prop: Prop,
    val string: String,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Prop(
    val from: From,
    val to: To,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class From(
    val day: Long,
    val month: Long,
    val year: Long,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class To(
    val day: Long?,
    val month: Long?,
    val year: Long?,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Broadcast(
    val day: String?,
    val time: String?,
    val timezone: String?,
    val string: String?,
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Producer(
    val mal_id: Long,
    val type: String,
    val name: String,
    val url: String,
) : Parcelable
