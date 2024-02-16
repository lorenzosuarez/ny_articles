package com.challenge.presentation.common.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.challenge.domain.model.Period
import com.challenge.presentation.R

/**
 * Displays a dialog allowing the user to select a period.
 *
 * This dialog presents a list of periods defined by the [Period] sealed class. Each period is represented
 * by a radio button, allowing for a single selection. The currently selected period is highlighted and persisted
 * when the dialog is confirmed.
 *
 * @param onDismissRequest A callback invoked when the dialog requests to be dismissed.
 * @param onConfirm A callback invoked with the selected period in days when the confirm button is pressed.
 * @param onDismiss A callback invoked when the dismiss button is pressed.
 * @param selectedPeriod The initially selected period when the dialog is displayed.
 *
 * @sample ActionDialogPreview
 */
@Composable
fun ActionDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit,
    selectedPeriod: Period,
) {
    var dialogSelectedPeriod by remember { mutableStateOf(selectedPeriod) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.action_dialog_title)) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Period.values.forEach { period ->
                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        onClick = { dialogSelectedPeriod = period },
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = (period == dialogSelectedPeriod),
                                onClick = { dialogSelectedPeriod = period },
                            )
                            Text(
                                text =
                                    stringResource(
                                        R.string.action_dialog_period_days,
                                        period.days,
                                    ),
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 8.dp),
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(dialogSelectedPeriod.days)
                    onDismiss()
                },
            ) {
                Text(text = stringResource(R.string.action_dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.action_dialog_cancel))
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun ActionDialogPreview() {
    MaterialTheme {
        ActionDialog(
            onDismissRequest = {},
            onConfirm = {},
            onDismiss = {},
            selectedPeriod = Period.One,
        )
    }
}
