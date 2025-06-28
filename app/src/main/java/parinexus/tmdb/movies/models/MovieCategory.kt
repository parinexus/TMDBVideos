package parinexus.tmdb.movies.models

enum class MovieCategory(val apiConst: String) {
    Popular(parinexus.tmdb.movies.data.utils.Constant.POPULAR),
    TopRated(parinexus.tmdb.movies.data.utils.Constant.TOP_RATED),
    Upcoming(parinexus.tmdb.movies.data.utils.Constant.UPCOMING)
}