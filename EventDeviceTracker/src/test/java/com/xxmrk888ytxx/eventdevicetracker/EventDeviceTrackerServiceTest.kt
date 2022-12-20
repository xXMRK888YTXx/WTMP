package com.xxmrk888ytxx.eventdevicetracker

import android.app.Application
import android.content.pm.ActivityInfo
import android.view.accessibility.AccessibilityEvent
import com.xxmrk888ytxx.coredeps.DepsProvider.DepsProvider
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.reflect.KClass

internal class EventDeviceTrackerServiceTest {

    private lateinit var service:EventDeviceTrackerService

    private var currentCallback:EventDeviceTrackerCallback = mockk(relaxed = true)

    @Before
    fun init() {
        service = spyk()
        every { service.applicationContext } returns TestApp() {
            currentCallback
        }
    }

    @After
    fun clear() {
        currentCallback = mockk(relaxed = true)
    }

    @Test
    fun sendEventExpectServiceSendEventToCallback() {
        val testPackageName = "com.test.com"
        val testPackageName2 = "by.test.com"
        val testPackageName3 = "net.test.com"

        service.onAccessibilityEvent(getTestEvent(testPackageName))
        service.onAccessibilityEvent(getTestEvent(testPackageName2))
        service.onAccessibilityEvent(getTestEvent(testPackageName3))



        verifyOrder() {
            currentCallback.onOpenAppChanged(testPackageName)
            currentCallback.onOpenAppChanged(testPackageName2)
            currentCallback.onOpenAppChanged(testPackageName3)
        }
        verify(exactly = 3) {
            currentCallback.onOpenAppChanged(any())
        }
    }

    @Test
    fun `send event in service expect service send in callback not duplicated in a row events`() {
        val testPackageName = "com.test.com"
        val testPackageName2 = "by.test.com"
        val testPackageName3 = "net.test.com"

        service.onAccessibilityEvent(getTestEvent(testPackageName))
        service.onAccessibilityEvent(getTestEvent(testPackageName))
        service.onAccessibilityEvent(getTestEvent(testPackageName))
        service.onAccessibilityEvent(getTestEvent(testPackageName2))
        service.onAccessibilityEvent(getTestEvent(testPackageName3))
        service.onAccessibilityEvent(getTestEvent(testPackageName2))
        service.onAccessibilityEvent(getTestEvent(testPackageName2))
        service.onAccessibilityEvent(getTestEvent(testPackageName3))
        service.onAccessibilityEvent(getTestEvent(testPackageName3))
        service.onAccessibilityEvent(getTestEvent(testPackageName))
        service.onAccessibilityEvent(getTestEvent(testPackageName))
        service.onAccessibilityEvent(getTestEvent(testPackageName2))
        service.onAccessibilityEvent(getTestEvent(testPackageName2))
        service.onAccessibilityEvent(getTestEvent(testPackageName3))
        service.onAccessibilityEvent(getTestEvent(testPackageName3))
        service.onAccessibilityEvent(getTestEvent(testPackageName))
        service.onAccessibilityEvent(getTestEvent(testPackageName2))


        verify(exactly = 3) {
            currentCallback.onOpenAppChanged(testPackageName)
        }
        verify(exactly = 4) {
            currentCallback.onOpenAppChanged(testPackageName2)
        }
        verify(exactly = 3) {
            currentCallback.onOpenAppChanged(testPackageName3)
        }
    }

    @Test
    fun `send event in service expect service send in callback events with correctly event type`() {
        val faceEvent = mockk<AccessibilityEvent>(relaxed = true)
        val faceEvent2 = mockk<AccessibilityEvent>(relaxed = true)
        val testPackageName = "com.qwe.dsa"
        every { faceEvent.eventType } returns AccessibilityEvent.CONTENTS_FILE_DESCRIPTOR
        every { faceEvent2.eventType } returns AccessibilityEvent.TYPE_WINDOWS_CHANGED
        every { faceEvent.packageName } returns testPackageName
        every { faceEvent2.packageName } returns testPackageName

        service.onAccessibilityEvent(faceEvent)
        service.onAccessibilityEvent(faceEvent2)
        service.onAccessibilityEvent(getTestEvent(testPackageName))

        verify(exactly = 1) { currentCallback.onOpenAppChanged(testPackageName) }
    }

