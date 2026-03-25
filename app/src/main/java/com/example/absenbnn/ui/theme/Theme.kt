package com.example.absenbnn.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Primary = Color(0xFF1565C0)
private val OnPrimary = Color(0xFFFFFFFF)
private val PrimaryContainer = Color(0xFFD6E4FF)
private val OnPrimaryContainer = Color(0xFF001C3B)
private val Secondary = Color(0xFF00695C)
private val OnSecondary = Color(0xFFFFFFFF)
private val SecondaryContainer = Color(0xFFB2DFDB)
private val OnSecondaryContainer = Color(0xFF002019)
private val Error = Color(0xFFB00020)
private val OnError = Color(0xFFFFFFFF)
private val Background = Color(0xFFF5F7FA)
private val OnBackground = Color(0xFF1A1C1E)
private val Surface = Color(0xFFFFFFFF)
private val OnSurface = Color(0xFF1A1C1E)
private val SurfaceVariant = Color(0xFFE1E3EC)
private val OnSurfaceVariant = Color(0xFF44474F)
private val Outline = Color(0xFF74777F)

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    error = Error,
    onError = OnError,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
)

@Composable
fun AbsenBnnTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content,
    )
}
