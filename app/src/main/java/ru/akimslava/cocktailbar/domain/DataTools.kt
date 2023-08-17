package ru.akimslava.cocktailbar.domain

import android.content.Context
import android.net.Uri

fun saveImageToInternalStorage(
    context: Context,
    uri: Uri,
    filename: String,
) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
}