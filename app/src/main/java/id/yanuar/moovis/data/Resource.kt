package id.yanuar.moovis.data

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<out T>(val status: Status, val data: T? = null, val throwable: Throwable? = null) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(throwable: Throwable?, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, throwable)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

}