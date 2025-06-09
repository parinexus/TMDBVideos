package parinexus.tmdb.movies.data.dataSources.remote

import parinexus.tmdb.movies.data.dataSources.remote.models.movie.MoviesResponse
import parinexus.tmdb.movies.data.dataSources.remote.models.movieCast.MovieCastResponse
import parinexustmdb.movies.data.BuildConfig.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
    ): MoviesResponse

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MoviesResponse

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("sort_by") sortBy: String = "vote_count.desc",
    ): MoviesResponse


    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieCastResponse


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MoviesResponse

}