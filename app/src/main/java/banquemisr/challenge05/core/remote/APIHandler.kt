package banquemisr.challenge05.core.remote
//
//import banquemisr.challenge05.core.remote.exception.UnhandledHttpCodeException
//import retrofit2.Response
//
////class ApiHandler {
//
//    suspend fun <T : Any> handleApi(
//        execute: suspend () -> Response<T>
//    ): APiResponse<T> {
//        return try {
//            val response = execute()
//            when (response.code()) {
//                HTTP_CODE_SUCCESS -> {
//                    return if (response.body() == null)
//                        APiResponse.Failure.ServerDataNullError
//                    else
//                        APiResponse.Success(requireNotNull(response.body()))
//                }
//
//                HTTP_CODE_AUTHENTICATION_FAILED, HTTP_CODE_DUPLICATE_ENTRY,
//                HTTP_CODE_INTERNAL_ERROR, HTTP_CODE_INVALID_FORMAT,
//                HTTP_CODE_SERVICE_OFFLINE, HTTP_CODE_INVALID_IDS,
//                HTTP_CODE_INVALID_PARAMETERS, HTTP_CODE_INVALID_SERVICE -> {
//                    return if (response.errorBody() != null) {
//                        APiResponse.Failure.ServerError(requireNotNull(response.body()))
//                    } else
//                        APiResponse.Failure.ServerDataNullError
//                }
//
//                else -> {
//                    throw UnhandledHttpCodeException(response.code())
//                }
//            }
//        } catch (e: Throwable) {
//            APiResponse.Failure.Exception(e)
//        }
//    }
//    suspend inline fun <reified T> handleResponse(
//        serviceMethod: suspend () -> Response<T>
//    ): APiResponse<T> {
//        return try {
//            val response = serviceMethod()
//            when (response.code()) {
//                HTTP_CODE_SUCCESS -> {
//                    return if (response.body() == null)
//                        APiResponse.Failure.ServerDataNullError
//                    else
//                        APiResponse.Success(requireNotNull(response.body()))
//                }
//
//                HTTP_CODE_AUTHENTICATION_FAILED, HTTP_CODE_DUPLICATE_ENTRY,
//                HTTP_CODE_INTERNAL_ERROR, HTTP_CODE_INVALID_FORMAT,
//                HTTP_CODE_SERVICE_OFFLINE, HTTP_CODE_INVALID_IDS,
//                HTTP_CODE_INVALID_PARAMETERS, HTTP_CODE_INVALID_SERVICE -> {
//                    return if (response.errorBody() != null) {
//                        APiResponse.Failure.ServerError(requireNotNull(response.body()))
//                    } else
//                        APiResponse.Failure.ServerDataNullError
//                }
//
//                else -> {
//                    throw UnhandledHttpCodeException(response.code())
//                }
//            }
//        } catch (e: Throwable) {
//            APiResponse.Failure.Exception(e)
//        }
//    }
////}
