package financial.atomic.demoapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import financial.atomic.demoapp.config.TransactConfigViewModel

private val DarkColorScheme =
    darkColorScheme(
        primary = PrimaryDark,
        onPrimary = OnPrimaryDark,
        primaryContainer = PrimaryVariantDark,
        onPrimaryContainer = OnPrimaryDark,
        secondary = SecondaryDark,
        onSecondary = OnSecondaryDark,
        background = BackgroundDark,
        onBackground = OnBackgroundDark,
        surface = SurfaceDark,
        onSurface = OnSurfaceDark,
        surfaceVariant = SurfaceDark,
        onSurfaceVariant = OnSurfaceDark,
        error = ErrorDark,
        onError = OnErrorDark)

private val LightColorScheme =
    lightColorScheme(
        primary = Primary,
        onPrimary = OnPrimary,
        primaryContainer = PrimaryVariant,
        onPrimaryContainer = OnPrimary,
        secondary = Secondary,
        onSecondary = OnSecondary,
        background = Background,
        onBackground = OnBackground,
        surface = Surface,
        onSurface = OnSurface,
        surfaceVariant = Surface,
        onSurfaceVariant = OnSurface,
        error = Error,
        onError = OnError)

// Custom colors for the app that aren't part of MaterialTheme
val ColorScheme.success: Color
  @Composable get() = if (isSystemInDarkTheme()) SuccessDark else Success

val ColorScheme.warning: Color
  @Composable get() = if (isSystemInDarkTheme()) WarningDark else Warning

@Composable
fun DemoAppTheme(
    configViewModel: TransactConfigViewModel = viewModel(),
    content: @Composable () -> Unit
) {
  val config = configViewModel.config
  val darkTheme = config.theme.dark

  val colorScheme =
      when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
      }

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colorScheme.surface.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }
  }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
