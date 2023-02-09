package com.example.pixabaywallpapers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pixabaywallpapers.model.Resource
import com.example.pixabaywallpapers.ui.screens.*
import com.example.pixabaywallpapers.ui.theme.PixabayWallpapersTheme
import com.example.pixabaywallpapers.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallpaperApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperApp() {

    val mainViewModel: MainViewModel = viewModel()

    PixabayWallpapersTheme {
        val navController = rememberNavController()

        Scaffold() { innerPadding ->
            WallpaperNavHost(
                navController = navController,
                viewModel = mainViewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun WallpaperNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val categoryResponse = viewModel.categoryResponse.collectAsState()
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = WallpaperScreen.Splash.name,
        modifier = modifier
    ) {
        composable(WallpaperScreen.Splash.name) {
            SplashScreen(
                onSplashComplete = {
                    navController.popBackStack()
                    navController.navigate(WallpaperScreen.CategoryGrid.name)
                }
            )
        }
        composable(WallpaperScreen.CategoryGrid.name) {
            CategoryGridScreen(
                onCategoryClick = {
                    viewModel.requestCategory(it.name)
                    navController.navigate("${WallpaperScreen.Category.name}/${it.name}")
                }
            )
        }
        composable(
            route = "${WallpaperScreen.Category.name}/{name}",
            arguments = listOf(navArgument("name") {type = NavType.StringType})
        ) { entry ->
            val categoryName = entry.arguments!!.getString("name")!!
            CategoryScreen(
                category = categoryName,
                pixabayResponse = categoryResponse.value,
                onPhotoClick = { navController.navigate("${WallpaperScreen.Category.name}/$categoryName/${it.id}") }
            )
        }
        composable(
            route = "${WallpaperScreen.Category.name}/{name}/{id}",
            arguments = listOf(navArgument("id") {type = NavType.IntType})
        ) { entry ->
            val photoId = entry.arguments!!.getInt("id")
            if (categoryResponse.value is Resource.Success) {
                PictureScreen(
                    imageUrl = categoryResponse.value.data!!.hits.find { it.id == photoId }!!.largeImageURL,
                    scope = scope
                )
            }

        }
    }
}