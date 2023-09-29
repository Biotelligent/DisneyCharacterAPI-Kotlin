
package com.biotelligent.disneyapi.data.di

import com.biotelligent.disneyapi.data.DefaultDisneyCharacterRepository
import com.biotelligent.disneyapi.data.DisneyCharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Singleton
    @Binds
    fun bindsDisneyCharacterRepository(disneyCharacterRepository: DefaultDisneyCharacterRepository): DisneyCharacterRepository
}