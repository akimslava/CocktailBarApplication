package ru.akimslava.cocktailbar.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.data.Cocktail
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme
import java.io.File

@Composable
fun CocktailInformationScreen(
    cocktail: Cocktail,
    onEditClick: () -> Unit = {},
    onBackPressed: () -> Unit = {},
) {
    BackHandler(onBack = onBackPressed)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
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
        Column {
            Spacer(modifier = Modifier.height(268.dp))
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
            ) {
                InformationPart(cocktail = cocktail, onEditClick = onEditClick)
            }
        }
    }
}

@Composable
private fun InformationPart(
    cocktail: Cocktail,
    onEditClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = cocktail.title,
            modifier = Modifier.padding(
                top = 28.dp,
                bottom = 16.dp,
            ),
            style = MaterialTheme.typography.headlineMedium,
        )
        if (cocktail.description.isNotBlank()) {
            Text(
                text = cocktail.description,
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        repeat(cocktail.ingredients.size) {
            Text(
                text = cocktail.ingredients[it],
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
            if (it != cocktail.ingredients.size - 1) {
                Divider(
                    modifier = Modifier.width(9.dp),
                    thickness = 1.dp,
                    color = Color(0xFF79747E),
                )
            }
        }
        if (cocktail.recipe.isNotBlank()) {
            Text(
                text = stringResource(id = R.string.recipe_info),
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 2.dp,
                ),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = cocktail.recipe,
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Button(
            onClick = onEditClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    bottom = 16.dp,
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.light_blue),
            ),
        ) {
            Text(
                text = stringResource(R.string.edit),
                style = MaterialTheme.typography.displayMedium,
            )
        }
    }
}

@Preview
@Composable
private fun CocktailInformationScreenPreview() {
    CocktailBarTheme {
        CocktailInformationScreen(Cocktail(
            title = "Title",
            description = "This is description of cocktail",
            ingredients = mutableListOf(
                "9 cups water",
                "2 cups white sugar",
            )
        ))
    }
}