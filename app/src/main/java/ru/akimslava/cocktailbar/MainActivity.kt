package ru.akimslava.cocktailbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import ru.akimslava.cocktailbar.ui.theme.CocktailBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CocktailBarTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = WindowInsets.systemBars
                                .asPaddingValues()
                                .calculateBottomPadding()
                        ),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CocktailApp()
                }
            }
        }
    }
}