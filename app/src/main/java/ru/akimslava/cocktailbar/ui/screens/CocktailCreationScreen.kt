package ru.akimslava.cocktailbar.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.domain.Cocktail
import ru.akimslava.cocktailbar.ui.models.CocktailCreationViewModel
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailCreationScreen(
    viewModel: CocktailCreationViewModel,
    onSaveClick: (Cocktail) -> Unit,
    onCancelClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        val ingredient = viewModel.ingredient
        AddIngredientDialog(
            inputText = ingredient,
            onChangeInputText = viewModel::setIngredient,
            isError = viewModel.triedAdd.value && (
                    ingredient.value.isBlank() ||
                    ingredient.value.length > 30
                ),
            onAddClick = viewModel::addIngredient,
            closeDialog = {
                viewModel.dropIngredient()
                openDialog.value = false
            },
        )
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
            .padding(
                top = WindowInsets.systemBars
                    .asPaddingValues()
                    .calculateTopPadding(),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImageSelect(
            picture = viewModel.cocktail.value.picture?.toUri(),
            onUpload = viewModel::setPicture,
        )
        OutlinedTextField(
            value = viewModel.cocktail.value.title,
            onValueChange = viewModel::setTitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .focusRequester(focusRequester = focusRequester),
            label = { Text(text = stringResource(R.string.title)) },
            placeholder = { Text(text = stringResource(R.string.cocktail_name)) },
            supportingText = { Text(text = stringResource(R.string.add_title)) },
            isError = viewModel.cocktail.value.title.isBlank(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
        )
        OutlinedTextField(
            value = viewModel.cocktail.value.description,
            onValueChange = viewModel::setDescription,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 24.dp)
                .height(154.dp),
            label = { Text(text = stringResource(R.string.description)) },
            supportingText = { Text(text = stringResource(R.string.optional_field)) },
            isError = false,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
        )
        IngredientsLine(
            cocktail = viewModel.cocktail,
            onDeleteClick = viewModel::removeIngredient,
            onAddClick = { openDialog.value = true },
        )
        OutlinedTextField(
            value = viewModel.cocktail.value.recipe,
            onValueChange = viewModel::setRecipe,
            modifier = Modifier
                .fillMaxWidth()
                .height(154.dp),
            label = { Text(text = stringResource(R.string.recipe)) },
            supportingText = { Text(text = stringResource(R.string.optional_field)) },
            isError = false,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
        )
        Button(
            onClick = {
                if (viewModel.isCocktailDataCorrect()) {
                    onSaveClick(viewModel.cocktail.value)
                    onCancelClick()
                } else {
                    focusRequester.requestFocus()
                }
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.light_blue),
            ),
        ) {
            Text(
                text = stringResource(R.string.save),
                style = MaterialTheme.typography.displayMedium,
            )
        }
        OutlinedButton(
            onClick = onCancelClick,
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    bottom = 26.dp,
                )
                .fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(id = R.color.light_blue),
            )
        ) {
            Text(
                text = stringResource(R.string.cancel),
                style = MaterialTheme.typography.displayMedium,
                color = colorResource(id = R.color.light_blue),
            )
        }
    }
}

@Composable
private fun ImageSelect(
    picture: Uri?,
    onUpload: (Uri) -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                saveImageToInternalStorage(context, it)
                onUpload((
                        context.filesDir.absolutePath + "/" +
                        it.encodedPath?.split("/")?.last() +
                        ".jpg"
                    ).toUri())
            }
        },
    )
    if (picture == null) {
        Box(
            modifier = Modifier.clickable {
                launcher.launch("image/*")
            },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.edit_image),
                contentDescription = null,
            )
            Icon(
                painter = painterResource(id = R.drawable.photik),
                contentDescription = null,
            )
        }
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(File(picture.toString()))
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_image_search),
            error = painterResource(id = R.drawable.ic_no_image),
            modifier = Modifier
                .height(204.dp)
                .width(216.dp)
                .fillMaxWidth()
                .clickable {
                    launcher.launch("image/*")
                },
            contentScale = ContentScale.Crop,
        )
    }
}

fun saveImageToInternalStorage(context: Context, uri: Uri) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val outputStream = context.openFileOutput(
        "${uri.encodedPath?.split("/")?.last()}.jpg",
        Context.MODE_PRIVATE,
    )
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IngredientsLine(
    cocktail: MutableState<Cocktail>,
    onDeleteClick: (String) -> Unit,
    onAddClick: () -> Unit,
) {
    val ingredients = cocktail.value.ingredients
    if (ingredients.isEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 24.dp),
        ) {
            AddIngredientButton(onAddClick = onAddClick)
        }
    } else {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(ingredients.size) {
                if (it == ingredients.lastIndex) {
                    LastIngredientAndAddButton(
                        ingredient = ingredients[it],
                        onDeleteClick = onDeleteClick,
                        onAddClick = onAddClick,
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
//                        .clickable { onDeleteClick(ingredient) },
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
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IngredientView(
            ingredient = ingredient,
            onDeleteClick = onDeleteClick,
            modifier = Modifier.padding(end = 6.dp),
        )
        AddIngredientButton(onAddClick = onAddClick)
    }
}

@Composable
private fun AddIngredientButton(onAddClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = stringResource(id = R.string.add_ingredient),
        modifier = Modifier
            .size(24.dp)
            .clickable { onAddClick() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientDialog(
    inputText: MutableState<String>,
    onChangeInputText: (String) -> Unit,
    isError: Boolean,
    onAddClick: () -> Boolean,
    closeDialog: () -> Unit,
) {
    val focusRequester: FocusRequester = remember { FocusRequester() }
    Dialog(onDismissRequest = closeDialog) {
        Card(
            modifier = Modifier.size(372.dp),
            shape = RoundedCornerShape(40.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.add_ingredient),
                    modifier = Modifier.padding(top = 24.dp),
                    style = MaterialTheme.typography.headlineMedium,
                )
                OutlinedTextField(
                    value = inputText.value,
                    onValueChange = onChangeInputText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .focusRequester(focusRequester = focusRequester),
                    label = {
                        Text(text = stringResource(R.string.ingredient_s_name))
                    },
                    supportingText = {
                        Text(text = stringResource(id = R.string.add_title))
                    },
                    isError = isError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                )
                Button(
                    onClick = {
                        if (onAddClick()) {
                            closeDialog()
                        } else {
                            focusRequester.requestFocus()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.light_blue),
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.add),
                        style = MaterialTheme.typography.displayMedium,
                    )
                }
                OutlinedButton(
                    onClick = closeDialog,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = colorResource(id = R.color.light_blue),
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
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
            remember {
                mutableStateOf(
                    Cocktail(
                        ingredients = mutableListOf("One", "Two", "Three", "Four Five"),
                    ),
                )
            },
            onDeleteClick = {},
            onAddClick = {},
        )
    }
}

@Preview
@Composable
private fun AddIngredientDialogPreview() {
    CocktailBarTheme {
        AddIngredientDialog(
            inputText = remember { mutableStateOf("") },
            onChangeInputText = {},
            isError = false,
            onAddClick = { true },
            closeDialog = {},
        )
    }
}