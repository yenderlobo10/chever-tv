@file:Suppress("unused")

package io.chever.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.chever.data.repository.CollectionRepositoryImpl
import io.chever.data.repository.MovieRepositoryImpl
import io.chever.domain.repository.CollectionRepository
import io.chever.domain.repository.MovieRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCollectionRepository(
        repositoryImpl: CollectionRepositoryImpl
    ): CollectionRepository

    @Binds
    abstract fun provideMovieRepository(
        repositoryImpl: MovieRepositoryImpl
    ): MovieRepository

}