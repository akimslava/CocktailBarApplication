package ru.akimslava.cocktailbar.ui.screens.creation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientDialog(
    inputText: String,
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
                    value = inputText,
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
private fun AddIngredientDialogPreview() {
    CocktailBarTheme {
        AddIngredientDialog(
            inputText = "",
            onChangeInputText = {},
            isError = false,
            onAddClick = { true },
            closeDialog = {},
        )
    }
}