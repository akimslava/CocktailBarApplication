package ru.akimslava.cocktailbar.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.domain.Cocktail
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme
import java.io.File

@Composable
fun CocktailInformationScreen(
    cocktail: Cocktail,
    onEditClick: () -> Unit,
    onBackPressed: () -> Unit,
    deleteCocktail: (Cocktail) -> Unit,
    navigateUp: () -> Unit,
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
                InformationPart(
                    cocktail = cocktail,
                    onEditClick = onEditClick,
                    deleteCocktail = deleteCocktail,
                    navigateUp = navigateUp,
                )
            }
        }
    }
}

@Composable
private fun InformationPart(
    cocktail: Cocktail,
    onEditClick: () -> Unit,
    deleteCocktail: (Cocktail) -> Unit,
    navigateUp: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        DeleteCocktailDialog(
            cocktailTitle = cocktail.title,
            onAcceptClick = {
                deleteCocktail(cocktail)
                openDialog.value = false
                navigateUp()
            },
            onDismissRequest = {
                openDialog.value = false
            },
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
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
        IngredientsPart(ingredients = cocktail.ingredients)
        if (cocktail.recipe.isNotBlank()) {
            RecipePart(recipe = cocktail.recipe)
        }
        InteractionRow(
            onEditClick = onEditClick,
            onDeleteClick = { openDialog.value = true },
        )
    }
}

@Composable
private fun IngredientsPart(ingredients: List<String>) {
    repeat(ingredients.size) {
        Text(
            text = ingredients[it],
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
        if (it != ingredients.size - 1) {
            Divider(
                modifier = Modifier.width(9.dp),
                thickness = 1.dp,
                color = colorResource(
                    id = R.color.ingredients_divider_color,
                ),
            )
        }
    }
}

@Composable
private fun RecipePart(recipe: String) {
    Text(
        text = stringResource(id = R.string.recipe_info),
        modifier = Modifier.padding(
            top = 16.dp,
            bottom = 4.dp,
        ),
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(
        text = recipe,
        modifier = Modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
private fun InteractionRow(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = onEditClick,
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    bottom = 16.dp,
                )
                .weight(4f),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.light_blue),
            ),
        ) {
            Text(
                text = stringResource(R.string.edit),
                style = MaterialTheme.typography.displayMedium,
            )
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.weight(1f),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = null,
                tint = Color.Red,
            )

        }
    }
}

@Composable
private fun DeleteCocktailDialog(
    cocktailTitle: String,
    onAcceptClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(
                        R.string.delete_cocktail_agreemant,
                        cocktailTitle,
                    ),
                    modifier = Modifier.padding(bottom = 12.dp),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        textAlign = TextAlign.Center,
                    ),
                )
                Button(
                    onClick = onAcceptClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.light_blue),
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.yes),
                        style = MaterialTheme.typography.displayMedium,
                    )
                }
                OutlinedButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(
                        width = 1.dp,
                        color = colorResource(id = R.color.light_blue),
                    )
                ) {
                    Text(
                        text = stringResource(R.string.no),
                        style = MaterialTheme.typography.displayMedium,
                        color = colorResource(id = R.color.light_blue),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CocktailInformationScreenPreview() {
    CocktailBarTheme {
        CocktailInformationScreen(
            Cocktail(
                title = "Mojito mocktail",
                description = "To make this homemade lemonade, simply " +
                        "combine all the ingredients in a pitcher.",
                ingredients = mutableListOf(
                    "9 cups sprite",
                    "small bunch mint",
                    "3 limes, juiced",
                ),
                recipe = "Muddle lime with leaves from the mint using a " +
                        "pestle and mortar. Mix with sprite. Add ice if needed.",
            ),
            onEditClick = {},
            onBackPressed = {},
            deleteCocktail = {},
            navigateUp = {},
        )
    }
}

@Preview
@Composable
private fun DeleteCocktailDialogPreview() {
    CocktailBarTheme {
        DeleteCocktailDialog(
            cocktailTitle = "Cocktail title",
            onAcceptClick = {},
            onDismissRequest = {},
        )
    }
}