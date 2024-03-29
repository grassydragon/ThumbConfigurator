package io.github.grassydragon.thumbconfigurator.data

import android.content.ContentResolver
import android.net.Uri
import java.io.FileOutputStream

object Editor {

    fun loadValues(contentResolver: ContentResolver, uri: Uri): Map<Parameter, Int> {
        val text = contentResolver.readText(uri)

        val values = mutableMapOf<Parameter, Int>()

        for (parameter in Parameter.entries) {
            val regex = Regex("""${parameter.id}=(\d+)""")
            val matchResult = regex.find(text)
            matchResult?.let {
                it.groups[1]?.value?.toIntOrNull()?.let { value ->
                    values[parameter] = value
                }
            }
        }

        return values
    }

    fun saveValues(contentResolver: ContentResolver, uri: Uri, values: Map<Parameter, Int>) {
        var text = contentResolver.readText(uri)

        for ((parameter, value) in values) {
            val regex = Regex("""${parameter.id}=(\d+)""")
            text = regex.replaceFirst(text, "${parameter.id}=$value")
        }

        contentResolver.writeText(uri, text)
    }

    private fun ContentResolver.readText(uri: Uri): String {
        val inputStream = openInputStream(uri)
            ?: throw IllegalStateException("Failed to open input stream")

        return inputStream.reader().use { reader ->
            reader.readText()
        }
    }

    private fun ContentResolver.writeText(uri: Uri, text: String) {
        val parcelFileDescriptor = openFileDescriptor(uri, "w")
            ?: throw IllegalStateException("Failed to open file descriptor")

        parcelFileDescriptor.use {
            FileOutputStream(it.fileDescriptor).writer().use { writer ->
                writer.write(text)
            }
        }
    }

}
