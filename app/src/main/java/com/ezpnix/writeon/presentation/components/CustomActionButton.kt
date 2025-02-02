package com.ezpnix.writeon.presentation.components

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CleaningServices
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import java.time.LocalDate
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ezpnix.writeon.presentation.screens.edit.model.EditViewModel
import com.ezpnix.writeon.presentation.screens.settings.widgets.copyToClipboard
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun AgreeButton(
    text: String,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = Modifier.imePadding(),
        shape = RoundedCornerShape(24.dp),
        onClick = { onClick() },
        icon = { Icon(Icons.Rounded.CheckCircle, text) },
        text = { Text(text = text) },
    )
}

@Composable
fun CenteredNotesButton(
    onFirstClick: String,
    onSecondClick: () -> Unit,
    onThirdClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(46.dp))
        ExitButton(onClick = onThirdClick)
        Spacer(modifier = Modifier.width(16.dp))
        NotesButton(text = onFirstClick, onClick = onSecondClick)
        Spacer(modifier = Modifier.width(16.dp))
        CalendarButton()
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun ExitButton(onClick: () -> Unit) {
    val activity = LocalContext.current as? Activity

    ExtendedFloatingActionButton(
        modifier = Modifier.size(56.dp),
        shape = RoundedCornerShape(24.dp),
        onClick = {
            Toast.makeText(activity, "Closed App - See you soon!", Toast.LENGTH_SHORT).show()
            onClick()
            activity?.finish()
        }
    ) {
        Icon(Icons.Rounded.CleaningServices, contentDescription = null)
    }
}

@Composable
fun NotesButton(
    text: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    ExtendedFloatingActionButton(
        modifier = Modifier.imePadding(),
        shape = RoundedCornerShape(24.dp),
        onClick = { Toast.makeText(context, "< Swipe from the left for Edit Mode\n Swipe from the right for View Mode >", Toast.LENGTH_SHORT).show()
            onClick() },
        icon = { Icon(Icons.Rounded.Edit, null) },
        text = { Text(text = text) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarButton() {
    val selectedDates = remember { mutableStateOf<List<LocalDate>>(listOf()) }
    val disabledDates = listOf(
        LocalDate.now().minusDays(0),
    )
    val calendarState = rememberUseCaseState()
    val context = LocalContext.current
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
    val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())

    ExtendedFloatingActionButton(
        modifier = Modifier.size(56.dp),
        shape = RoundedCornerShape(24.dp),
        onClick = {
            Toast.makeText(context, "Today is: $dayOfWeek, $currentDate", Toast.LENGTH_SHORT).show()
            calendarState.show()
        }

    ) {
        Icon(Icons.Rounded.CalendarMonth, contentDescription = "Calendar")
    }

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
            disabledDates = disabledDates
        ),
        selection = CalendarSelection.Dates { newDates ->
            selectedDates.value = newDates
        }
    )
}

@Composable
fun CopyButton(viewModel: EditViewModel, onClick: () -> Unit) {
    val context = LocalContext.current

    IconButton(
        modifier = Modifier.size(56.dp),
        onClick = { copyToClipboard(context, viewModel.noteDescription.value.text)}
    ) {
        Icon(Icons.Rounded.CopyAll, null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalButton() {
    val selectedDates = remember { mutableStateOf<List<LocalDate>>(listOf()) }
    val disabledDates = listOf(
        LocalDate.now().minusDays(0),
    )
    val calendarState = rememberUseCaseState()
    val context = LocalContext.current
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
    val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())

    IconButton(
        modifier = Modifier.size(56.dp),
        onClick = {
            Toast.makeText(context, "Today's date is: $dayOfWeek, $currentDate", Toast.LENGTH_SHORT).show()
            calendarState.show()
        }

    ) {
        Icon(Icons.Rounded.CalendarMonth, contentDescription = "Calendar")
    }

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
            disabledDates = disabledDates
        ),
        selection = CalendarSelection.Dates { newDates ->
            selectedDates.value = newDates
        }
    )
}
