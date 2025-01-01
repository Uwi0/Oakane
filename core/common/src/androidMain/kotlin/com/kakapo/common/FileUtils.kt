package com.kakapo.common

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
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