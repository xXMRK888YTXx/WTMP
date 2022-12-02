package com.xxmrk888ytxx.adminreceiver

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.UserHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxmrk888ytxx.coredeps.DepsProvider.DepsProvider
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.reflect.KClass

/**
 * [Ru]
 * Данный класс тестирует функциональность класса [AdminReceiver]
 * Для того что бы проверить состояние тестируемого класса необходимо запускать эти тесты
 * на устройстве с версией API 26+ и API 25<
 */

/**
 * [En]
 * This class tests the functionality of the [AdminReceiver] class
 * In order to check the state of the class under test, you must run these tests
 * on a device with API version 26+ and API 25<
 */
@RunWith(AndroidJUnit4::class)
class AdminReceiverTest {
    private val adminReceiver: AdminReceiver =  AdminReceiver()
    private val context:Context = mockk(relaxed = true)
    private val intent:Intent = mockk(relaxed = true)
    private val user:UserHandle = mockk(relaxed = true)
    private val adminEventsCallback: AdminEventsCallback = mockk(relaxed = true)
    private val testApp = TestApp(adminEventsCallback)

    @Before
    fun init() {
        every { context.applicationContext } returns testApp
        every { adminReceiver.getManager(context) } returns mockk(relaxed = true)
    }

    @Test
    fun ifEnableAdminDeviceExpectReceiverMustInvokeCallbackMethod() {
        adminReceiver.onEnabled(context,intent)

        verifySequence { adminEventsCallback.onAdminEnabled() }
    }

    @Test
    fun ifDisableAdminDeviceExpectReceiverMustInvokeCallbackMethod() {
        adminReceiver.onDisabled(context, intent)

        verifySequence { adminEventsCallback.onAdminDisabled() }
    }

    @Test
            /**
             * For this test need API 25-
             */
    fun ifUserEnterInvalidPasswordExpectReceiverMustInvokeCallbackMethod_test_duplicated_method() {
        val spyAdminReceiver:AdminReceiver = spyk()

        spyAdminReceiver.onPasswordFailed(context, intent)

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        verifySequence { adminEventsCallback.onPasswordFailed(any()) }
        else verify(exactly = 0) { adminEventsCallback.onPasswordFailed(any()) }
    }

    @Test
    /**
    * For this test need API 26+
    */
    fun ifUserEnterInvalidPasswordExpectReceiverMustInvokeCallbackMethod() {
        val spyAdminReceiver:AdminReceiver = spyk()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            spyAdminReceiver.onPasswordFailed(context, intent,user)
            verifySequence { adminEventsCallback.onPasswordFailed(any()) }
        }
        else verify(exactly = 0) { adminEventsCallback.onPasswordFailed(any()) }
    }

    @Test
            /**
             * For this test need API 25-
             */
    fun ifUserEnterValidPasswordExpectReceiverMustInvokeCallbackMethod_test_duplicated_method() {
        adminReceiver.onPasswordSucceeded(context, intent)

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            verifySequence { adminEventsCallback.onPasswordSucceeded() }
        else verify(exactly = 0) { adminEventsCallback.onPasswordSucceeded() }
    }

    @Test
            /**
             * For this test need API 26+
             */
    fun ifUserEnterValidPasswordExpectReceiverMustInvokeCallbackMethod() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            adminReceiver.onPasswordSucceeded(context, intent,user)
            verifySequence { adminEventsCallback.onPasswordSucceeded() }
        }
        else verify(exactly = 0) { adminEventsCallback.onPasswordSucceeded() }
    }

    @Test
    fun ifCallOnReceiveMethodExpectReceiverMustInvokeCallbackMethod() {
        adminReceiver.onReceive(context, intent)

        verifySequence { adminEventsCallback.onReceive(context, intent) }
    }



    private class TestApp(val adminEventsCallback: AdminEventsCallback) : Application(),DepsProvider {
        override fun <DEPS : Any> provide(classType: KClass<DEPS>): DEPS {
            if(classType.isInstance(adminEventsCallback)) return adminEventsCallback as DEPS
            else error("")
        }

    }

}