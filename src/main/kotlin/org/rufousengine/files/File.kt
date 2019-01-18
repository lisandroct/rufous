package org.rufousengine.files

import org.rufousengine.files.Files.FileType
import org.rufousengine.files.Files.FileType.*
import java.io.IOException

private typealias NativeFile = java.io.File

class File(fileName: String, fileType: FileType) {
    val native : NativeFile
    private val type : FileType

    val readonly: Boolean
        get() = type == Internal

    val name: String
        get() = native.name
    val extension: String
        get() = native.extension
    val nameWithoutExtension: String
        get() = native.nameWithoutExtension
    val path: String
        get() = native.path
    val exists: Boolean
        get() = native.exists()
    val isFile: Boolean
        get() = native.isFile
    val isDirectory: Boolean
        get() = native.isDirectory

    init {
        val path = when(fileType) {
            Internal -> "${Files.internalPath}$fileName"
            Local -> "${Files.localPath}$fileName"
            External -> "${Files.externalPath}$fileName"
        }

        native = NativeFile(path)

        type = fileType
    }

    fun new() {
        if(readonly) {
            throw IOException("File is readonly.")
        }

        native.mkdirs()
        native.createNewFile()
    }

    fun readBytes() = native.readBytes()
}