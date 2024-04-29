package banquemisr.challenge05.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.paging.compose.LazyPagingItems
import banquemisr.challenge05.data.remote.Constants
import banquemisr.challenge05.domain.model.Movie
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NowPlayingHorizontalPager(
    nowPlayingPagingItem: LazyPagingItems<Movie>,
    onNavigateDetailScreen: (String) -> Unit
) {
    val state = rememberPagerState()
    HorizontalPager(
        state = state,
        count = nowPlayingPagingItem.itemCount
    ) { page ->
        val pageOffset = (state.currentPage - page) + state.currentPageOffset
        val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)
        NowPlayingItem(
            movie = nowPlayingPagingItem[page]!!,
            pageOffset = pageOffset,
            scale = scaleFactor,
            onNavigateDetailScreen = onNavigateDetailScreen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowPlayingItem(
    movie: Movie,
    pageOffset: Float,
    scale: Float,
    onNavigateDetailScreen: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp / 1.2
    val screenHeight = LocalConfiguration.current.screenHeightDp / 1.8

    Card(
        colors = CardDefaults.cardColors(Color.Transparent),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            onNavigateDetailScreen(movie.id.toString())
        },
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier
            .graphicsLayer {
                lerp(
                    start = 0.10f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
                scaleY = scale
                scaleX = scale

                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }
    ) {

        Box {
            AsyncImage(
                modifier = Modifier
                    .width(screenWidth.dp)
                    .height(screenHeight.dp)
                    .offset {
                        IntOffset(
                            x = (screenWidth.dp * pageOffset).roundToPx(),
                            y = 0,
                        )
                    },
                model = "${Constants.IMAGE_BASE_URL}/w300/${movie.posterPath}",
                contentDescription = "Now Playing Image",
                contentScale = ContentScale.Crop
            )
            MovieInfo(
                movie = movie, modifier = Modifier
                    .width(screenWidth.dp)
                    .align(Alignment.BottomStart)
                    .background(color = Color(0xFF625b71).copy(alpha = 0.6f))
            )

        }
    }
}

@Composable
fun MovieInfo(modifier: Modifier = Modifier, movie: Movie) {
    Box(
        modifier = modifier,
    )
    {
        Column(modifier=Modifier.padding(3.dp)) {
            Text(text = movie.title, maxLines = 1, color = Color.White , overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.size(1.dp))
            movie.releaseDate?.let {
                Text(text = it, color = Color.White)
            }
        }
    }
}
