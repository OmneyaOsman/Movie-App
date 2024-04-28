package banquemisr.challenge05.presentation.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun HomeScreen(
    onNavigateDetailScreen: (String) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val popularPagingItem = viewModel.popularMovies.collectAsLazyPagingItems()
    val upComingPagingItem = viewModel.upComingMovies.collectAsLazyPagingItems()
    val nowPlayingPagingItem = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
    Log.e("HomeScreen", "nowPlayingPagingItem ${nowPlayingPagingItem.itemCount}")


    Log.d("HomeScreen", nowPlayingPagingItem.loadState.toString())

    HomeContent(
        upComingPagingItem = upComingPagingItem,
        nowPlayingPagingItem = nowPlayingPagingItem,
        popularPagingItem = popularPagingItem,
        onNavigateDetailScreen = onNavigateDetailScreen
    )

}