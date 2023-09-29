package com.biotelligent.disneyapi.data

import com.biotelligent.disneyapi.data.local.database.DisneyCharacter
import com.biotelligent.disneyapi.data.local.database.DisneyCharacterDao
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface DisneyCharacterRepository {
    val disneyCharacters: Flow<List<DisneyCharacter>>

    suspend fun loadData(disneyCharacters: List<DisneyCharacter>)
}

class DefaultDisneyCharacterRepository
    @Inject
    constructor(
        private val disneyCharacterDao: DisneyCharacterDao
    ) : DisneyCharacterRepository {
        override val disneyCharacters: Flow<List<DisneyCharacter>> =
            disneyCharacterDao.getDisneyCharacters()

        /**
         * Loads disney character data into the database;
         * TODO: Replace with API result using refit, checking updated_at to determine if record should be replaced
         */
        @OptIn(DelicateCoroutinesApi::class)
        override suspend fun loadData(disneyCharacters: List<DisneyCharacter>) {
            GlobalScope.launch(Dispatchers.IO) {
                disneyCharacterDao.deleteAll()
                disneyCharacters.forEach { disneyCharacterDao.insertDisneyCharacter(it) }
            }
        }
    }