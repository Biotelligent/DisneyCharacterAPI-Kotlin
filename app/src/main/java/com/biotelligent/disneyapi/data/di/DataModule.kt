
package com.biotelligent.disneyapi.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.biotelligent.disneyapi.data.DisneyCharacterRepository
import com.biotelligent.disneyapi.data.DefaultDisneyCharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsDisneyCharacterRepository(
        disneyCharacterRepository: DefaultDisneyCharacterRepository
    ): DisneyCharacterRepository
}

