package parinexus.tmdb.movies.data.dataSources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import parinexus.tmdb.movies.data.utils.Constant.MOVIES_CAST_TABLE

@Entity(tableName = MOVIES_CAST_TABLE)
data class MovieCastEntity(
    @PrimaryKey val id: Int,
    val movieId: Int,

    val adult: Boolean,
    @ColumnInfo(name = "cast_id") val castId: Int,
    val character: String,
    @ColumnInfo(name = "credit_id") val creditId: String,
    val gender: Int,
    @ColumnInfo(name = "known_for_department") val knownForDepartment: String,
    val name: String,
    val order: Int,
    @ColumnInfo(name = "original_name") val originalName: String,
    val popularity: Double,
    @ColumnInfo(name = "profile_path") val profilePath: String
)