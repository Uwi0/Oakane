package com.kakapo.oakane

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.TestWorkerBuilder
import androidx.work.workDataOf
import com.kakapo.data.repository.base.SettingsRepository
import com.kakapo.model.reminder.Reminder
import com.kakapo.model.reminder.ReminderDay
import com.kakapo.oakane.sync.worker.ReminderWorker
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ReminderWorkerTest {
    private lateinit var context: Context
    private lateinit var executor: Executor

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        executor = Executors.newSingleThreadExecutor()
    }

    @Test
    fun testReminderWorker() {
        val worker = TestListenableWorkerBuilder<ReminderWorker>(
            context = context,
        ).build()

        runBlocking {
            val result = worker.doWork()
            assertThat(result, `is`(ListenableWorker.Result.success()))
        }

    }
}