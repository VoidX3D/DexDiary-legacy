package com.dexdiary.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

object FileUtils {
    fun saveImageToInternalStorage(context: Context, uri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val fileName = "img_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, "images/$fileName")
        file.parentFile?.mkdirs()
        
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        
        inputStream?.close()
        outputStream.close()
        
        return file.absolutePath
    }

    fun deleteFile(path: String) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }
}
