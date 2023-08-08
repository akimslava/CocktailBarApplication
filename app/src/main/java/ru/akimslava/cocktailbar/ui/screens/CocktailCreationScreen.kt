package ru.akimslava.cocktailbar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.ui.AppViewModelProvider
import ru.akimslava.cocktailbar.ui.models.HomeViewModel
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailCreationScreen(
    viewModel: HomeViewModel,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val focusRequester = remember { FocusRequester() }
        Image(
            painter = painterResource(id = R.drawable.summer_holidays_1),
            contentDescription = null,
            modifier = Modifier
                .width(216.dp)
                .height(204.dp),
        )
        OutlinedTextField(
            value = viewModel.currentCocktail.value.title,
            onValueChange = viewModel::setCurrentCocktailTitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            label = { Text(text = stringResource(R.string.title)) },
            placeholder = { Text(text = stringResource(R.string.cocktail_name)) },
            supportingText = { Text(text = stringResource(R.string.add_title)) },
            isError = viewModel.currentCocktail.value.title.isBlank(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
        )
        OutlinedTextField(
            value = viewModel.currentCocktail.value.description,
            onValueChange = viewModel::setCurrentCocktailDescription,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 24.dp)
                .height(154.dp),
            label = { Text(text = stringResource(R.string.description)) },
//            placeholder = { Text(text = stringResource(R.string.optional_field)) },
            supportingText = { Text(text = stringResource(R.string.optional_field)) },
            isError = false,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
        )
        OutlinedTextField(
            value = viewModel.currentCocktail.value.recipe,
            onValueChange = viewModel::setCurrentCocktailRecipe,
            modifier = Modifier
                .fillMaxWidth()
                .height(154.dp),
            label = { Text(text = stringResource(R.string.recipe)) },
//            placeholder = { Text(text = stringResource(R.string.optional_field)) },
            supportingText = { Text(text = stringResource(R.string.optional_field)) },
            isError = false,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
        )
        // padding 24.dp
    }
}