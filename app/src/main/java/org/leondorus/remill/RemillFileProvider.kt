package org.leondorus.remill

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import java.io.File
import java.util.UUID

class RemillFileProvider: FileProvider(R.xml.file_paths) {
    fun getNewUri(context: Context): Uri {
        val dir = File(context.filesDir, "drug_images")
        dir.mkdirs()
        val filename = UUID.randomUUID().toString() + ".jpg"

        val file = File(dir, filename)
        val authority = context.packageName + ".fileprovider"

        return getUriForFile(context, authority, file)
    }
}