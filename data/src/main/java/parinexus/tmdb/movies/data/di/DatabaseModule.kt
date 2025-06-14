package parinexus.tmdb.movies.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDao
import parinexus.tmdb.movies.data.dataSources.local.database.MovieDatabase
import parinexus.tmdb.movies.data.utils.Constant.MOVIES_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MovieDatabase::class.java,
            name = MOVIES_DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideMovieDao(
        movieDatabase: MovieDatabase,
    ): MovieDao = movieDatabase.movieDao

}