package com.example.pixabaywallpapers.ui.screens

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PictureScreen(
    imageUrl: String,
    scope: CoroutineScope
) {
    val context = LocalContext.current
    val bm = rememberSaveable { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(key1 = Any()) {
        scope.launch {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .allowHardware(false) // Disable hardware bitmaps.
                .build()
            val result = (loader.execute(request) as SuccessResult).drawable
            bm.value = (result as BitmapDrawable).bitmap
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { bm.value?.let { WallpaperManager.getInstance(context).setBitmap(it) } },
            enabled = bm.value != null,
            content = { Text("Set as background") }
        )
        bm.value?.let {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = it.asImageBitmap(),
                contentScale = ContentScale.Crop,
                contentDescription = "Picture Full Size"
            )
        } ?: Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}