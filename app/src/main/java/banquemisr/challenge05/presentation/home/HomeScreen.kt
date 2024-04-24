package banquemisr.challenge05.presentation.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import banquemisr.challenge05.presentation.home.component.MovieList

@Composable
fun HomeScreen(
    onNavigateDetailScreen: (String) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
//    val topRatedPagingItem = viewModel.topRatedMovieList.collectAsLazyPagingItems()
//    val popularPagingItem = viewModel.popularMovieList.collectAsLazyPagingItems()
    val nowPlayingPagingItem = viewModel.nowPlayingMovieListt.collectAsLazyPagingItems()
    Log.e("HomeScreen", "nowPlayingPagingItem ${nowPlayingPagingItem.itemCount}")

    val movies = viewModel.nowPlayingMovieListt.collectAsLazyPagingItems()


    Log.d("HomeScreen", nowPlayingPagingItem.loadState.toString())

    MovieList(
        movies, onNavigateDetailScreen = onNavigateDetailScreen
    )

}