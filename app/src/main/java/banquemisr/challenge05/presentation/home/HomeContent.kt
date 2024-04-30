package banquemisr.challenge05.presentation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import banquemisr.challenge05.core.TestTag
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.presentation.home.component.MovieList
import banquemisr.challenge05.presentation.home.component.NowPlayingHorizontalPager


@Composable
fun HomeContent(
    upComingPagingItem: LazyPagingItems<Movie>,
    popularPagingItem: LazyPagingItems<Movie>,
    nowPlayingPagingItem: LazyPagingItems<Movie>,
    onNavigateDetailScreen: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TestTag.NOW_PLAYING_MOVIES),
        state = rememberLazyListState()
    ) {
        item {
            Text(
                text = "Now Playing",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(15.dp))
            NowPlayingHorizontalPager(
                nowPlayingPagingItem = nowPlayingPagingItem,
                onNavigateDetailScreen = onNavigateDetailScreen
            )
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "UpComing",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            MovieList(
                pagingItem = upComingPagingItem,
                onNavigateDetailScreen = onNavigateDetailScreen,
                Modifier.testTag(TestTag.UPCOMING_MOVIES)
            )
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Popular",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            MovieList(
                pagingItem = popularPagingItem,
                onNavigateDetailScreen = onNavigateDetailScreen,
                Modifier.testTag(TestTag.POPULAR_MOVIES)
            )
        }

    }
}
