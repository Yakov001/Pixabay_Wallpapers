package com.example.pixabaywallpapers

enum class WallpaperScreen {
    CategoryGrid,
    Category,
    Splash;

    companion object {
        fun fromRoute(route: String?): WallpaperScreen =
            when (route?.substringBefore("/")) {
                CategoryGrid.name -> CategoryGrid
                Category.name -> Category
                Splash.name -> Splash
                null -> CategoryGrid
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}