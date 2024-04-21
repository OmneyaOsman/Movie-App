package banquemisr.challenge05.presentation.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems


@Composable
fun HomeScreen(
    onNavigateDetailScreen : (String) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
//    val topRatedPagingItem = viewModel.topRatedMovieList.collectAsLazyPagingItems()
//    val popularPagingItem = viewModel.popularMovieList.collectAsLazyPagingItems()
    val nowPlayingPagingItem = viewModel.nowPlayingMovieList.collectAsLazyPagingItems()

    HomeContent(
//        topRatedPagingItem = topRatedPagingItem,
//        popularPagingItem = popularPagingItem,
        nowPlayingPagingItem = nowPlayingPagingItem,
        onNavigateDetailScreen = onNavigateDetailScreen
    )
}