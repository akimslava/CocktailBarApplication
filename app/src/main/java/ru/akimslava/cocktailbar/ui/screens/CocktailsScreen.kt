package ru.akimslava.cocktailbar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.data.Cocktail
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme


@Composable
fun CocktailsScreen(
    cocktails: List<Cocktail>,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.my_cocktails),
            modifier = Modifier.padding(
                top = 24.dp,
                bottom = 24.dp,
            ),
            style = TextStyle(
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.didact_gothic)),
                fontWeight = FontWeight(400),
                color = Color(0xFF313131),
                textAlign = TextAlign.Right,
            ),
        )
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
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .size(160.dp)
            .padding(4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(54.dp),
    ) {
        Box() {
            Image(
                painter = painterResource(id = R.drawable.summer_holidays_1),
                contentDescription = cocktail.title,
                contentScale = ContentScale.Crop,
            )
            Text(
                text = cocktail.title,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 34.dp),
                maxLines = 1,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.didact_gothic)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Right,
                ),
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun CocktailsScreenPreview() {
    CocktailBarTheme {
        CocktailsScreen(listOf(
            Cocktail(0, 0, "Title of Cocktail", "", ""),
            Cocktail(0, 0, "Title of Cocktail", "", ""),
            Cocktail(0, 0, "Title of Cocktail", "", ""),
        ))
    }
}

@Preview
@Composable
private fun CocktailViewPreview() {
    CocktailBarTheme {
        CocktailView(Cocktail(0, 0, "Title of Cocktail", "", ""))
    }
}