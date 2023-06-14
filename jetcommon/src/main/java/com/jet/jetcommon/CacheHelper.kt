package com.jet.jetcommon

import android.content.Context
import okio.buffer
import okio.sink
import okio.source
import java.io.File

object CacheHelper {

    fun okioCache(context: Context) {
        //create a file
        val file = File("${context.externalCacheDir}/cache12.txt")
        if (!file.exists()) {
            file.createNewFile()
        }

        //write to file
        val sink = file.sink() //open to write
        val bufferSink = sink.buffer() //buffer is used for cache for performance
        bufferSink.writeUtf8("hi, i'm cache") //utf-8 encoding supports multiple chars types
        bufferSink.close() //close the stream

        //read from file
        val source = file.source() //open to read
        val bufferSource = source.buffer()
        val content = bufferSource.readUtf8() //read content
        bufferSource.close()
        //content = hi, i'm cache
    }
}