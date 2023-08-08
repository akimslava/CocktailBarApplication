package ru.akimslava.cocktailbar.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.data.Cocktail
import ru.akimslava.cocktailbar.ui.AppViewModelProvider
import ru.akimslava.cocktailbar.ui.models.HomeViewModel
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = AppViewModelProvider.Factory,
    ),
    onButtonClick: () -> Unit = {},
) {
    Scaffold(
        floatingActionButton = {
            CreationButton(onButtonClick = { onButtonClick() })
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        val cocktails = viewModel.homeUiState.collectAsState().value.cocktailsList
        if (cocktails.isEmpty()) {
            val systemUiController = rememberSystemUiController()
//    systemUiController.setSystemBarsColor(
//        color = Color.White,
//    )
//    TopAppBar(colors =  backgroundColor = Color.White)
            NoCocktailsScreen(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            )
        } else {
            CocktailsScreen(
                cocktails = cocktails,
                onClick = {},
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
            containerColor = Color(0xFF4B97FF),
            contentColor = Color.White,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.frame),
                contentDescription = null,
            )
        }
    }
}
