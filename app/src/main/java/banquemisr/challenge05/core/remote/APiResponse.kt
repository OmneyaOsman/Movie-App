package banquemisr.challenge05.core.remote

//
//public sealed interface APiResponse<out T> {
//    data class Success<T>(val data: T) : APiResponse<T>
//
//    public sealed interface Failure<T> : APiResponse<T> {
//        data class ServerError<T>(val serverResponseError: T) : Failure<T>
//        data object ServerDataNullError : APiResponse<Nothing>
//        data class Exception(val throwable: Throwable) : Failure<Nothing>
//    }
//
//}
//
