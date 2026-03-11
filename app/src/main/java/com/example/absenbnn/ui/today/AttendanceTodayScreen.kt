package com.example.absenbnn.ui.today

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absenbnn.domain.model.UserRole
import com.example.absenbnn.ui.AppViewModelFactory

@Composable
fun AttendanceTodayScreen(
    onBack: () -> Unit,
    viewModel: AttendanceTodayViewModel = viewModel(factory = AppViewModelFactory),
) {
    val state by viewModel.state.collectAsState()
    if (state.session?.role != UserRole.ADMIN) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Absen Hari Ini") }) },
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text("Akses ditolak: hanya Admin yang bisa input/edit absensi.", color = MaterialTheme.colorScheme.error)
                Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Kembali") }
            }
        }
        return
    }
    val selectedDivision = state.divisions.firstOrNull { it.id == state.selectedDivisionId }

    val jumlah = state.jumlahInt()
    val hadir = state.hadirInt()
    val kurang = jumlah - hadir
    val breakdownTotal = state.breakdown().total()
    val mismatch = kurang >= 0 && breakdownTotal != kurang
    val invalidHadir = hadir > jumlah

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Absen Hari Ini") },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Tanggal", style = MaterialTheme.typography.titleSmall)
                    OutlinedTextField(
                        value = state.dateString,
                        onValueChange = { viewModel.setDate(it) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("YYYY-MM-DD") },
                    )
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Bidang", style = MaterialTheme.typography.titleSmall)
                    var expanded by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = selectedDivision?.let { "${it.code} - ${it.name}" }.orEmpty(),
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        singleLine = true,
                        placeholder = { Text("Pilih bidang") },
                        trailingIcon = { Text(if (expanded) "▲" else "▼") },
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        state.divisions.forEach { d ->
                            DropdownMenuItem(
                                text = { Text("${d.code} - ${d.name}") },
                                onClick = {
                                    expanded = false
                                    viewModel.selectDivision(d.id)
                                },
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        NumberField(
                            label = "Jumlah",
                            value = state.jumlahText,
                            onValueChange = viewModel::setJumlah,
                            modifier = Modifier.weight(1f),
                        )
                        NumberField(
                            label = "Hadir",
                            value = state.hadirText,
                            onValueChange = viewModel::setHadir,
                            modifier = Modifier.weight(1f),
                        )
                    }

                    if (invalidHadir) {
                        Text(
                            text = "Validasi: hadir tidak boleh lebih besar dari jumlah",
                            color = MaterialTheme.colorScheme.error,
                        )
                    }

                    Text("Kurang: ${if (kurang >= 0) kurang else 0}", style = MaterialTheme.typography.titleMedium)
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Keterangan Kurang", style = MaterialTheme.typography.titleSmall)

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        NumberField("Dinas", state.dinasText, viewModel::setDinas, Modifier.weight(1f))
                        NumberField("Terlambat", state.terlambatText, viewModel::setTerlambat, Modifier.weight(1f))
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        NumberField("Sakit", state.sakitText, viewModel::setSakit, Modifier.weight(1f))
                        NumberField("Cuti", state.cutiText, viewModel::setCuti, Modifier.weight(1f))
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        NumberField("Izin", state.izinText, viewModel::setIzin, Modifier.weight(1f))
                        NumberField("Off Dinas", state.offDinasText, viewModel::setOffDinas, Modifier.weight(1f))
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        NumberField("Lain-lain", state.lainLainText, viewModel::setLainLain, Modifier.weight(1f))
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    OutlinedTextField(
                        value = state.lainLainNote,
                        onValueChange = viewModel::setLainLainNote,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Catatan lain-lain (opsional)") },
                    )

                    val indicatorColor = if (mismatch) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    Text(
                        text = "Total keterangan: $breakdownTotal (harus = kurang $kurang)",
                        color = indicatorColor,
                    )
                }
            }

            state.message?.let { msg ->
                Text(text = msg, color = MaterialTheme.colorScheme.primary)
            }

            Button(
                onClick = viewModel::save,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isSaving && state.session != null,
            ) {
                Text(if (state.isSaving) "Menyimpan..." else if (state.loadedExisting == null) "Simpan" else "Update")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = onBack) { Text("Kembali") }
                if (state.loadedExisting != null) {
                    Text("Mode Edit", style = MaterialTheme.typography.labelLarge)
                } else {
                    Text("Mode Input", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Composable
private fun NumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it.filter { ch -> ch.isDigit() }.take(9)) },
        label = { Text(label) },
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}
