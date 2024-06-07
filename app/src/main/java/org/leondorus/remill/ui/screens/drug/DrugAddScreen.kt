package org.leondorus.remill.ui.screens.drug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import org.leondorus.remill.R
import org.leondorus.remill.ui.AppViewModelProvider
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset.UTC

@Composable
fun DrugAddScreen(
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DrugAddViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = AppViewModelProvider.Factory
    ),
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    DrugAddBody(
        onSaveButtonClick = {
        coroutineScope.launch {
            viewModel.saveCurDrug()
            goBack()
        }
    },
        onCancelButtonClick = goBack,

        drugName = uiState.drugName,
        onDrugNameUpdate = { viewModel.updateDrugName(it) },

        notifGroupName = uiState.notifGroupName,
        onNotifGroupNameChange = {viewModel.updateNotifGroupName(it)},
        notifGroupTimes = uiState.times,
        onNotifTimeDelete = { viewModel.deleteNotifTime(it) },

        isDialogShown = uiState.isDialogShown,
        onStartNewDialog = { viewModel.showDialog() },
        onDialogDismiss = { viewModel.dismissDialog() },
        onDialogAdd = { viewModel.addNotifTime(it); viewModel.dismissDialog() },
        modifier = modifier
    )
}

@Composable
fun DrugAddBody(
    onCancelButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
    drugName: String,
    onDrugNameUpdate: (String) -> Unit,
    notifGroupName: String,
    onNotifGroupNameChange: (String) -> Unit,
    notifGroupTimes: List<LocalDateTime>,
    onNotifTimeDelete: (LocalDateTime) -> Unit,
    isDialogShown: Boolean,
    onStartNewDialog: () -> Unit,
    onDialogDismiss: () -> Unit,
    onDialogAdd: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (isDialogShown) {
        AddNewDateTimeDialog(onAdd = { onDialogAdd(it) }, onCancel = { onDialogDismiss() })
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = drugName,
            onValueChange = onDrugNameUpdate,
            label = { Text(stringResource(R.string.drug_name)) },
            modifier = Modifier.fillMaxWidth()
        )

        NotifGroupAddWidget(
            notifGroupName,
            onNotifGroupNameChange,
            notifGroupTimes,
            onNotifTimeDelete,
            onStartNewDialog,
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = onCancelButtonClick, modifier = Modifier.weight(1f),
            ) {
                Text(text = stringResource(R.string.cancel))
            }
            Button(
                onClick = onSaveButtonClick, modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}

@Composable
fun NotifGroupAddWidget(
    notifGroupName: String,
    onNotifGroupNameChange: (String) -> Unit,
    times: List<LocalDateTime>,
    onNotifTimeDelete: (LocalDateTime) -> Unit,
    onStartNewDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column {
            OutlinedTextField(
                value = notifGroupName,
                onValueChange = onNotifGroupNameChange,
                label = { Text(stringResource(R.string.notifgroup_name)) })

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(times) {
                    Row (modifier = Modifier.fillMaxWidth()) {
                        Text(it.toString())
                        Button(onClick = { onNotifTimeDelete(it) }) {
                            Text(stringResource(R.string.delete_this_notification))
                        }
                    }
                }
            }

            Button(onClick = onStartNewDialog) {
                Text(text = stringResource(R.string.add_new_time))
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewDateTimeDialog(
    onAdd: (LocalDateTime) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentTime = LocalDateTime.now()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentTime.toEpochSecond(UTC) * 1000,
        initialDisplayMode = DisplayMode.Input)
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.hour,
        initialMinute = currentTime.minute
    )

    val curLocalDate: LocalDate?
    val epochDateMillis = datePickerState.selectedDateMillis
    curLocalDate = if (epochDateMillis == null) null
    else Instant.ofEpochMilli(epochDateMillis).atZone(UTC).toLocalDate()

    val curLocalTime = LocalTime.of(timePickerState.hour, timePickerState.minute)

    val curLocalDateTime: LocalDateTime? = curLocalDate?.atTime(curLocalTime)

    Dialog(onDismissRequest = onCancel, DialogProperties( usePlatformDefaultWidth = false )) {
        Card(modifier = modifier.padding(8.dp).fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                DatePicker(datePickerState)
                TimeInput(timePickerState)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    OutlinedButton(onClick = onCancel) {
                        Text(stringResource(R.string.cancel))
                    }
                    Button(onClick = {
                        if (curLocalDateTime != null) onAdd(curLocalDateTime)
                    }) {
                        Text(stringResource(R.string.add))
                    }
                }
            }
        }
    }
}