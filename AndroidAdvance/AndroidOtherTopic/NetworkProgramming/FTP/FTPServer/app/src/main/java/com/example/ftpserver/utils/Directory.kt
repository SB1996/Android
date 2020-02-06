package com.example.ftpserver.utils

import android.content.Context
import android.content.Intent
import net.rdrei.android.dirchooser.DirectoryChooserActivity
import net.rdrei.android.dirchooser.DirectoryChooserConfig

class Directory(private val context: Context) {

    internal fun choseDirectory(newDirName: String) : Intent{
        val chooserIntent: Intent = Intent(context, DirectoryChooserActivity::class.java)
        val config = DirectoryChooserConfig.builder()
            .newDirectoryName(newDirName)
            .allowNewDirectoryNameModification(true)
            .build()
        chooserIntent.putExtra(DirectoryChooserActivity.EXTRA_CONFIG, config)

        return chooserIntent
    }
}