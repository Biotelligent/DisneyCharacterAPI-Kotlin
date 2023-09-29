
package com.biotelligent.disneyapi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Database(entities = [DisneyCharacter::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun disneyCharacterDao(): DisneyCharacterDao
}

class Converters {
    @OptIn(ExperimentalSerializationApi::class)
    @TypeConverter
    fun fromList(value: List<String>) = Json.encodeToString(value)

    @OptIn(ExperimentalSerializationApi::class)
    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)
}