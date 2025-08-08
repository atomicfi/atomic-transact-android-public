package financial.atomic.demoapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import financial.atomic.demoapp.BuildConfig
import financial.atomic.demoapp.config.TransactConfigViewModel
import financial.atomic.demoapp.constants.AutomationLocatorsDataState
import financial.atomic.demoapp.ui.components.TRANSACT_OPTIONS
import financial.atomic.demoapp.ui.components.TransactOptionCard
import financial.atomic.demoapp.ui.components.WarningCard
import financial.atomic.transact.Config
import financial.atomic.transact.Transact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    configViewModel: TransactConfigViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val config by remember { derivedStateOf { configViewModel.config } }
  var isLoading by remember { mutableStateOf(false) }

  fun launchTransact(option: financial.atomic.demoapp.ui.components.TransactOptionData) {
    if (!config.hasValidToken) {
      onNavigateToSettings()
      return
    }

    isLoading = true

    try {
      val transactConfig = configViewModel.getTransactConfig(option.scope, option.product)
      Transact.present(context, transactConfig)

      // Show success message
      Toast.makeText(context, "${option.title} launched successfully!", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
      Toast.makeText(context, "Error launching ${option.title}: ${e.message}", Toast.LENGTH_LONG)
          .show()
    } finally {
      isLoading = false
    }
  }

  LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 20.dp)) {
    // Header Section
    item {
      Surface(color = MaterialTheme.colorScheme.surface, tonalElevation = 1.dp) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                  text = "Atomic Transact Demo",
                  style = MaterialTheme.typography.headlineMedium,
                  textAlign = TextAlign.Center,
                  modifier = Modifier.padding(bottom = 8.dp))
              Text(
                  text = "Android Integration",
                  style = MaterialTheme.typography.bodyLarge,
                  color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                  textAlign = TextAlign.Center)
              Text(
                  text = "SDK v${BuildConfig.TRANSACT_VERSION_NAME}",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                  textAlign = TextAlign.Center,
                  modifier = Modifier.padding(top = 4.dp))
            }
      }
    }

    // Warning Card if no token
    if (!config.hasValidToken) {
      item {
        WarningCard(
            title = "Don't forget to set your public token!",
            actionText = "Add it now",
            onActionClick = onNavigateToSettings,
            modifier =
                Modifier.padding(16.dp)
                    .testTag(AutomationLocatorsDataState.HomeScreen.SETTINGS_LINK_BUTTON))
      }
    }

    // Options Section
    item { Spacer(modifier = Modifier.height(16.dp)) }

    items(TRANSACT_OPTIONS) { option ->
      val testTag =
          when (option.product) {
            Config.Product.DEPOSIT -> AutomationLocatorsDataState.HomeScreen.TRANSACT_OPTION_DEPOSIT
            Config.Product.VERIFY -> AutomationLocatorsDataState.HomeScreen.TRANSACT_OPTION_VERIFY
            Config.Product.SWITCH -> AutomationLocatorsDataState.HomeScreen.TRANSACT_OPTION_SWITCH
            else -> ""
          }

      TransactOptionCard(
          option = option,
          enabled = config.hasValidToken && !isLoading,
          onClick = { launchTransact(option) },
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp).testTag(testTag))
    }

    // Loading indicator
    if (isLoading) {
      item {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
              CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
              Text(
                  text = "Launching Transact...",
                  style = MaterialTheme.typography.bodyMedium,
                  color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                  modifier = Modifier.padding(top = 8.dp))
            }
      }
    }
  }
}
