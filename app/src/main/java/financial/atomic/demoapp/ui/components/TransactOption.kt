package financial.atomic.demoapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import financial.atomic.transact.Config

data class TransactOptionData(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val product: Config.Product,
    val scope: Config.Scope
)

val TRANSACT_OPTIONS =
    listOf(
        TransactOptionData(
            title = "Setup direct deposit",
            description = "Configure direct deposit with your employer",
            icon = Icons.Outlined.AccountBalanceWallet,
            product = Config.Product.DEPOSIT,
            scope = Config.Scope.USER_LINK),
        TransactOptionData(
            title = "Verify employment status",
            description = "Verify your employment information",
            icon = Icons.Outlined.Person,
            product = Config.Product.VERIFY,
            scope = Config.Scope.USER_LINK),
        TransactOptionData(
            title = "Switch payment method",
            description = "Update your payment method with employers",
            icon = Icons.Outlined.CreditCard,
            product = Config.Product.SWITCH,
            scope = Config.Scope.PAY_LINK))

@Composable
fun TransactOptionCard(
    option: TransactOptionData,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
  Card(
      modifier =
          modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).clickable(enabled = enabled) {
            onClick()
          },
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
              Icon(
                  imageVector = option.icon,
                  contentDescription = null,
                  modifier = Modifier.size(32.dp),
                  tint =
                      if (enabled) {
                        MaterialTheme.colorScheme.primary
                      } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                      })

              Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Text(
                    text = option.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color =
                        if (enabled) {
                          MaterialTheme.colorScheme.onSurface
                        } else {
                          MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        })
                Text(
                    text = option.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color =
                        if (enabled) {
                          MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        } else {
                          MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        })
              }

              Icon(
                  imageVector = Icons.Outlined.ChevronRight,
                  contentDescription = null,
                  tint =
                      if (enabled) {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                      } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                      })
            }
      }
}
