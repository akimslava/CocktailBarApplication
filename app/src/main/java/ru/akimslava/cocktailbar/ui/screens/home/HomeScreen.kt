package ru.akimslava.cocktailbar.ui.screens.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.createChooser
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.domain.Cocktail
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    cocktails: List<Cocktail>,
    onButtonClick: () -> Unit,
    onCocktailClick: (Cocktail) -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            BottomBar()
        },
        floatingActionButton = {
            CreationButton(
                onButtonClick = { onButtonClick() },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
    ) {
        val modifier = Modifier
            .padding()
            .fillMaxSize()
        if (cocktails.isEmpty()) {
            NoCocktailsScreen(
                modifier = modifier,
            )
        } else {
            val intentText = stringResource(
                R.string.share_cocktails_intent,
                cocktails.takeLast(4).joinToString(", ") { it.title },
            )
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, intentText)
                type = "text/plain"
            }
            CocktailsScreen(
                cocktails = cocktails,
                onClick = onCocktailClick,
                onShareClick = {
                    startActivity(
                        context,
                        createChooser(intent, null),
                        null,
                    )
                },
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun BottomBar() {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth()
            .height(62.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 50.dp,
                    topEnd = 50.dp,
                )
            ),
        backgroundColor = Color.White,
        cutoutShape = CircleShape,
        elevation = 2.dp,
        content = {},
    )
}

@Composable
private fun CreationButton(
    onButtonClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = { onButtonClick() },
        modifier = Modifier.size(80.dp),
        shape = CircleShape,
        containerColor = colorResource(
            id = R.color.light_blue,
        ),
        contentColor = Color.White,
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.frame,
            ),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun NoCocktailsHomeScreen() {
    CocktailBarTheme {
        HomeScreen(
            cocktails = emptyList(),
            onButtonClick = {},
            onCocktailClick = {},
        )
    }
}

@Preview
@Composable
private fun HasCocktailsHomeScreen() {
    CocktailBarTheme {
        HomeScreen(
            cocktails = listOf(
                Cocktail(),
                Cocktail(),
                Cocktail(),
                Cocktail(),
                Cocktail(),
                Cocktail(),
                Cocktail(),
                Cocktail(),
                Cocktail(),
                Cocktail(),
                Cocktail(),
            ),
            onButtonClick = {},
            onCocktailClick = {},
        )
    }
}
