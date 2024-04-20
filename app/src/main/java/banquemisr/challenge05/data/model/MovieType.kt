package banquemisr.challenge05.data.model

sealed interface MovieType {
    object NOW_PLAYING: MovieType
    object POPULAR: MovieType
    object UPCOMING: MovieType
}

object  MovieTypeConstants {
    const val NOW_PLAYING: String ="now_playing"
    const val POPULAR: String ="popular"
    const val UPCOMING: String ="upcoming"
}