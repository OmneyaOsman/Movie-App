package banquemisr.challenge05.data.entities


sealed interface MovieType {
    object NOW_PLAYING : MovieType
    object POPULAR : MovieType
    object UPCOMING : MovieType
}

object MovieTypeConstants {
    const val NOW_PLAYING: String = "now_playing"
    const val POPULAR: String = "popular"
    const val UPCOMING: String = "upcoming"
}

fun MovieType.value(): String =
    when (this) {
        MovieType.NOW_PLAYING -> MovieTypeConstants.NOW_PLAYING
        MovieType.POPULAR -> MovieTypeConstants.POPULAR
        MovieType.UPCOMING -> MovieTypeConstants.UPCOMING
    }