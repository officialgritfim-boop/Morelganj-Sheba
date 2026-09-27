package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MorelganjGreenDarkPrimary,
    onPrimary = MorelganjGreenDarkOnPrimary,
    primaryContainer = MorelganjGreenDarkContainer,
    onPrimaryContainer = MorelganjGreenDarkOnContainer,
    secondary = MorelganjTealDarkSecondary,
    onSecondary = MorelganjTealDarkOnSecondary,
    secondaryContainer = MorelganjTealDarkContainer,
    onSecondaryContainer = MorelganjTealDarkOnContainer,
    tertiary = MorelganjAmberDarkTertiary,
    onTertiary = MorelganjAmberDarkOnTertiary,
    tertiaryContainer = MorelganjAmberDarkContainer,
    onTertiaryContainer = MorelganjAmberDarkOnContainer,
    background = SurfaceDark,
    surface = SurfaceDark,
    onBackground = OnSurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = MorelganjGreenPrimary,
    onPrimary = MorelganjGreenOnPrimary,
    primaryContainer = MorelganjGreenContainer,
    onPrimaryContainer = MorelganjGreenOnContainer,
    secondary = MorelganjTealSecondary,
    onSecondary = MorelganjTealOnSecondary,
    secondaryContainer = MorelganjTealContainer,
    onSecondaryContainer = MorelganjTealOnContainer,
    tertiary = MorelganjAmberTertiary,
    onTertiary = MorelganjAmberOnTertiary,
    tertiaryContainer = MorelganjAmberContainer,
    onTertiaryContainer = MorelganjAmberOnContainer,
    background = SurfaceLight,
    surface = SurfaceLight,
    onBackground = OnSurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set false to maintain the dignified Morrelganj emerald brand identity
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
