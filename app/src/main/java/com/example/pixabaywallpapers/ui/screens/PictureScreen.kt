package com.example.pixabaywallpapers.ui.screens

import android.app.WallpaperManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.pixabaywallpapers.ui.theme.PixabayWallpapersTheme
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

    ConstraintLayout {
        val (pic, button) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(pic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            bm.value?.let {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState()),
                    bitmap = it.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Picture Full Size"
                )
            } ?: CircularProgressIndicator()
        }

        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.2f))
                .constrainAs(button) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        ) {
            Button(
                onClick = { bm.value?.let { WallpaperManager.getInstance(context).setBitmap(it) } },
                enabled = bm.value != null,
                content = { Text("Set as background") },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}