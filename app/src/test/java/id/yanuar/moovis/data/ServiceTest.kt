package id.yanuar.moovis.data

import id.yanuar.moovis.util.ServerDispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before


/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */
abstract class ServiceTest {
    lateinit var mockWebServer: MockWebServer

    @Before
    open fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.setDispatcher(ServerDispatcher())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}