package ru.akimslava.cocktailbar.ui.screens.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.createChooser
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalConfiguration
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
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val height = 62.dp
    val circleRadius = 50.dp
    val cutoutSize = (circleRadius + 2.dp) * 2
    val borderColor = Color.LightGray
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = screenWidth, height = height)
            .clickable(enabled = false, onClick = {}),
    ) {
        clipPath(
            path = Path().apply {
                addOval(
                    oval = Rect(
                        offset = Offset(
                            x = (screenWidth / 2 - cutoutSize / 2).toPx(),
                            y = -(cutoutSize / 2).toPx(),
                        ),
                        Size(
                            width = cutoutSize.toPx(),
                            height = cutoutSize.toPx(),
                        ),
                    )
                )
            },
            clipOp = ClipOp.Difference,
        ) {
            drawRoundRect(
                color = Color.White,
                size = Size(
                    width = screenWidth.toPx(),
                    height = (height * 2).toPx(),
                ),
                cornerRadius = CornerRadius(
                    x = circleRadius.toPx(),
                    y = circleRadius.toPx(),
                ),
            )
            drawRoundRect(
                color = borderColor,
                size = Size(
                    width = screenWidth.toPx(),
                    height = (height * 2).toPx(),
                ),
                cornerRadius = CornerRadius(
                    x = circleRadius.toPx(),
                    y = circleRadius.toPx(),
                ),
                style = Stroke(width = 2.dp.toPx()),
            )
            clipRect {
                drawCircle(
                    color = borderColor,
                    radius = (cutoutSize / 2 + 1.dp).toPx(),
                    center = Offset(
                        x = (screenWidth / 2).toPx(),
                        y = 0f,
                    ),
                    style = Stroke(width = 2.dp.toPx()),
                )
            }
        }
    }
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
private fun BottomBarPreview() {
    CocktailBarTheme {
        BottomBar()
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
