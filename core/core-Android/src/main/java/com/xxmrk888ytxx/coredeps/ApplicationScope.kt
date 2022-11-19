package com.xxmrk888ytxx.coredeps

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

object ApplicationScope : CoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() +
            Dispatchers.Default +
            CoroutineName("ApplicationScope")
}