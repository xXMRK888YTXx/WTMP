package com.xxmrk888ytxx.observer.domain.ResourcesProvider

import android.annotation.SuppressLint
import android.content.Context
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ResourcesProvider
import javax.inject.Inject

class ResourcesProviderImpl @Inject constructor(
    private val context: Context
) : ResourcesProvider {

    @SuppressLint("ResourceType")
    override fun getString(stringRes: Int): String {
        return context.getString(stringRes)
    }
}