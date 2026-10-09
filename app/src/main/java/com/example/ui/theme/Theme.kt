package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Bright, warm, elegant Atelier color scheme
// Light backgrounds with darker, high-contrast foreground typography
// Strictly avoids pitch black (#000000) and pure white (#FFFFFF)
private val KimeraBrightColorScheme = lightColorScheme(
    primary = KimeraPrimary,
    onPrimary = KimeraOnPrimary,
    primaryContainer = KimeraPrimaryContainer,
    onPrimaryContainer = KimeraOnPrimaryContainer,
    secondary = KimeraSecondary,
    onSecondary = KimeraOnPrimary,
    secondaryContainer = KimeraSecondaryContainer,
    onSecondaryContainer = KimeraOnSecondaryContainer,
    tertiary = KimeraTertiary,
    onTertiary = KimeraOnPrimary,
    tertiaryContainer = KimeraTertiaryContainer,
    onTertiaryContainer = KimeraOnTertiaryContainer,
    background = KimeraSurface,
    onBackground = KimeraOnSurface,
    surface = KimeraSurface,
    onSurface = KimeraOnSurface,
    surfaceVariant = KimeraSurfaceContainerHighest,
    onSurfaceVariant = KimeraOnSurfaceVariant,
    surfaceContainerLowest = KimeraSurfaceContainerLowest,
    surfaceContainerLow = KimeraSurfaceContainerLow,
    surfaceContainer = KimeraSurfaceContainer,
    surfaceContainerHigh = KimeraSurfaceContainerHigh,
    surfaceContainerHighest = KimeraSurfaceContainerHighest,
    outline = KimeraOutline,
    outlineVariant = KimeraOutlineVariant
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // The Kimera Atelier design is locked to the warm bright aesthetic with light background & dark foreground
    MaterialTheme(
        colorScheme = KimeraBrightColorScheme,
        typography = Typography,
        content = content
    )
}
