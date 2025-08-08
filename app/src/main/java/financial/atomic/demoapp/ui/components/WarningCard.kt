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
import androidx.compose.ui.unit.dp

@Composable
fun WarningCard(
    title: String,
    actionText: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
  Card(
      modifier = modifier.fillMaxWidth(),
      colors =
          CardDefaults.cardColors(
              containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)),
      border =
          androidx.compose.foundation.BorderStroke(
              width = 1.dp, color = MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
      shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.Top) {
          Icon(
              imageVector = Icons.Outlined.Warning,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.error,
              modifier = Modifier.size(24.dp))

          Column(
              modifier = Modifier.weight(1f).padding(start = 12.dp),
              horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(bottom = 8.dp))

                Row(
                    modifier =
                        Modifier.clip(RoundedCornerShape(4.dp))
                            .clickable { onActionClick() }
                            .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                      Text(
                          text = actionText,
                          style = MaterialTheme.typography.bodyMedium,
                          color = MaterialTheme.colorScheme.primary)
                      Icon(
                          imageVector = Icons.Outlined.ArrowForward,
                          contentDescription = null,
                          tint = MaterialTheme.colorScheme.primary,
                          modifier = Modifier.size(16.dp).padding(start = 4.dp))
                      Icon(
                          imageVector = Icons.Outlined.Settings,
                          contentDescription = null,
                          tint = MaterialTheme.colorScheme.primary,
                          modifier = Modifier.size(16.dp).padding(start = 4.dp))
                    }
              }
        }
      }
}
