package parinexus.tmdb.movies.data.dataSources.remote.models.movie

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int = 0,

    @SerializedName("results")
    val results: List<Movie> = emptyList(),

    @SerializedName("total_pages")
    val totalPages: Int = 0,

    @SerializedName("total_results")
    val totalResults: Int = 0
)

data class Movie(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("original_title")
    val originalTitle: String = "",

    @SerializedName("overview")
    val overview: String = "",

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("media_type")
    val mediaType: String = "",

    @SerializedName("adult")
    val adult: Boolean = false,

    @SerializedName("original_language")
    val originalLanguage: String = "",

    @SerializedName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerializedName("popularity")
    val popularity: Double = 0.0,

    @SerializedName("release_date")
    val releaseDate: String = "",

    @SerializedName("video")
    val video: Boolean = false,

    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,

    @SerializedName("vote_count")
    val voteCount: Int = 0
)