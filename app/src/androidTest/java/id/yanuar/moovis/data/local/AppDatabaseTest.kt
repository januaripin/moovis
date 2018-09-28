package id.yanuar.moovis.data.local

import android.arch.core.executor.testing.CountingTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */
abstract class AppDatabaseTest {

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    lateinit var appDatabase: AppDatabase

    @Before
    open fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        countingTaskExecutorRule.drainTasks(10,TimeUnit.SECONDS)
        appDatabase.close()
    }
}