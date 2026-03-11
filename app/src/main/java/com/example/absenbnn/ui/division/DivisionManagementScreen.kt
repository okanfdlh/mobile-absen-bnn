package com.example.absenbnn.ui.division

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absenbnn.ServiceLocator
import com.example.absenbnn.domain.model.Division
import com.example.absenbnn.domain.model.UserRole
import com.example.absenbnn.ui.AppViewModelFactory

@Composable
fun DivisionManagementScreen(
    onBack: () -> Unit,
    viewModel: DivisionViewModel = viewModel(factory = AppViewModelFactory),
) {
    val session by ServiceLocator.sessionRepository.session.collectAsState(initial = null)
    if (session?.role != UserRole.ADMIN) {
        Scaffold(topBar = { TopAppBar(title = { Text("Master Bidang") }) }) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text("Akses ditolak: hanya Admin yang bisa kelola master bidang.", color = MaterialTheme.colorScheme.error)
                Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Kembali") }
            }
        }
        return
    }
    val divisions by viewModel.divisions.collectAsState()
    var dialogState by remember { mutableStateOf<DivisionDialogState?>(null) }
    var message by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Master Bidang") }) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            message?.let { Text(it, color = MaterialTheme.colorScheme.primary) }

            Button(
                onClick = { dialogState = DivisionDialogState.new() },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Tambah Bidang")
            }

            divisions.forEach { division ->
                DivisionCard(division = division, onClick = { dialogState = DivisionDialogState.edit(division) })
            }

            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Kembali") }
        }
    }

    dialogState?.let { ds ->
        DivisionDialog(
            state = ds,
            onDismiss = { dialogState = null },
            onSave = { next ->
                viewModel.upsert(
                    id = next.id,
                    code = next.code,
                    name = next.name,
                    isActive = next.isActive,
                ) { result ->
                    result.onSuccess {
                        message = "Tersimpan"
                        dialogState = null
                    }.onFailure { e ->
                        message = e.message ?: "Gagal menyimpan"
                    }
                }
            },
        )
    }
}

@Composable
private fun DivisionCard(
    division: Division,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${division.code} - ${division.name}", style = MaterialTheme.typography.titleSmall)
                Text(if (division.isActive) "Aktif" else "Nonaktif", style = MaterialTheme.typography.bodySmall)
            }
            Text("Edit", style = MaterialTheme.typography.labelLarge)
        }
    }
}

private data class DivisionDialogState(
    val id: Long? = null,
    val code: String = "",
    val name: String = "",
    val isActive: Boolean = true,
) {
    companion object {
        fun new(): DivisionDialogState = DivisionDialogState()
        fun edit(division: Division): DivisionDialogState =
            DivisionDialogState(id = division.id, code = division.code, name = division.name, isActive = division.isActive)
    }
}

@Composable
private fun DivisionDialog(
    state: DivisionDialogState,
    onDismiss: () -> Unit,
    onSave: (DivisionDialogState) -> Unit,
) {
    var code by remember(state.id) { mutableStateOf(state.code) }
    var name by remember(state.id) { mutableStateOf(state.name) }
    var isActive by remember(state.id) { mutableStateOf(state.isActive) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = { onSave(state.copy(code = code, name = name, isActive = isActive)) }) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        },
        title = { Text(if (state.id == null) "Tambah Bidang" else "Edit Bidang") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Kode") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Aktif")
                    Switch(checked = isActive, onCheckedChange = { isActive = it })
                }
            }
        },
    )
}
