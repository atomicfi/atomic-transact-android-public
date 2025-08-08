package financial.atomic.demoapp.constants

object AutomationLocatorsDataState {
  object HomeScreen {
    const val SETTINGS_LINK_BUTTON = "settings-link-button"
    const val TRANSACT_OPTION_DEPOSIT = "transact-option-deposit"
    const val TRANSACT_OPTION_VERIFY = "transact-option-verify"
    const val TRANSACT_OPTION_SWITCH = "transact-option-switch"
  }

  object SettingsScreen {
    const val PUBLIC_TOKEN_INPUT = "public-token-input"
    const val CUSTOM_URL_INPUT = "custom-url-input"
    const val BRAND_COLOR_INPUT = "brand-color-input"
    const val ENVIRONMENT_OPTION_SANDBOX = "environment-option-sandbox"
    const val ENVIRONMENT_OPTION_PRODUCTION = "environment-option-production"
    const val ENVIRONMENT_OPTION_CUSTOM = "environment-option-custom"
    const val DARK_MODE_SWITCH = "dark-mode-switch"
    const val FULLSCREEN_SWITCH = "fullscreen-switch"
    const val RESET_CONFIGURATION_BUTTON = "reset-configuration-button"
  }

  object Navigation {
    const val HOME_TAB = "home-tab"
    const val SETTINGS_TAB = "settings-tab"
  }

  object Components {
    fun collapsible(title: String): String {
      return "collapsible-${title.lowercase().replace(Regex("\\s+"), "-")}"
    }
  }
}

// Alias for shorter access (matching the RN pattern)
val automationLocatorsDataState = AutomationLocatorsDataState
