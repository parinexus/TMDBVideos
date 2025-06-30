package parinexus.tmdb.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import parinexus.tmdb.movies.domain.repository.MoviesRepository
import parinexus.tmdb.movies.domain.usecase.FetchMovieCastUseCase
import parinexus.tmdb.movies.domain.usecase.GetMoviesByCategoryUseCase
import parinexus.tmdb.movies.domain.usecase.GetTrendingMoviesUseCase
import parinexus.tmdb.movies.domain.usecase.RefreshTrendingMoviesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCase(
        moviesRepository: MoviesRepository,
    ): GetTrendingMoviesUseCase {
        return GetTrendingMoviesUseCase(
            moviesRepository
        )
    }

    @Provides
    @Singleton
    fun provideMoviesByCategoryUseCase(
        moviesRepository: MoviesRepository,
    ): GetMoviesByCategoryUseCase {
        return GetMoviesByCategoryUseCase(
            moviesRepository
        )
    }

    @Provides
    @Singleton
    fun provideRefreshTrendingMoviesUseCase(
        moviesRepository: MoviesRepository,
    ): RefreshTrendingMoviesUseCase {
        return RefreshTrendingMoviesUseCase(
            moviesRepository
        )
    }

    @Provides
    @Singleton
    fun provideFetchMovieCastUseCase(
        moviesRepository: MoviesRepository,
    ): FetchMovieCastUseCase {
        return FetchMovieCastUseCase(
            moviesRepository
        )
    }
}