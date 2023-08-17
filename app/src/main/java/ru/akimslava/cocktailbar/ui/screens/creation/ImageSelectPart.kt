package ru.akimslava.cocktailbar.ui.screens.creation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.domain.saveImageToInternalStorage
import java.io.File


@Composable
fun ImageSelect(
    picture: String?,
    onUpload: (String) -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            val filename = uri?.path?.let(::File)?.name ?: "image.png"
            uri?.let {
                saveImageToInternalStorage(context, uri, filename)
            }
            onUpload("${context.filesDir}/${filename}")
        },
    )
    val onImageClick = { launcher.launch("image/*") }
    if (picture == null) {
        NoImageSelectedPlaceHolder(
            modifier = Modifier.clickable { onImageClick() },
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(File(picture))
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_image_search),
            error = painterResource(id = R.drawable.ic_no_image),
            modifier = Modifier
                .height(204.dp)
                .width(216.dp)
                .clip(RoundedCornerShape(54.dp))
                .clickable { onImageClick() },
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun NoImageSelectedPlaceHolder(
    modifier: Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.edit_image),
            contentDescription = null,
        )
        Icon(
            painter = painterResource(id = R.drawable.photik),
            contentDescription = null,
        )
    }
}