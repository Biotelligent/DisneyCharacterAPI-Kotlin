package com.biotelligent.disneyapi.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "characters")
data class DisneyCharacter(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Int,
    val name: String,
    val imageUrl: String,
    val updatedAt: String?,
    val url: String?,
    val films: List<String>,
    val shortFilms: List<String>,
    val parkAttractions: List<String>
) {
    val popularity: Int
        get() = films.size + shortFilms.size + parkAttractions.size

    fun getPopularityCount(): Int {
        return films.size + shortFilms.size + parkAttractions.size
    }

    override fun toString() =
        """
        id: $id name: $name popularity: $popularity
        """.trimIndent()
}

@Dao
interface DisneyCharacterDao {
    @Query("SELECT * FROM characters ORDER BY _id ASC LIMIT 50")
    fun getDisneyCharacters(): Flow<List<DisneyCharacter>>

//  TODO: order by popularity; not valid for SQLite
//    @Query("SELECT name, getPopularityCount() as popularityCount FROM characters ORDER BY popularityCount DESC LIMIT 50")
//    fun getOrderedDisneyCharacters(): Flow<List<DisneyCharacter>>

    @Insert
    suspend fun insertDisneyCharacter(item: DisneyCharacter)

    @Query("DELETE FROM characters")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM characters")
    fun getCount(): Int
}