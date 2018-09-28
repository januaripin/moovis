package id.yanuar.moovis.data.remote.response

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ErrorResponse<T> {
            return ErrorResponse(error)
        }

        fun <T> create(response: retrofit2.Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    EmptyResponse()
                } else {
                    SuccessResponse(body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ErrorResponse(Throwable(errorMsg))
            }
        }
    }
}

class EmptyResponse<T> : ApiResponse<T>()

data class SuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>()