package banquemisr.challenge05.presentation.home.component


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import banquemisr.challenge05.core.remote.Constants
import banquemisr.challenge05.domain.model.Movie
import coil.compose.AsyncImage

@Composable
fun MovieList(
    pagingItem: LazyPagingItems<Movie>,
    onNavigateDetailScreen: (String) -> Unit,
    modifier: Modifier =Modifier
) {
    LazyRow (modifier = modifier ){
        items(pagingItem.itemCount) { index ->
            MovieListItem(
                movie = pagingItem[index]!!,
                onNavigateDetailScreen = onNavigateDetailScreen
            )
        }
        pagingItem.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    Log.e("MovieList" , "Loading")
                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    Log.e("MovieList" , "Error")

                    val error = pagingItem.loadState.refresh as LoadState.Error
                    Log.e("MovieList" , "Error ----> ${error.error.localizedMessage}")

                    item {
                        ErrorDialog(
                            errorMessage = error.error.localizedMessage!!,
                            onRetryClick = { retry() },
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = pagingItem.loadState.append as LoadState.Error

                    Log.e("MovieList" , "Error ----> ${error.error.localizedMessage}")

                    item {
                        ErrorDialog(
                            errorMessage = error.error.localizedMessage!!,
                            onRetryClick = { retry() },
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MovieListItem(movie: Movie, onNavigateDetailScreen: (String) -> Unit) {
    Box {
        Column(
            modifier = Modifier
                .size(width = 130.dp, height = 170.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onNavigateDetailScreen(movie.id.toString()) },
        ) {

            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = "${Constants.IMAGE_BASE_URL}/w300/${movie.posterPath}",
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop
            )
        }

        MovieInfo(
            movie = movie, modifier = Modifier
                .width(130.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(
                    RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)
                )
                .align(Alignment.BottomStart)
                .background(
                    color = Color(0xFF625b71).copy(alpha = 0.6f)
                )
        )


    }
}