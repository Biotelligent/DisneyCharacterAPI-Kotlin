
package com.biotelligent.disneyapi.data.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.biotelligent.disneyapi.data.local.database.AppDatabase
import com.biotelligent.disneyapi.data.local.database.DisneyCharacterDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDisneyCharacterDao(appDatabase: AppDatabase): DisneyCharacterDao {
        return appDatabase.disneyCharacterDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "DisneyCharacter"
        ).build()
    }
}
