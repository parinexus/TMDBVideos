package parinexus.tmdb.movies.data.dataSources.remote.models.movieCast

import com.google.gson.annotations.SerializedName

data class MovieCastResponse(
    @SerializedName("cast")
    val cast: List<MovieCastDto> = emptyList(),

    @SerializedName("crew")
    val crew: List<MovieCrewDto> = emptyList(),

    @SerializedName("id")
    val movieId: Int = 0
)

data class MovieCastDto(
    @SerializedName("adult")
    val adult: Boolean = false,

    @SerializedName("cast_id")
    val castId: Int = 0,

    @SerializedName("character")
    val character: String = "",

    @SerializedName("credit_id")
    val creditId: String = "",

    @SerializedName("gender")
    val gender: Int = 0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("known_for_department")
    val knownForDepartment: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("order")
    val order: Int = 0,

    @SerializedName("original_name")
    val originalName: String = "",

    @SerializedName("popularity")
    val popularity: Double = 0.0,

    @SerializedName("profile_path")
    val profilePath: String? = null
)

data class MovieCrewDto(
    @SerializedName("adult")
    val adult: Boolean = false,

    @SerializedName("credit_id")
    val creditId: String = "",

    @SerializedName("department")
    val department: String = "",

    @SerializedName("gender")
    val gender: Int = 0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("job")
    val job: String = "",

    @SerializedName("known_for_department")
    val knownForDepartment: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("original_name")
    val originalName: String = "",

    @SerializedName("popularity")
    val popularity: Double = 0.0,

    @SerializedName("profile_path")
    val profilePath: String? = null
)
