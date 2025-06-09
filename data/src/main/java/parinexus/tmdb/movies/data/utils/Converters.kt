package parinexus.tmdb.movies.data.utils

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromGenreList(genres: List<Int>): String = genres.joinToString(",")

    @TypeConverter
    fun toGenreList(genreString: String): List<Int> =
        if (genreString.isBlank()) emptyList() else genreString.split(",").mapNotNull { it.toIntOrNull() }
}
