package com.xxmrk888ytxx.bootreceiver

import android.app.Application
import android.content.Context
import android.content.Intent
import com.xxmrk888ytxx.coredeps.DepsProvider.DepsProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Test
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class BootReceiverTest {

    private val bootReceiver : BootReceiver
        get() = BootReceiver()

    private val context = mockk<Context>(relaxed = true)

    private val callback:BootCallback = mockk(relaxed = true)

    @Before
    fun init() {
        every { context.applicationContext } returns TestApp(callback)
    }

    @Test
    fun triggeredReceiverExpectTheySendTriggerInCallback() {
        val intent = mockk<Intent>(relaxed = true)
        every { intent.action } returns Intent.ACTION_BOOT_COMPLETED

        bootReceiver.onReceive(context,intent)

        verifySequence { callback.onBootCompleted() }
    }

    @Test
    fun triggeredReceiverWithoutActionExpectTheyNotSendTriggerInCallback() {
        val intent = mockk<Intent>(relaxed = true)

        bootReceiver.onReceive(context,intent)

        verify(exactly = 0) { callback.onBootCompleted() }
    }


    class TestApp(private val callback: BootCallback) : Application(),DepsProvider {
        override fun <DEPS : Any> provide(classType: KClass<DEPS>): DEPS {
            return callback as DEPS
        }

    }
}