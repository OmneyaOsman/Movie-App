package banquemisr.challenge05.presentation.details

import banquemisr.challenge05.domain.model.Movie

data class DetailUIState(
    var movie : Movie? = null,
    var isLoading : Boolean = false,
    var error : String = ""
)
