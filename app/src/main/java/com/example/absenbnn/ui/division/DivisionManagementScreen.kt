package com.example.absenbnn.ui.division

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absenbnn.ServiceLocator
import com.example.absenbnn.domain.model.Division
import com.example.absenbnn.domain.model.UserRole
import com.example.absenbnn.ui.AppViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DivisionManagementScreen(
    onBack: () -> Unit,
    viewModel: DivisionViewModel = viewModel(factory = AppViewModelFactory),
) {
    val session by ServiceLocator.sessionRepository.session.collectAsState(initial = null)
    if (session?.role != UserRole.ADMIN) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Master Bidang") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                )
            },
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
            ) {
                Text(
                    "Akses ditolak: hanya Admin yang bisa kelola master bidang.",
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        return
    }

    val divisions by viewModel.divisions.collectAsState()
    var dialogState by remember { mutableStateOf<DivisionDialogState?>(null) }
    var deleteTarget by remember { mutableStateOf<Division?>(null) }
    var message by remember { mutableStateOf<String?>(null) }

    // Delete confirmation dialog
    deleteTarget?.let { target ->
        AlertDialog(
            onDismissRequest = { deleteTarget = null },
            title = { Text("Hapus Bidang") },
            text = { Text("Yakin ingin menghapus \"${target.code} - ${target.name}\"? Aksi tidak dapat dibatalkan.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.delete(target.id) { result ->
                            result.onSuccess {
                                message = "Bidang dihapus"
                                deleteTarget = null
                            }.onFailure { e ->
                                message = e.message ?: "Gagal menghapus"
                                deleteTarget = null
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { deleteTarget = null }) { Text("Batal") }
            },
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Master Bidang", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { dialogState = DivisionDialogState.new() },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah", tint = MaterialTheme.colorScheme.onPrimary)
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            message?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (it.startsWith("Gagal")) MaterialTheme.colorScheme.errorContainer
                        else MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(12.dp),
                        color = if (it.startsWith("Gagal")) MaterialTheme.colorScheme.onErrorContainer
                        else MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
            ) {
                items(divisions) { division ->
                    DivisionCard(
                        division = division,
                        onEdit = { dialogState = DivisionDialogState.edit(division) },
                        onDelete = { deleteTarget = division },
                    )
                }
            }
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
                        message = if (next.id == null) "Bidang ditambahkan" else "Bidang diperbarui"
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
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${division.code} - ${division.name}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = if (division.isActive) "Aktif" else "Nonaktif",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (division.isActive) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error)
            }
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
            Button(
                onClick = { onSave(state.copy(code = code, name = name, isActive = isActive)) },
                modifier = Modifier.height(44.dp),
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        },
        title = { Text(if (state.id == null) "Tambah Bidang" else "Edit Bidang", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it.uppercase() },
                    label = { Text("Kode") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama Bidang") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text("Status Aktif", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                        Text(
                            text = if (isActive) "Bidang aktif" else "Bidang nonaktif",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Switch(checked = isActive, onCheckedChange = { isActive = it })
                }
            }
        },
        shape = RoundedCornerShape(20.dp),
    )
}
