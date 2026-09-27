package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.DefaultMorelganjData
import com.example.data.model.InstitutionCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("মোরেলগঞ্জ সেবা", appName)
    }

    @Test
    fun `verify initial data contains hospitals schools and govt offices`() {
        val data = DefaultMorelganjData.initialInstitutions
        assertTrue(data.isNotEmpty())
        assertTrue(data.any { it.category == InstitutionCategory.HOSPITAL.name })
        assertTrue(data.any { it.category == InstitutionCategory.GOVT_OFFICE.name })
        assertTrue(data.any { it.category == InstitutionCategory.MADRASAH.name })
        assertTrue(data.any { it.category == InstitutionCategory.SCHOOL_COLLEGE.name })
        assertTrue(data.any { it.category == InstitutionCategory.MOSQUE.name })
    }

    @Test
    fun `verify problem needs have solutions and keywords`() {
        val problems = DefaultMorelganjData.problemNeeds
        assertTrue(problems.isNotEmpty())
        val backPain = problems.find { it.keywords.contains("মাজা ব্যথা") }
        assertTrue(backPain != null)
        val fever = problems.find { it.keywords.contains("হাড় কাঁপানো") }
        assertTrue(fever != null)
        val heartSpecialist = problems.find { it.keywords.contains("হৃদরোগ বিশেষজ্ঞ") }
        assertTrue(heartSpecialist != null)
    }
}
