package banquemisr.challenge05.presentation.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import banquemisr.challenge05.core.remote.Constants
import banquemisr.challenge05.core.remote.Response
import banquemisr.challenge05.domain.model.Genre
import banquemisr.challenge05.domain.model.Movie
import banquemisr.challenge05.presentation.home.component.CircularProgress
import banquemisr.challenge05.presentation.home.component.ErrorDialog
import banquemisr.challenge05.presentation.home.component.PageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun MovieDetailsContent(state: Response<Movie>, onNavigateBack: () -> Unit, onRetry: () -> Unit) {
    AnimatedContent(targetState = state) {

        when (it) {
            is Response.Error -> {
                ErrorDialog(
                    errorMessage = it.exception.localizedMessage ?: "Un Handled Error",
                    onRetryClick = { onRetry() }) {

                }
            }

            Response.Loading -> PageLoader()
            is Response.Success -> MovieDetails(it.data) {
                onNavigateBack()
            }
        }

    }
}


@Composable
fun MovieDetails(movie: Movie, onNavigateBack: () -> Unit) {
    Box {
        LazyColumn {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current).data(
                            "${Constants.IMAGE_BASE_URL}/w500/${movie.posterPath}"
                        ).size(Size.ORIGINAL).crossfade(true).build(),
                    )
                    if (painter.state is AsyncImagePainter.State.Loading)
                        CircularProgress(true)
                    else
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painter,
                            contentScale = ContentScale.Crop,
                            contentDescription = "Movie Poster"
                        )

                    Spacer(
                        modifier = Modifier
                            .size(10.dp)
                    )

                }
            }
            item {

                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = movie.title,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    movie.releaseDate?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    movie.popularity?.let {
                        Text(
                            text = "Popularity : $it",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    movie.voteAverage?.let {
                        Text(
                            text = "Vote Average : $it",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))

                    movie.runtime?.let {
                        Text(
                            text = "RunTime : $it",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    movie.genres?.let {
                        GenreList(genres = it)
                    }

                    Spacer(modifier = Modifier.size(10.dp))
                    movie.overview?.let {
                        Text(
                            text = it,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }


                }

            }
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(shape = CircleShape, color = MaterialTheme.colorScheme.background)
                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clip(CircleShape)
                .clickable {
                    onNavigateBack()
                }
                .padding(5.dp)
                .align(Alignment.TopStart)
//                .testTag(TestTag.BackIcon)

        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                "Back Button",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

}

@Composable
fun GenreList(genres: List<Genre>) {
    LazyRow {
        items(genres.size) { index ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(99.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {
                Text(
                    text = genres[index].name,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))

        }
    }
}
