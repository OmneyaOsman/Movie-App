package banquemisr.challenge05.core.remote.exception

class UnhandledHttpCodeException(val httpStatusCode: Int) : Exception()