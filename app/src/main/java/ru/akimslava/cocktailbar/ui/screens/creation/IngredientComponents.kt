package ru.akimslava.cocktailbar.ui.screens.creation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.domain.Cocktail
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IngredientsLine(
    cocktail: MutableState<Cocktail>,
    onDeleteClick: (String) -> Unit,
    onAddClick: () -> Unit,
    isIngredientsIncorrect: Boolean,
) {
    val ingredients = cocktail.value.ingredients
    if (ingredients.isEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
        ) {
            AddIngredientButton(
                onAddClick = onAddClick,
                isIngredientsIncorrect = isIngredientsIncorrect,
            )
        }
    } else {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            repeat(ingredients.size) {
                if (it == ingredients.lastIndex) {
                    LastIngredientAndAddButton(
                        ingredient = ingredients[it],
                        onDeleteClick = onDeleteClick,
                        onAddClick = onAddClick,
                        isIngredientsIncorrect = isIngredientsIncorrect,
                    )
                } else {
                    IngredientView(
                        ingredient = ingredients[it],
                        onDeleteClick = onDeleteClick,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun IngredientView(
    ingredient: String,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.padding(2.dp),
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 3.dp,
                horizontal = 10.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = ingredient,
                modifier = Modifier.padding(
                    end = 4.dp,
                ),
            )
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = { onDeleteClick(ingredient) },
            ) {
                Image(
                    modifier = Modifier
                        .size(16.dp),
                    painter = painterResource(id = R.drawable.frame__1_),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun LastIngredientAndAddButton(
    ingredient: String,
    onDeleteClick: (String) -> Unit,
    onAddClick: () -> Unit,
    isIngredientsIncorrect: Boolean,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IngredientView(
            ingredient = ingredient,
            onDeleteClick = onDeleteClick,
            modifier = Modifier.padding(end = 6.dp),
        )
        AddIngredientButton(
            onAddClick = onAddClick,
            isIngredientsIncorrect = isIngredientsIncorrect,
        )
    }
}

@Composable
private fun AddIngredientButton(
    onAddClick: () -> Unit,
    isIngredientsIncorrect: Boolean,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = stringResource(id = R.string.add_ingredient),
        modifier = Modifier
            .size(24.dp)
            .clickable { onAddClick() },
        colorFilter = if (isIngredientsIncorrect) {
            ColorFilter.lighting(Color.Red, Color.Black)
        } else null,
    )
}

@Preview
@Composable
private fun IngredientViewPreview() {
    CocktailBarTheme {
        IngredientView(
            ingredient = "9 cups sprite",
            onDeleteClick = {},
        )
    }
}

@Preview
@Composable
private fun IngredientsLinePreview() {
    CocktailBarTheme {
        IngredientsLine(
            cocktail = remember {
                mutableStateOf(
                    Cocktail(
                        ingredients = mutableListOf("One", "Two", "Three", "Four Five"),
                    ),
                )
            },
            onDeleteClick = {},
            onAddClick = {},
            isIngredientsIncorrect = false,
        )
    }
}

@Preview
@Composable
private fun EmptyIngredientsLineAfterAddButtonPreview() {
    CocktailBarTheme {
        IngredientsLine(
            cocktail = remember { mutableStateOf(Cocktail()) },
            onDeleteClick = {},
            onAddClick = {},
            isIngredientsIncorrect = true,
        )
    }
}