package com.kakapo.common

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import co.touchlab.kermit.Logger
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

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

fun Context.saveImageUriToPublicDirectory(uri: Uri): Result<String> = runCatching {
    val fileName = "image-oakane${System.currentTimeMillis()}.jpg"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/Oakane")
        }
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            ?: throw FileNotFoundException("Failed to create MediaStore entry")
        contentResolver.openOutputStream(imageUri)?.use { outputStream ->
            contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        fileName
    } else {
        val picturesDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Oakane")
        if (!picturesDir.exists()) {
            picturesDir.mkdirs()
        }
        val file = File(picturesDir, fileName)
        contentResolver.openInputStream(uri)?.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        file.absolutePath
    }
}

fun Context.getSavedImageUri(fileName: String): Result<Uri> = runCatching {
    val file = getFileStreamPath(fileName)
    FileProvider.getUriForFile(this, "$packageName.provider", file)
}

fun Context.copyFileToUri(sourceFile: File, destinationUri: Uri) {
    try {
        contentResolver.openOutputStream(destinationUri)?.use { outputStream ->
            sourceFile.inputStream().use { inputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }catch (e: IOException){
        e.printStackTrace()
        Logger.e("Error copying file to uri: $e", e)
    }
}