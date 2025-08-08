package financial.atomic.demoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import financial.atomic.demoapp.config.Environment
import financial.atomic.demoapp.config.TransactConfigViewModel
import financial.atomic.demoapp.constants.AutomationLocatorsDataState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    configViewModel: TransactConfigViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
  val config by remember { derivedStateOf { configViewModel.config } }

  fun resetConfiguration() {
    configViewModel.updateConfig {
      copy(publicToken = "", environment = Environment.SANDBOX, customUrl = "")
    }
    configViewModel.updateTheme { copy(brandColor = null) }
  }

  LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 20.dp)) {
    // Header Section
    item {
      Surface(color = MaterialTheme.colorScheme.surface, tonalElevation = 1.dp) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                  text = "Settings",
                  style = MaterialTheme.typography.headlineMedium,
                  textAlign = TextAlign.Center,
                  modifier = Modifier.padding(bottom = 8.dp))
              Text(
                  text = "Configure Atomic Transact",
                  style = MaterialTheme.typography.bodyLarge,
                  color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                  textAlign = TextAlign.Center,
                  modifier = Modifier.padding(bottom = 12.dp))

              if (config.hasValidToken) {
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(20.dp)) {
                      Row(
                          modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                          verticalAlignment = Alignment.CenterVertically,
                          horizontalArrangement = Arrangement.Center) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp))
                            Text(
                                text = "Configuration Valid",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 4.dp))
                          }
                    }
              }
            }
      }
    }

    // Authentication Section
    item {
      SettingsSection(title = "Authentication") {
        OutlinedTextField(
            value = config.publicToken,
            onValueChange = { configViewModel.updateConfig { copy(publicToken = it) } },
            label = { Text("Public Token *") },
            placeholder = { Text("Enter your Atomic public token") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier =
                Modifier.fillMaxWidth()
                    .testTag(AutomationLocatorsDataState.SettingsScreen.PUBLIC_TOKEN_INPUT))
        Text(
            text = "Get your public token from the Atomic dashboard",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 4.dp))
      }
    }

    // Environment Section
    item {
      SettingsSection(title = "Environment") {
        Environment.values().forEach { environment ->
          val testTag =
              when (environment) {
                Environment.SANDBOX ->
                    AutomationLocatorsDataState.SettingsScreen.ENVIRONMENT_OPTION_SANDBOX
                Environment.PRODUCTION ->
                    AutomationLocatorsDataState.SettingsScreen.ENVIRONMENT_OPTION_PRODUCTION
                Environment.CUSTOM ->
                    AutomationLocatorsDataState.SettingsScreen.ENVIRONMENT_OPTION_CUSTOM
              }

          Row(
              modifier =
                  Modifier.fillMaxWidth()
                      .selectable(
                          selected = config.environment == environment,
                          onClick = {
                            configViewModel.updateConfig { copy(environment = environment) }
                          })
                      .padding(vertical = 8.dp)
                      .testTag(testTag),
              verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = config.environment == environment,
                    onClick = { configViewModel.updateConfig { copy(environment = environment) } })
                Column(modifier = Modifier.padding(start = 12.dp)) {
                  Text(text = environment.displayName, style = MaterialTheme.typography.bodyLarge)
                  Text(
                      text = environment.description,
                      style = MaterialTheme.typography.bodySmall,
                      color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
              }
        }

        if (config.environment == Environment.CUSTOM) {
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
              value = config.customUrl,
              onValueChange = { configViewModel.updateConfig { copy(customUrl = it) } },
              label = { Text("Custom Environment URL") },
              placeholder = { Text("https://your-custom-environment.com/transact") },
              singleLine = true,
              modifier =
                  Modifier.fillMaxWidth()
                      .testTag(AutomationLocatorsDataState.SettingsScreen.CUSTOM_URL_INPUT))
        }
      }
    }

    // Theme Section
    item {
      SettingsSection(title = "Theme") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
              Column {
                Text(text = "Dark Mode", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = if (config.theme.dark) "Using dark theme" else "Using light theme",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
              }
              Switch(
                  checked = config.theme.dark,
                  onCheckedChange = { configViewModel.updateTheme { copy(dark = it) } },
                  modifier =
                      Modifier.testTag(AutomationLocatorsDataState.SettingsScreen.DARK_MODE_SWITCH))
            }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = config.theme.brandColor ?: "",
            onValueChange = {
              configViewModel.updateTheme {
                copy(brandColor = if (it.isBlank()) null else it.trim())
              }
            },
            label = { Text("Brand Color (Optional)") },
            placeholder = { Text("#6366f1") },
            singleLine = true,
            modifier =
                Modifier.fillMaxWidth()
                    .testTag(AutomationLocatorsDataState.SettingsScreen.BRAND_COLOR_INPUT))
        Text(
            text = "Hex color code for customizing the Transact interface",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 4.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
              Column {
                Text(text = "Fullscreen Presentation", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = if (config.showFullscreen) "Present fullscreen" else "Present as modal",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
              }
              Switch(
                  checked = config.showFullscreen,
                  onCheckedChange = { configViewModel.updateConfig { copy(showFullscreen = it) } },
                  modifier =
                      Modifier.testTag(
                          AutomationLocatorsDataState.SettingsScreen.FULLSCREEN_SWITCH))
            }
      }
    }

    // Reset Button Section
    item {
      Column(
          modifier = Modifier.fillMaxWidth().padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { resetConfiguration() },
                modifier =
                    Modifier.fillMaxWidth()
                        .testTag(
                            AutomationLocatorsDataState.SettingsScreen.RESET_CONFIGURATION_BUTTON),
                colors =
                    ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error)) {
                  Text(text = "Reset to Defaults", modifier = Modifier.padding(8.dp))
                }
          }
    }
  }
}

@Composable
private fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
  Card(
      modifier = Modifier.fillMaxWidth().padding(16.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
          Text(
              text = title,
              style = MaterialTheme.typography.titleMedium,
              modifier = Modifier.padding(bottom = 16.dp))
          content()
        }
      }
}
