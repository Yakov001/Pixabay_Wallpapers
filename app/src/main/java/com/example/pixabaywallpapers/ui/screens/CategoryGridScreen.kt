package com.example.pixabaywallpapers.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pixabaywallpapers.ui.theme.PixabayWallpapersTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun CategoryGridScreen(
    onCategoryClick: (Category) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        var i = 0
        while (i < categories.size - 1) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) {
                    val category = categories.getOrNull(i) ?: return@repeat
                    CategoryIcon(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        category = category,
                        onCategoryClick = { onCategoryClick(category) }
                        )
                    i++
                }
            }
        }

    }
}

@Composable
fun CategoryIcon(
    modifier: Modifier = Modifier,
    category: Category,
    onCategoryClick: (Category) -> Unit
) {
    Card(
        modifier = modifier.clickable { onCategoryClick(category) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .alpha(alpha = 1f),
                imageVector = category.icon,
                contentDescription = category.name,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(text = category.name.capitalize())
        }
    }
}

@Composable
@Preview
fun CategoryScreenPreview() {
    PixabayWallpapersTheme() {
        CategoryGridScreen(
            onCategoryClick = {}
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CategoryScreenDarkPreview() {
    PixabayWallpapersTheme() {
        CategoryGridScreen(
            onCategoryClick = {}
        )
    }
}

class Category(
    val name: String,
    val icon: ImageVector
)

val categories = listOf<Category>(
    Category("backgrounds", Icons.Default.Wallpaper),
    Category("nature", Icons.Default.Forest),
    Category("science", Icons.Default.Science),
    Category("animals", Icons.Default.Pets),
    Category("sports", Icons.Default.SportsSoccer),
    Category("food", Icons.Default.LunchDining),
    Category("buildings", Icons.Default.LocationCity),
    Category("music", Icons.Default.MusicNote)
)