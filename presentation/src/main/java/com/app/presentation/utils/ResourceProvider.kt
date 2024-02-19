package com.app.presentation.utils

import android.content.Context

interface ResourceProvider {
    fun getString(resId: Int): String
}

class AndroidResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
}
