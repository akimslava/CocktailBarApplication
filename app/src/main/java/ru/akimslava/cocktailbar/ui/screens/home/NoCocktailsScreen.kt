package ru.akimslava.cocktailbar.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme

@Composable
fun NoCocktailsScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.summer_holidays_1),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 33.dp)
                .size(283.dp),
        )
        Text(
            text = stringResource(id = R.string.my_cocktails),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(15.dp),
        )
        AddendumFragment()
    }
}

@Composable
private fun AddendumFragment() {
    Text(
        text = stringResource(R.string.add_your_first_cocktail_here),
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .padding(top = 32.dp)
            .width(130.dp),
    )
    Image(
        painter = painterResource(id = R.drawable.arrow_1),
        contentDescription = null,
        contentScale = ContentScale.None,
        modifier = Modifier
            .padding(
                top = 16.dp,
                bottom = 16.dp,
            )
            .height(31.dp),
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun NoCocktailsScreenPreview() {
    CocktailBarTheme {
        NoCocktailsScreen()
    }
}