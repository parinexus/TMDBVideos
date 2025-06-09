package parinexus.tmdb.movies.data.dataSources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import parinexus.tmdb.movies.data.utils.Constant.MOVIES_TABLE

@Entity(tableName = MOVIES_TABLE)
data class MovieEntity(
    @PrimaryKey val id: Int,
    val category: String,

    val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "genre_ids") val genreIds: String,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    val title: String,
    val video: Boolean,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int
)