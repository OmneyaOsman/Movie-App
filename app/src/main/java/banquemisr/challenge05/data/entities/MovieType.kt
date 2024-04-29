package banquemisr.challenge05.data.entities


sealed interface MovieType {
    data object NowPlaying : MovieType
    data object Popular : MovieType
    data object UpComing : MovieType
}

object MovieTypeConstants {
    const val NOW_PLAYING: String = "now_playing"
    const val POPULAR: String = "popular"
    const val UPCOMING: String = "upcoming"
}

fun MovieType.value(): String =
    when (this) {
        MovieType.NowPlaying -> MovieTypeConstants.NOW_PLAYING
        MovieType.Popular -> MovieTypeConstants.POPULAR
        MovieType.UpComing -> MovieTypeConstants.UPCOMING
    }