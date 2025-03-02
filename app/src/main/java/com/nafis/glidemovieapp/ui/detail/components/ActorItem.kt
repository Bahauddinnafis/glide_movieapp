package com.nafis.glidemovieapp.ui.detail.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nafis.glidemovieapp.movie_detail.domain.models.Cast
import com.nafis.glidemovieapp.ui.GlideImage
import com.nafis.glidemovieapp.ui.detail.DetailViewModel
import com.nafis.glidemovieapp.R

@Composable
fun ActorItem(
    modifier: Modifier = Modifier,
    cast: Cast,
    imageUrl: String,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    Log.d("ActorItem", "Loading image from URL: $imageUrl")
    var startTime by remember { mutableStateOf(0L) }
    val existingLoadingTime = detailViewModel.getLoadingTime(cast.name, imageUrl)

    LaunchedEffect(imageUrl) {
        startTime = 0L
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cek apakah profilePath null atau kosong
        val validImageUrl = if (cast.profilePath.isNullOrEmpty()) {
            // Gunakan URL placeholder jika profilePath null atau kosong
            "https://via.placeholder.com/92"
        } else {
            imageUrl
        }

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            GlideImage(
                imageUrl = validImageUrl,
                contentDescription = cast.name,
                modifier = Modifier.size(72.dp),
                contentScale = ContentScale.Crop,
                onLoading = {
                   if (existingLoadingTime == null) {
                       Log.d("ImageURL", "Image URL: $imageUrl")
                       startTime = System.nanoTime()
                       Log.d("LoadingTime", "Start loading image for ${cast.name}")
                   }
                },
                onSuccess = { bitmap ->
                    if (startTime == 0L) {
                        Log.w("ActorItem", "Loading time skipped (cached image?)")
                        return@GlideImage
                    }
                    val endTime = System.nanoTime()
                    val loadingTime = (endTime - startTime) / 1_000_000

                    val runtime = Runtime.getRuntime()
                    val usedMemInMB = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)

                    Log.d("LoadingTime", "Glide loading time for ${cast.name}: $loadingTime ms")
                    Log.d("MemoryUsage", "Memori setelah loading ${cast.firstName}: ${usedMemInMB} MB")
                    detailViewModel.addLoadingTime(cast.name, imageUrl, loadingTime)
                },
                onError = { error ->
                    if (startTime == 0L) {
                        Log.w("ActorItem", "Error but startTime not set")
                        return@GlideImage
                    }
                    val endTime = System.nanoTime()
                    val loadingTime = (endTime - startTime) / 1_000_000
                    Log.d("LoadingTime", "Glide loading failed for ${cast.name}: $loadingTime ms")
                    detailViewModel.addLoadingTime(cast.name, imageUrl, loadingTime)
                },
                placeholder = R.drawable.baseline_person_24
            )
        }
        // Gender Role (Actor / Actress)
        Text(text = cast.genderRole, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = cast.firstName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = cast.lastName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

    }
}