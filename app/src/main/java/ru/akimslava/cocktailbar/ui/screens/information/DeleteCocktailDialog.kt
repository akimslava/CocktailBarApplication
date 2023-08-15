package ru.akimslava.cocktailbar.ui.screens.information

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.akimslava.cocktailbar.R
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme

@Composable
fun DeleteCocktailDialog(
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
private fun DeleteCocktailDialogPreview() {
    CocktailBarTheme {
        DeleteCocktailDialog(
            cocktailTitle = "Cocktail title",
            onAcceptClick = {},
            onDismissRequest = {},
        )
    }
}