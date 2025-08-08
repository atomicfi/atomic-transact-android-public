package financial.atomic.demoapp.config

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import financial.atomic.transact.Config

data class TransactConfiguration(
    val publicToken: String = "",
    val environment: Environment = Environment.SANDBOX,
    val customUrl: String = "",
    val theme: Theme = Theme(),
    val showFullscreen: Boolean = true
) {
  val hasValidToken: Boolean
    get() = publicToken.trim().isNotEmpty()
}

data class Theme(
    val dark: Boolean = false,
    val brandColor: String? = null,
    val overlayColor: String? = null
)

enum class Environment(val displayName: String, val description: String) {
  SANDBOX("Sandbox", "Test environment for development"),
  PRODUCTION("Production", "Live environment"),
  CUSTOM("Custom URL", "Use a custom environment URL")
}

class TransactConfigViewModel : ViewModel() {
  var config by mutableStateOf(TransactConfiguration())
    private set

  fun updateConfig(updates: TransactConfiguration.() -> TransactConfiguration) {
    config = config.updates()
  }

  fun updateTheme(updates: Theme.() -> Theme) {
    config = config.copy(theme = config.theme.updates())
  }

  fun getTransactConfig(scope: Config.Scope, product: Config.Product): Config {
    return Config(
        publicToken = config.publicToken.replace(Regex("\\s"), "").uppercase(),
        tasks = listOf(Config.Task(product)),
        scope = scope)
  }
}
