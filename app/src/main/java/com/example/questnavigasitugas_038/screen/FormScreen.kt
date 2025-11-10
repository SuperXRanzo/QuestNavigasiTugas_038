package com.example.questnavigasitugas_038.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.questnavigasitugas_038.navigation.Screen
import com.example.questnavigasitugas_038.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavController, viewModel: MainViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        SuccessDialog(
            viewModel = viewModel,
            onDismiss = {
                showDialog = false
                navController.popBackStack()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulir Pendaftaran") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Agar bisa di-scroll
        ) {

            OutlinedTextField(
                value = viewModel.nama.value,
                onValueChange = { viewModel.nama.value = it },
                label = { Text("Nama Lengkap") },
                placeholder = { Text("Isikan nama lengkap") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(Modifier.fillMaxWidth()) {
                Text("Jenis Kelamin", style = MaterialTheme.typography.labelLarge)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewModel.gender.value == "Laki-laki",
                        onClick = { viewModel.gender.value = "Laki-laki" }
                    )
                    Text("Laki-laki", modifier = Modifier.selectable(
                        selected = viewModel.gender.value == "Laki-laki",
                        onClick = { viewModel.gender.value = "Laki-laki" }
                    ).padding(end = 16.dp))

                    RadioButton(
                        selected = viewModel.gender.value == "Perempuan",
                        onClick = { viewModel.gender.value = "Perempuan" }
                    )
                    Text("Perempuan", modifier = Modifier.selectable(
                        selected = viewModel.gender.value == "Perempuan",
                        onClick = { viewModel.gender.value = "Perempuan" }
                    ))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = viewModel.status.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Status Perkawinan") },
                    placeholder = { Text("Pilih status kawin")},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    viewModel.statusOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                viewModel.status.value = option
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.alamat.value,
                onValueChange = { viewModel.alamat.value = it },
                label = { Text("Alamat") },
                placeholder = { Text("Isikan alamat") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f)) // Mendorong tombol ke bawah

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() }, // Kembali
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Kembali")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        viewModel.addParticipant()
                        showDialog = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

@Composable
fun SuccessDialog(viewModel: MainViewModel, onDismiss: () -> Unit) {
    val submittedData = viewModel.lastSubmittedData.value

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Data Berhasil Disimpan") },
        text = {
            if (submittedData != null) {
                Column {
                    DialogRow("Nama", submittedData.nama)
                    DialogRow("Jenis Kelamin", submittedData.gender)
                    DialogRow("Status", submittedData.status)
                    DialogRow("Alamat", submittedData.alamat)
                }
            } else {
                Text("Data tidak ditemukan.")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
fun DialogRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$label ",
            modifier = Modifier.width(100.dp) // Agar rapi
        )
        Text(": $value")
    }
}