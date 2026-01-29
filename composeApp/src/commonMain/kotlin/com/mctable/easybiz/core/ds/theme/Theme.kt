package com.mctable.easybiz.core.ds.theme


import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(

    primary = md_light_primary,
    onPrimary = md_light_onPrimary,

    secondary = md_light_secondary,
    onSecondary = md_light_onSecondary,

    background = md_light_background,
    onBackground = md_light_onBackground,

    surface = md_light_surface,
    onSurface = md_light_onSurface,

    surfaceVariant = md_light_surfaceVariant,
    onSurfaceVariant = md_light_onSurfaceVariant,

    error = md_light_error,
    onError = md_light_onError,
)

@Composable
fun PetFinderTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = appTypography(),
        shapes = Shapes(
            small = androidx.compose.foundation.shape.RoundedCornerShape(8),
            medium = androidx.compose.foundation.shape.RoundedCornerShape(12),
            large = androidx.compose.foundation.shape.RoundedCornerShape(16)
        ),
        content = content
    )
}
