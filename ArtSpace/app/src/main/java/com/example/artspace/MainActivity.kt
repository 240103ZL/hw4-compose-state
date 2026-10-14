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
            imageRes = R.drawable.art_1,
            contentDescription = R.string.art_1_title
        )
        Spacer(modifier = Modifier.height(32.dp))
        ArtworkDescriptor(
            title = R.string.art_1_title,
            artist = R.string.art_1_artist,
            year = R.string.art_1_year
        )
        Spacer(modifier = Modifier.height(24.dp))
        DisplayController(
            onPreviousClick = { },
            onNextClick = { }
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