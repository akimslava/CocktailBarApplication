package ru.akimslava.cocktailbar.ui.screens.creation

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.domain.Cocktail
import ru.akimslava.cocktailbar.ui.AppViewModelProvider
import ru.akimslava.cocktailbar.ui.models.CocktailCreationViewModel
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme
import java.io.File

@Composable
fun CocktailCreationScreen(
    viewModel: CocktailCreationViewModel,
    onSaveClick: (Cocktail) -> Unit,
    onCancelClick: () -> Unit,
) {
    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        AddIngredientDialog(
            inputText = viewModel.getIngredient(),
            onChangeInputText = viewModel::setIngredient,
            isError = (
                viewModel.haveTriedAddIngredient() &&
                viewModel.isIngredientValid()
            ),
            onAddClick = viewModel::addIngredient,
            closeDialog = {
                viewModel.dropIngredient()
                openDialog.value = false
            },
        )
    }
    CreationPart(
        viewModel = viewModel,
        onAddIngredientClick = { openDialog.value = true },
        onSaveClick = onSaveClick,
        onCancelClick = onCancelClick,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CreationPart(
    viewModel: CocktailCreationViewModel,
    onAddIngredientClick: () -> Unit,
    onSaveClick: (Cocktail) -> Unit,
    onCancelClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusRequester: FocusRequester = remember { FocusRequester() }
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
        val cocktail = viewModel.getCocktail()
        val keyboard = LocalSoftwareKeyboardController.current
        ImageSelect(
            picture = cocktail.picture,
            onUpload = viewModel::setPicture,
        )
        TitlePart(
            title = cocktail.title,
            onTitleChange = viewModel::setTitle,
            focusRequester = focusRequester,
            isError = viewModel.isTitleIncorrect(),
        )
        DescriptionPart(
            description = cocktail.description,
            onDescriptionChange = viewModel::setDescription,
        )
        IngredientsLine(
            cocktail = viewModel.getCocktailState(),
            onDeleteClick = viewModel::removeIngredient,
            onAddClick = onAddIngredientClick,
            isIngredientsIncorrect = viewModel.isIngredientsIncorrect(),
        )
        RecipePart(
            recipe = cocktail.recipe,
            onRecipeChange = viewModel::setRecipe,
        )
        val toast = Toast.makeText(
            LocalContext.current,
            stringResource(
                R.string.at_least_one_ingredient
            ),
            Toast.LENGTH_SHORT,
        )
        val onSaveButtonClick = {
            if (viewModel.isCocktailDataCorrect()) {
                viewModel.successfulAddingCocktail()
                onSaveClick(viewModel.getCocktail())
                onCancelClick()
            } else {
                viewModel.tryAddCocktail()
                if (viewModel.isTitleIncorrect()) {
                    focusRequester.requestFocus()
                    keyboard?.show()
                } else {
                    toast.show()
                }
            }
        }
        SaveButton(
            onClick = { onSaveButtonClick() },
        )
        CancelButton(
            onClick = onCancelClick,
        )
    }
}

@Composable
private fun ImageSelect(
    picture: String?,
    onUpload: (String) -> Unit,
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
                ))
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
                .data(File(picture))
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_image_search),
            error = painterResource(id = R.drawable.ic_no_image),
            modifier = Modifier
                .height(204.dp)
                .width(216.dp)
                .clip(RoundedCornerShape(54.dp))
                .clickable {
                    launcher.launch("image/*")
                },
            contentScale = ContentScale.Crop,
        )
    }
}

private fun saveImageToInternalStorage(context: Context, uri: Uri) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TitlePart(
    title: String,
    onTitleChange: (String) -> Unit,
    focusRequester: FocusRequester,
    isError: Boolean,
) {
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .focusRequester(focusRequester = focusRequester),
        label = { Text(text = stringResource(R.string.title)) },
        placeholder = { Text(text = stringResource(R.string.cocktail_name)) },
        supportingText = { Text(text = stringResource(R.string.add_title)) },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
        ),
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DescriptionPart(
    description: String,
    onDescriptionChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChange,
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipePart(
    recipe: String,
    onRecipeChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = recipe,
        onValueChange = onRecipeChange,
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
}

@Composable
private fun SaveButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
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
}

@Composable
private fun CancelButton(
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
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

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun CocktailCreationScreenPreview() {
    CocktailBarTheme {
        CocktailCreationScreen(
            viewModel = viewModel(
                factory = AppViewModelProvider
                    .CocktailCreationFactory(Cocktail()),
            ),
            onSaveClick = {},
            onCancelClick = {},
        )
    }
}