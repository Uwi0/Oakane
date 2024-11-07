package com.kakapo.oakane.common.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException

fun Context.saveImageUri(uri: Uri): Result<String> = runCatching {
    val fileName = "image-oakane${System.currentTimeMillis()}.jpg"
    val inputStream = contentResolver.openInputStream(uri)
    val outputStream = openFileOutput(fileName, MODE_PRIVATE)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    val savedFile = File(filesDir, fileName)
    if (savedFile.exists()) fileName
    else throw FileNotFoundException("File not found after saving")
}

fun Context.getSavedImageUri(fileName: String): Result<Uri> = runCatching {
    val file = getFileStreamPath(fileName)
    FileProvider.getUriForFile(this, "$packageName.provider", file)
}