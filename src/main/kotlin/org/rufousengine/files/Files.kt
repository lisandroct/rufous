package org.rufousengine.files

object Files {
    val classLoader = javaClass.classLoader ?: throw RuntimeException("Error initializing Files")
    val internalPath = classLoader.getResource("").file
    val localPath = "assets/"
    val externalPath = "${ System.getProperty("user.home") }/"

    enum class FileType {
        /** Path relative to the root of the internal. Internal files are always readonly. */
        Internal,

        /** Path relative to the application's assets folder. Internal files are always readonly. */
        Local,

        /** Path relative to the application's folder in the home directory of the current user.  */
        External
    }

    fun internal(fileName: String): File {
        return File(fileName, FileType.Internal)
    }

    fun local(fileName: String): File {
        return File(fileName, FileType.Local)
    }

    fun external(fileName: String): File {
        return File(fileName, FileType.External)
    }
}