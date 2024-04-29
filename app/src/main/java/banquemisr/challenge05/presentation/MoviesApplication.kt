package banquemisr.challenge05.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MoviesApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }
}
private fun setupTimber() =
    object : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement) =
            "${super.createStackElementTag(element)}:${element.lineNumber}"
    }