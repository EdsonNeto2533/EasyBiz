package com.mctable.easybiz.core.ds.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import easybiz.composeapp.generated.resources.Res
import easybiz.composeapp.generated.resources.lexend_thin
import easybiz.composeapp.generated.resources.lexend_extra_light
import easybiz.composeapp.generated.resources.lexend_light
import easybiz.composeapp.generated.resources.lexend_regular
import easybiz.composeapp.generated.resources.lexend_medium
import easybiz.composeapp.generated.resources.lexend_semi_bold
import easybiz.composeapp.generated.resources.lexend_bold
import easybiz.composeapp.generated.resources.lexend_extra_bold
import easybiz.composeapp.generated.resources.lexend_black
import org.jetbrains.compose.resources.Font

@Composable
fun lexendFontFamily(): FontFamily = FontFamily(
    Font(resource = Res.font.lexend_thin, weight = FontWeight.Thin),
    Font(resource = Res.font.lexend_extra_light, weight = FontWeight.ExtraLight),
    Font(resource = Res.font.lexend_light, weight = FontWeight.Light),
    Font(resource = Res.font.lexend_regular, weight = FontWeight.Normal),
    Font(resource = Res.font.lexend_medium, weight = FontWeight.Medium),
    Font(resource = Res.font.lexend_semi_bold, weight = FontWeight.SemiBold),
    Font(resource = Res.font.lexend_bold, weight = FontWeight.Bold),
    Font(resource = Res.font.lexend_extra_bold, weight = FontWeight.ExtraBold),
    Font(resource = Res.font.lexend_black, weight = FontWeight.Black),
)

@Composable
fun appTypography(): Typography {
    val fontFamily = lexendFontFamily()
    return Typography(
        displayLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Thin,
            fontSize = 57.sp,
            lineHeight = 64.sp
        ),
        displayMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 45.sp,
            lineHeight = 52.sp
        ),
        displaySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 36.sp,
            lineHeight = 44.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            lineHeight = 40.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            lineHeight = 36.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        titleSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        bodySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),
        labelLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),
        labelSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Thin,
            fontSize = 11.sp,
            lineHeight = 16.sp
        )
    )
}
