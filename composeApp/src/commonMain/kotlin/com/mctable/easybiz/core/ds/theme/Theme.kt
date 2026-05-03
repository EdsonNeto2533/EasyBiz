package com.mctable.easybiz.core.ds.theme


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(

    primary = md_light_primary,
    onPrimary = md_light_onPrimary,

    primaryContainer = md_light_primaryContainer,
    onPrimaryContainer = md_light_onPrimaryContainer,

    secondary = md_light_secondary,
    onSecondary = md_light_onSecondary,

    tertiary = md_light_tertiary,
    onTertiary = md_light_onTertiary,

    tertiaryContainer = md_light_tertiaryContainer,
    onTertiaryContainer = md_light_onTertiaryContainer,

    background = md_light_background,
    onBackground = md_light_onBackground,

    surface = md_light_surface,
    onSurface = md_light_onSurface,

    surfaceVariant = md_light_surfaceVariant,
    onSurfaceVariant = md_light_onSurfaceVariant,

    outline = md_light_outline,
    outlineVariant = md_light_outlineVariant,

    error = md_light_error,
    onError = md_light_onError,

    errorContainer = md_light_errorContainer,
    onErrorContainer = md_light_onErrorContainer,
)

@Composable
fun EasyBizTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = appTypography(),
        shapes = Shapes(
            extraSmall = RoundedCornerShape(6.dp),
            small = RoundedCornerShape(10.dp),
            medium = RoundedCornerShape(14.dp),
            large = RoundedCornerShape(20.dp),
            extraLarge = RoundedCornerShape(28.dp)
        ),
        content = content
    )
}
