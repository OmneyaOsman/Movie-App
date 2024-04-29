package banquemisr.challenge05.core.remote

const val HTTP_CODE_SUCCESS = 200
const val HTTP_CODE_AUTHENTICATION_FAILED = 401
const val HTTP_CODE_DUPLICATE_ENTRY= 403
const val HTTP_CODE_INVALID_IDS = 404
const val HTTP_CODE_INVALID_FORMAT = 405
const val HTTP_CODE_INVALID_PARAMETERS = 422
const val HTTP_CODE_INTERNAL_ERROR= 500
const val HTTP_CODE_INVALID_SERVICE = 501
const val HTTP_CODE_SERVICE_OFFLINE= 503

//2	501	Invalid service: This service is temporarily offline OR IT does not exist.
//3	401	Authentication failed: You must be granted a valid key OR You do not have permissions to access the service.
//4	405	Invalid format: This service doesn't exist in that format.
//5	422	Invalid parameters: Your request parameters are incorrect.
//6	404	Invalid id: The pre-requisite id is invalid or not found.
//7	401	Invalid API key: You must be granted a valid key.
//8	403	Duplicate entry: The data you tried to submit already exists.
//9	503	Service offline: This service is temporarily offline, try again later.
//11	500	Internal error: Something went wrong, contact TMDB.