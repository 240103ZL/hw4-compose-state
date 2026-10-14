package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceLayout()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier) {
    var currentArtwork by remember { mutableStateOf(1) }

    val imageRes = when (currentArtwork) {
        1 -> R.drawable.art_1
        2 -> R.drawable.art_2
        else -> R.drawable.art_3
    }
    val titleRes = when (currentArtwork) {
        1 -> R.string.art_1_title
        2 -> R.string.art_2_title
        else -> R.string.art_3_title
    }
    val artistRes = when (currentArtwork) {
        1 -> R.string.art_1_artist
        2 -> R.string.art_2_artist
        else -> R.string.art_3_artist
    }
    val yearRes = when (currentArtwork) {
        1 -> R.string.art_1_year
        2 -> R.string.art_2_year
        else -> R.string.art_3_year
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ArtworkWall(
            imageRes = imageRes,
            contentDescription = titleRes
        )
        Spacer(modifier = Modifier.height(32.dp))
        ArtworkDescriptor(
            title = titleRes,
            artist = artistRes,
            year = yearRes
        )
        Spacer(modifier = Modifier.height(24.dp))
        DisplayController(
            onPreviousClick = {
                currentArtwork = when (currentArtwork) {
                    1 -> 3
                    2 -> 1
                    else -> 2
                }
            },
            onNextClick = {
                currentArtwork = when (currentArtwork) {
                    1 -> 2
                    2 -> 3
                    else -> 1
                }
            }
        )
    }
}
@Composable
fun ArtworkWall(
    @DrawableRes imageRes: Int,
    @StringRes contentDescription: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = stringResource(contentDescription),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(24.dp)
                .heightIn(max = 400.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(
    @StringRes title: Int,
    @StringRes artist: Int,
    @StringRes year: Int,
    modifier: Modifier = Modifier
) {
    val artistText = stringResource(artist)
    val yearText = stringResource(year)

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(artistText)
                    }
                    append(" ($yearText)")
                }
            )
        }
    }
}

@Composable
fun DisplayController(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = onPreviousClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.previous))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = onNextClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}