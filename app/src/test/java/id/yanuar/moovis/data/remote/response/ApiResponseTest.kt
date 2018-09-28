package id.yanuar.moovis.data.remote.response

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@RunWith(JUnit4::class)
class ApiResponseTest {
    @Test
    fun testException() {
        val (throwable) = ApiResponse.create<String>(Exception("error"))
        assertThat(throwable.message, `is`("error"))
    }

    @Test
    fun testSuccess() {
        val apiResponse = ApiResponse.create<String>(Response.success("sukses")) as SuccessResponse<String>
        assertThat(apiResponse.body, `is`("sukses"))
    }

    @Test
    fun testError() {
        val errorResponse = Response.error<String>(401,
                ResponseBody.create(MediaType.parse("application/txt"), "Unauthorized"))

        val apiResponse = ApiResponse.create<String>(errorResponse)
        val (throwable) = (apiResponse as ErrorResponse)
        assertThat(throwable.message, `is`("Unauthorized"))
    }
}