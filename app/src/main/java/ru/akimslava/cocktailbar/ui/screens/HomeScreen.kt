package ru.akimslava.cocktailbar.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.data.Cocktail
import ru.akimslava.cocktailbar.ui.models.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onButtonClick: () -> Unit = {},
    onCocktailClick: (Cocktail) -> Unit = {},
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true,
        )
    }
    Scaffold(
        floatingActionButton = {
            CreationButton(onButtonClick = { onButtonClick() })
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        val cocktails = viewModel.homeUiState.collectAsState().value.cocktailsList
        if (cocktails.isEmpty()) {
            NoCocktailsScreen(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            )
        } else {
            CocktailsScreen(
                cocktails = cocktails,
                onClick = onCocktailClick,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
private fun CreationButton(
    onButtonClick: () -> Unit = {},
) {
    Box {
        FloatingActionButton(
            onClick = { onButtonClick() },
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(40.dp),
            containerColor = colorResource(id = R.color.light_blue),
            contentColor = Color.White,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.frame),
                contentDescription = null,
            )
        }
    }
}
