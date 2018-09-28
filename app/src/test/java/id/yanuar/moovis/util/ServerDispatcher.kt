package id.yanuar.moovis.util

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.Okio
import java.io.IOException
import java.nio.charset.StandardCharsets


/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */
class ServerDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse? {
        val path: String
        path = if (request.method == "GET") {
            val queryIndex = request.path.lastIndexOf("?")
            request.path.substring(0, queryIndex)
        } else {
            request.path
        }

        val inputStream = javaClass.classLoader
                .getResourceAsStream("api/$path.json")
        val source = Okio.buffer(Okio.source(inputStream))

        var response: MockResponse? = null
        try {
            response = MockResponse().setBody(source.readString(StandardCharsets.UTF_8))
        } catch (io: IOException) {
            io.printStackTrace()
        }

        return response
    }
}