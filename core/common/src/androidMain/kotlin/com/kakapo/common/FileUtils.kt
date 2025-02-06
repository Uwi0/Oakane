package com.kakapo.common

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

private const val DIRECTORY = "Oakane"

fun Context.saveImageUriToPublicDirectory(uri: Uri): Result<String> = runCatching {
    val fileName = "image-oakane${System.currentTimeMillis()}.jpg"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/$DIRECTORY")
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
        val picturesDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), DIRECTORY)
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

fun Context.getImageUriFromFileName(fileName: String): Result<Uri> = runCatching {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // First try to get from MediaStore directly
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val selection = "${MediaStore.Images.Media.DISPLAY_NAME} LIKE ? AND " +
                "${MediaStore.Images.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf(fileName, "%Pictures/$DIRECTORY%")

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                return@runCatching Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
            }
        }

        // If not found, try to scan the file
        val file = File("${Environment.DIRECTORY_PICTURES}/$DIRECTORY/$fileName")
        if (file.exists()) {
            var scannedUri: Uri? = null
            val latch = CountDownLatch(1)

            MediaScannerConnection.scanFile(
                this,
                arrayOf(file.absolutePath),
                arrayOf("image/jpeg")
            ) { _, uri ->
                scannedUri = uri
                latch.countDown()
            }

            // Wait for scan to complete with timeout
            if (latch.await(5, TimeUnit.SECONDS) && scannedUri != null) {
                return@runCatching scannedUri!!
            }

            // If scanning didn't work, try one more MediaStore query
            contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                "${MediaStore.Images.Media.DATE_ADDED} DESC"
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    return@runCatching Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
                }
            }
        }

        throw FileNotFoundException("File not found in MediaStore and physical location: $fileName")
    } else {
        val picturesDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), DIRECTORY)
        val file = File(picturesDir, fileName)
        if (file.exists()) {
            FileProvider.getUriForFile(
                this,
                "${packageName}.provider",
                file
            )
        } else {
            throw FileNotFoundException("File not found at path: ${file.absolutePath}")
        }
    }
}
