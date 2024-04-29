package banquemisr.challenge05.presentation.home

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import banquemisr.challenge05.MainActivity
import banquemisr.challenge05.di.AppModule
import banquemisr.challenge05.di.DatabaseModule
import banquemisr.challenge05.di.NetworkModule
import banquemisr.challenge05.presentation.MoviesMainApp
import banquemisr.challenge05.ui.theme.Banquemisrchallenge05Theme
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

//@HiltAndroidTest
//@UninstallModules(
//    DatabaseModule::class,
//    NetworkModule::class,
////    AppModule::class,
//
//)
//class MovieDetailsScreensTest {
//
//    @get:Rule(order = 0)
//    val hiltRule = HiltAndroidRule(this)
//
//    @get:Rule(order = 1)
//    val composeRule = createAndroidComposeRule<MainActivity>()
//
//    @ExperimentalAnimationApi
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//        composeRule.activity.setContent {
//            Banquemisrchallenge05Theme {
//                MoviesMainApp()
//            }
//        }
//    }
//
//}