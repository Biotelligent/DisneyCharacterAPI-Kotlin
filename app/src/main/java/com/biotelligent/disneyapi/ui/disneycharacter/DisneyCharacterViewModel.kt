package com.biotelligent.disneyapi.ui.disneycharacter

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biotelligent.disneyapi.data.DisneyCharacterRepository
import com.biotelligent.disneyapi.data.local.database.DisneyCharacter
import com.biotelligent.disneyapi.ui.disneycharacter.DisneyCharacterUiState.Error
import com.biotelligent.disneyapi.ui.disneycharacter.DisneyCharacterUiState.Loading
import com.biotelligent.disneyapi.ui.disneycharacter.DisneyCharacterUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject

private fun JSONArray.toStringList(): List<String> {
    val stringList = mutableListOf<String>()
    for (i in 0 until length()) {
        val item = optString(i)
        if (!item.isNullOrEmpty()) {
            stringList.add(item)
        }
    }
    return stringList
}

@HiltViewModel
class DisneyCharacterViewModel
    @Inject
    constructor(
        @ApplicationContext val appContext: Context,
        private val disneyCharacterRepository: DisneyCharacterRepository
    ) : ViewModel() {
        init {
            // TODO: if we want to reset the database, or debugging && the (disneyCharacterRepository.characterCount == 0), loadSampleData
            loadSampleData()
        }

        val uiState: StateFlow<DisneyCharacterUiState> =
            disneyCharacterRepository
                .disneyCharacters.map<List<DisneyCharacter>, DisneyCharacterUiState>(::Success)
                .catch { emit(Error(it)) }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

        /**
         * Debugging; load sample data from assets/sampledata.json
         */
        private fun loadSampleData() {
            viewModelScope.launch {
                try {
                    val json = loadJSONFromAsset(appContext, "sampledata.json")
                    val jsonArray = JSONArray(json)

                    val disneyCharacters = mutableListOf<DisneyCharacter>()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val id = jsonObject.getInt("_id")

                        val disneyCharacter =
                            DisneyCharacter(
                                id = id,
                                name = jsonObject.getString("name"),
                                imageUrl = jsonObject.getString("imageUrl"),
                                updatedAt = jsonObject.getString("updatedAt"),
                                url = jsonObject.getString("url"),
                                films = jsonObject.getJSONArray("films").toStringList(),
                                shortFilms = jsonObject.getJSONArray("shortFilms").toStringList(),
                                parkAttractions = jsonObject.getJSONArray("parkAttractions").toStringList()
                            )
                        disneyCharacters.add(disneyCharacter)
                    }

                    disneyCharacterRepository.loadData(disneyCharacters)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        private fun loadJSONFromAsset(
            context: Context,
            fileName: String
        ): String {
            val json: String
            try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charset.forName("UTF-8"))
            } catch (ex: IOException) {
                ex.printStackTrace()
                return ""
            }
            return json
        }
    }

sealed interface DisneyCharacterUiState {
    object Loading : DisneyCharacterUiState

    data class Error(val throwable: Throwable) : DisneyCharacterUiState

    data class Success(val data: List<DisneyCharacter>) : DisneyCharacterUiState
}