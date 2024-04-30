package banquemisr.challenge05.presentation

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import banquemisr.challenge05.MainActivity
import banquemisr.challenge05.MoviesMainApp
import banquemisr.challenge05.core.TestTag
import banquemisr.challenge05.data.utils.MoviesFactory
import banquemisr.challenge05.di.AppModule
import banquemisr.challenge05.di.DatabaseModule
import banquemisr.challenge05.di.NetworkModule
import banquemisr.challenge05.ui.theme.Banquemisrchallenge05Theme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(
    DatabaseModule::class,
    NetworkModule::class,
    AppModule::class,
    )
class HomeMovieDetailsScreensTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val movieDetail = MoviesFactory.resultList[0]

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            Banquemisrchallenge05Theme {
                MoviesMainApp()
            }
        }
    }

    @Test
    fun testNowPlayingMovies() {
        composeRule.onNode(hasText(NOW_PLAYING_LABEL)).assertIsDisplayed()
        composeRule.onNode(hasTestTag(TestTag.NOW_PLAYING_MOVIES)).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTag.NOW_PLAYING_MOVIES).performScrollToNode(hasText(CIVIL_WAR))
            .assertIsDisplayed()
        composeRule.onNodeWithTag(TestTag.NOW_PLAYING_MOVIES).performScrollToNode(hasText(
            MOVIE_TITLE
        ))
            .assertIsDisplayed()
    }

    @Test
    fun testUComingMovies() {
        composeRule.onNode(hasText(UPCOMING_LABEL)).assertIsDisplayed()
        composeRule.onNode(hasTestTag(TestTag.UPCOMING_MOVIES)).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTag.UPCOMING_MOVIES).performScrollToNode(hasText(CIVIL_WAR))
            .assertIsDisplayed()
        composeRule.onNodeWithTag(TestTag.UPCOMING_MOVIES).performScrollToNode(hasText(MOVIE_TITLE))
            .assertIsDisplayed()
    }

    @Test
    fun testNavigationToMovieWithId929590Details() {
        val itemMatcher = hasText(CIVIL_WAR)
        composeRule.onNodeWithTag(TestTag.UPCOMING_MOVIES)
            .performScrollToNode(itemMatcher)
            .onChildren()
            .filterToOne(itemMatcher)
            .performClick()


        composeRule.onNodeWithTag(TestTag.BACK_ICON).assertIsDisplayed()


    }

    @Test
    fun testNavigationToMovieWithId929590DetailsAndDisplayInfo() {
        val itemMatcher = hasText(CIVIL_WAR)

        composeRule.onNodeWithTag(TestTag.UPCOMING_MOVIES)
            .performScrollToNode(itemMatcher)
            .onChildren()
            .filterToOne(itemMatcher)
            .performClick()

        val itemImage = hasTestTag(TestTag.DETAILS_IMAGE)


        composeRule.onNodeWithTag(TestTag.DETAILS_LIST).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTag.DETAILS_LIST)
            .performScrollToNode(itemImage)
            .onChildren()
            .filterToOne(itemImage)
            .assertIsDisplayed()

        composeRule.onNodeWithTag(TestTag.DETAILS_LIST)
            .performScrollToNode(hasText(movieDetail.title))
            .onChildren()
            .filterToOne(hasText(movieDetail.title))
            .assertIsDisplayed()

        composeRule.onNodeWithTag(TestTag.DETAILS_LIST)
            .performScrollToNode(hasText(movieDetail.overview!!))
            .onChildren()
            .filterToOne(hasText(movieDetail.overview!!))
            .assertIsDisplayed()

        composeRule.onNodeWithTag(TestTag.DETAILS_LIST)
            .performScrollToNode(hasText(movieDetail.releaseDate!!))
            .onChildren()
            .filterToOne(hasText(movieDetail.releaseDate!!))
            .assertIsDisplayed()

        composeRule.onNodeWithTag(TestTag.DETAILS_LIST)
            .performScrollToNode(hasTestTag(TestTag.DETAILS_MOVIES_GENRES)).assertIsDisplayed()

    }

    companion object {
        const val CIVIL_WAR = "Civil War"
        const val MOVIE_TITLE = "Godzilla x Kong: The New Empire"
        const val NOW_PLAYING_LABEL = "Now Playing"
        const val UPCOMING_LABEL = "UpComing"
    }

}