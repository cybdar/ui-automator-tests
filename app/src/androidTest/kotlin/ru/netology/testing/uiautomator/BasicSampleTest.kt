package ru.netology.testing.uiautomator

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BasicSampleTest {

    private lateinit var device: UiDevice
    private val packageName = "ru.netology.testing.uiautomator"
    private val timeout = 5000L

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), timeout)

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)

        device.wait(Until.hasObject(By.pkg(packageName)), timeout)
    }

    @Test
    fun emptyTextShouldNotChangeTextView() {
        val inputField = device.wait(Until.findObject(By.res(packageName, "userInput")), timeout)
        inputField.text = "   "

        val changeButton = device.wait(Until.findObject(By.res(packageName, "buttonChange")), timeout)
        changeButton.click()

        val textView = device.wait(Until.findObject(By.res(packageName, "textToBeChanged")), timeout)

        Assert.assertNotEquals("", textView.text)
        Assert.assertNotEquals("   ", textView.text)
    }

    @Test
    fun openTextInNewActivity() {
        val testText = "UI Automator Test"

        val inputField = device.wait(Until.findObject(By.res(packageName, "userInput")), timeout)
        inputField.text = testText

        val openButton = device.wait(Until.findObject(By.res(packageName, "buttonActivity")), timeout)
        openButton.click()

        val newScreenText = device.wait(Until.findObject(By.text(testText)), timeout)

        Assert.assertEquals(testText, newScreenText.text)
    }
}