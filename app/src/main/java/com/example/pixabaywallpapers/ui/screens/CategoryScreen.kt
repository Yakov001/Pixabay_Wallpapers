package com.example.pixabaywallpapers.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pixabaywallpapers.model.CategoryResponse
import com.example.pixabaywallpapers.model.Hit
import com.example.pixabaywallpapers.model.Resource

@Composable
fun CategoryScreen(
    category: String,
    pixabayResponse: Resource<CategoryResponse>,
    onPhotoClick: (Hit) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Text(text = category.capitalize())
        }
        if (pixabayResponse is Resource.Success) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {
                items(pixabayResponse.data!!.hits) {
                    PhotoItem(
                        photoUrl = it.previewURL,
                        onPhotoClick = { onPhotoClick(it) }
                    )
                }
            }
        } else if (pixabayResponse is Resource.Loading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun PhotoItem(
    photoUrl: String,
    onPhotoClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .clickable { onPhotoClick() }
                .width(128.dp)
                .height(228.dp),
            model = photoUrl,
            contentScale = ContentScale.Crop,
            contentDescription = "Photo Preview"
        )
    }
}