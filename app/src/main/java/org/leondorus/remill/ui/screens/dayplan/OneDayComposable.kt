package org.leondorus.remill.ui.screens.dayplan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.leondorus.remill.domain.sharing.HolyTriple


@Composable
fun OneDayComposable(
    onTimeClick: (HolyTriple) -> Unit,
    holyTriples: List<HolyTriple>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(holyTriples) { holyTriple ->
            OneTimeCard(
                onClick = { onTimeClick(holyTriple) },
                time = holyTriple,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun OneTimeCard(onClick: () -> Unit, time: HolyTriple, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Drug name: %s".format(time.second.drug.name),
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = time.first.toString())
        }
    }
}