    @Test
    fun `send event in service expect service don't send event in ignoreList`() {
        val testPK = "com.notIngore.com"
        val testPK2 = "by.notIngore.com"
        val ignoreList = listOf("com.ingnore.com","com.ignore2.com")
        every { currentCallback.params } returns EventDeviceTrackerParams.Builder()
            .setIgnoreList(ignoreList)
            .isActivityOnly(false)
            .build()

        service.onAccessibilityEvent(getTestEvent(testPK))
        service.onAccessibilityEvent(getTestEvent(ignoreList[0]))
        service.onAccessibilityEvent(getTestEvent(testPK2))
        service.onAccessibilityEvent(getTestEvent(testPK))
        service.onAccessibilityEvent(getTestEvent(ignoreList[1]))
        service.onAccessibilityEvent(getTestEvent(ignoreList[0]))
        service.onAccessibilityEvent(getTestEvent(ignoreList[1]))
        service.onAccessibilityEvent(getTestEvent(ignoreList[0]))
        service.onAccessibilityEvent(getTestEvent(testPK))
        service.onAccessibilityEvent(getTestEvent(testPK2))

        verify(exactly = 2) {
            currentCallback.onOpenAppChanged(testPK)
        }
        verify(exactly = 2) {
            currentCallback.onOpenAppChanged(testPK2)
        }
        verify(exactly = 0) {
            currentCallback.onOpenAppChanged(ignoreList[0])
        }
        verify(exactly = 0) {
            currentCallback.onOpenAppChanged(ignoreList[1])
        }
    }

    @Test
    fun `send event in service expect service don't send event in ignoreList(vararg)`() {
        val testPK = "com.notIngore.com"
        val testPK2 = "by.notIngore.com"
        val ignoreList = listOf("com.ingnore.com","com.ignore2.com")
        every { currentCallback.params } returns EventDeviceTrackerParams.Builder()
            .setIgnoreList(ignoreList[1],ignoreList[0])
            .isActivityOnly(false)
            .build()

        service.onAccessibilityEvent(getTestEvent(testPK))
        service.onAccessibilityEvent(getTestEvent(ignoreList[0]))
        service.onAccessibilityEvent(getTestEvent(testPK2))
        service.onAccessibilityEvent(getTestEvent(testPK))
        service.onAccessibilityEvent(getTestEvent(ignoreList[1]))
        service.onAccessibilityEvent(getTestEvent(ignoreList[0]))
        service.onAccessibilityEvent(getTestEvent(ignoreList[1]))
        service.onAccessibilityEvent(getTestEvent(ignoreList[0]))
        service.onAccessibilityEvent(getTestEvent(testPK))
        service.onAccessibilityEvent(getTestEvent(testPK2))

        verify(exactly = 2) {
            currentCallback.onOpenAppChanged(testPK)
        }
        verify(exactly = 2) {
            currentCallback.onOpenAppChanged(testPK2)
        }
        verify(exactly = 0) {
            currentCallback.onOpenAppChanged(ignoreList[0])
        }
        verify(exactly = 0) {
            currentCallback.onOpenAppChanged(ignoreList[1])
        }
    }

    @Test
    fun `send event and enable isActivity expect service send event in callback if activityInfo_isEnable return true`() {
        val packageNames = listOf("com.tyhg.fdsgf","com.qwe.ewrt","by.eretyt.ret")
        val activityInfo = mockk<ActivityInfo>(relaxed = true)
        every { activityInfo.isEnabled } returns true
        every { service.packageManager.getActivityInfo(any(),0) } returns activityInfo
        every { currentCallback.params } returns EventDeviceTrackerParams.Builder()
            .isActivityOnly(true)
            .build()

        packageNames.map { getTestEvent(it) }.forEach {
            service.onAccessibilityEvent(it)
        }
        every { activityInfo.isEnabled } returns false
        packageNames.map { getTestEvent(it) }.forEach {
            service.onAccessibilityEvent(it)
        }

        packageNames.forEach {
            verify(exactly = 1) { currentCallback.onOpenAppChanged(it) }
        }
    }

    @Test
    fun `send event and disable isActivity expect service send all event in callback`() {
        val packageNames = listOf("com.tyhg.fdsgf","com.qwe.ewrt","by.eretyt.ret")
        val activityInfo = mockk<ActivityInfo>(relaxed = true)
        every { activityInfo.isEnabled } returns true
        every { service.packageManager.getActivityInfo(any(),0) } returns activityInfo
        every { currentCallback.params } returns EventDeviceTrackerParams.Builder()
            .isActivityOnly(false)
            .build()

        packageNames.map { getTestEvent(it) }.forEach {
            service.onAccessibilityEvent(it)
        }
        every { activityInfo.isEnabled } returns false
        packageNames.map { getTestEvent(it) }.forEach {
            service.onAccessibilityEvent(it)
        }

        packageNames.forEach {
            verify(exactly = 2) { currentCallback.onOpenAppChanged(it) }
        }
    }

    private fun getTestEvent(packageName:String) : AccessibilityEvent {
        val event = mockk<AccessibilityEvent>(relaxed = true)
        every { event.packageName } returns packageName
        every { event.eventType } returns AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        return event
    }

    @Suppress("UNCHECKED_CAST")
    private class TestApp(val onGetCallback: () -> EventDeviceTrackerCallback) : Application(),DepsProvider {
        override fun <DEPS : Any> provide(classType: KClass<DEPS>): DEPS {
            return onGetCallback() as DEPS
        }

    }
}