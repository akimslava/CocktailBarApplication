package ru.akimslava.cocktailbar.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.domain.Cocktail
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme
import java.io.File

@Composable
fun CocktailsScreen(
    cocktails: List<Cocktail>,
    onClick: (Cocktail) -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            top = WindowInsets.systemBars
                .asPaddingValues()
                .calculateBottomPadding(),
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 24.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.weight(2f))
            Text(
                text = stringResource(id = R.string.my_cocktails),
                style = MaterialTheme.typography.headlineLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onShareClick,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = null,
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(12.dp),
        ) {
            items(cocktails) { cocktail ->
                CocktailView(
                    cocktail = cocktail,
                    onClick = onClick,
                )
            }
        }
    }
}

@Composable
private fun CocktailView(
    cocktail: Cocktail,
    onClick: (Cocktail) -> Unit,
) {
    Card(
        modifier = Modifier
            .size(160.dp)
            .padding(4.dp)
            .clickable { onClick(cocktail) },
        shape = RoundedCornerShape(54.dp),
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(File(cocktail.picture.toString()))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_image_search),
                error = painterResource(id = R.drawable.ic_no_image),
                modifier = Modifier
                    .height(329.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = cocktail.title,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 34.dp)
                    .padding(horizontal = 8.dp),
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview
@Composable
private fun CocktailViewPreview() {
    CocktailBarTheme {
        CocktailView(
            cocktail = Cocktail(
                title = "Title of cocktail",
            ),
            onClick = {},
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun CocktailsScreenPreview() {
    val cocktailsList = listOf(
        Cocktail(
            title = "Title of Cocktail",
        ),
        Cocktail(
            title = "Title of Cocktail",
        ),
        Cocktail(
            title = "Title of Cocktail",
        ),
    )
    CocktailBarTheme {
        CocktailsScreen(
            cocktails = cocktailsList,
            onClick = {},
            onShareClick = {},
        )
    }
}