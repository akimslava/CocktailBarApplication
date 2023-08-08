package ru.akimslava.cocktailbar.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.akimslava.cocktailbar.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineLarge = TextStyle(
        fontSize = 36.sp,
        fontFamily = FontFamily(Font(R.font.didact_gothic)),
        fontWeight = FontWeight(400),
        color = Color(0xFF313131),
        textAlign = TextAlign.Right,
    ),
    labelMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.didact_gothic)),
        fontWeight = FontWeight(400),
        color = Color(0xFF79747E),
        textAlign = TextAlign.Center,
    ),
    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.didact_gothic)),
        fontWeight = FontWeight(400),
        color = Color(0xFFFFFFFF),
        textAlign = TextAlign.Right,
    ),
    headlineMedium = TextStyle(
        fontSize = 32.sp,
        lineHeight = 38.4.sp,
        fontFamily = FontFamily(Font(R.font.didact_gothic)),
        fontWeight = FontWeight(400),
        color = Color(0xFF313131),
        textAlign = TextAlign.Right,
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
        fontFamily = FontFamily(Font(R.font.didact_gothic)),
        fontWeight = FontWeight(400),
        color = Color(0xFF313131),
        textAlign = TextAlign.Center,
    ),
    displayMedium = TextStyle(
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(R.font.didact_gothic)),
        fontWeight = FontWeight(400),
        color = Color(0xFFFFFFFF),
        textAlign = TextAlign.Right,
    ),
)