package financial.atomic.demoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import financial.atomic.demoapp.config.TransactConfigViewModel
import financial.atomic.demoapp.constants.AutomationLocatorsDataState
import financial.atomic.demoapp.ui.screens.HomeScreen
import financial.atomic.demoapp.ui.screens.SettingsScreen
import financial.atomic.demoapp.ui.theme.DemoAppTheme

sealed class Screen(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
  object Home : Screen("home", "Home", Icons.Outlined.Home)

  object Settings : Screen("settings", "Settings", Icons.Outlined.Settings)
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val configViewModel: TransactConfigViewModel = viewModel()
      DemoAppTheme(configViewModel = configViewModel) { MainApp(configViewModel = configViewModel) }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(configViewModel: TransactConfigViewModel) {
  val navController = rememberNavController()

  val screens = listOf(Screen.Home, Screen.Settings)

  Scaffold(
      modifier = Modifier.fillMaxSize(),
      bottomBar = {
        NavigationBar {
          val navBackStackEntry by navController.currentBackStackEntryAsState()
          val currentDestination = navBackStackEntry?.destination

          screens.forEach { screen ->
            val testTag =
                when (screen) {
                  Screen.Home -> AutomationLocatorsDataState.Navigation.HOME_TAB
                  Screen.Settings -> AutomationLocatorsDataState.Navigation.SETTINGS_TAB
                }

            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                  navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                  }
                },
                modifier = Modifier.testTag(testTag))
          }
        }
      }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)) {
              composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToSettings = {
                      navController.navigate(Screen.Settings.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                      }
                    },
                    configViewModel = configViewModel)
              }
              composable(Screen.Settings.route) {
                SettingsScreen(configViewModel = configViewModel)
              }
            }
      }
}
