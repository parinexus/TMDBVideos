package parinexus.tmdb.movies.di

import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.data.dataSources.remote.MoviesApi
import parinexus.tmdb.movies.data.repository.MoviesRepositoryImpl
import parinexus.tmdb.movies.domain.repository.MoviesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApi: MoviesApi,
        movieDatabase: MovieDatabase
    ): MoviesRepository {
        return MoviesRepositoryImpl(movieApi, movieDatabase)
    }

}