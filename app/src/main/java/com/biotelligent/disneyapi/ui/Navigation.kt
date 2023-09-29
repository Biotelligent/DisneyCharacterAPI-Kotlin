
package com.biotelligent.disneyapi.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.biotelligent.disneyapi.ui.disneycharacter.DisneyCharacterScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { DisneyCharacterScreen() }
    }
}