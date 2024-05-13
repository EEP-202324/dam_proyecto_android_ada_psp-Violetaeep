package com.eep.dam.android.aplicacion_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eep.dam.android.aplicacion_android.network.Estudiante
import com.eep.dam.android.aplicacion_android.network.utils.Apis
import com.eep.dam.android.aplicacion_android.ui.theme.Aplicacion_androidTheme
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl("http://127.0.0.1:8080")
    .build()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Aplicacion_androidTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main_screen") {
                    composable("main_screen") {
                        HomeScreen(navController)
                    }
                    composable("student_list_screen") {
                        StudentListScreen(navController)
                    }
                    composable("course_list_screen") {
                        CourseListScreen(navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val drawerState = remember { mutableStateOf(false) }
 //   val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        drawerContent = {
            DrawerHeader()
            DrawerItem("Todos los Estudiantes", onClick = { /* Handle click */ })
            DrawerItem("Eliminar Estudiante", onClick = { /* Handle click */ })
            DrawerItem("Actualizar Estudiante", onClick = { /* Handle click */ })
            Button(onClick = { drawerState.value = false }) {
                Text("Cerrar Drawer")
            }
        }
    ) {
        Scaffold(
            topBar = {
TopAppBar(
title = { Text("Aplicación EEP") },
navigationIcon = {
    IconButton(onClick = { drawerState.value = true }) {
        Icon(Icons.Filled.Menu, contentDescription = "Menu")
    }
}
)
}
) { padding ->
            ContentArea(navController = navController,padding)
        }
    }
}
@Composable
fun CourseListScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Lista de Cursos",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}
@Composable
fun StudentListScreen(navController: NavController) {

    val estudiantesState = remember { mutableStateOf<List<Estudiante>>(emptyList()) }

    LaunchedEffect(key1 = Unit) {
        try {
            val service = Apis.getEstudianteService()
            val response = service.getEstudiantes().execute()
            if (response.isSuccessful) {
                val estudiantes = response.body() ?: emptyList()
                estudiantesState.value = estudiantes
            } else {
                print("La lista de estudiantes está vacia")
                estudiantesState.value = emptyList()
            }
        } catch (e: Exception) {
            print("La lista de estudiantes está vacia")
            estudiantesState.value = emptyList()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Top ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lista de Estudiantes",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn {
            items(estudiantesState.value) { estudiante ->
                EstudianteItem(estudiante = estudiante)
            }
        }
        Button(
            onClick = {/*Actualizar*/},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Text("Actualizar")
        }
        Button(
            onClick = { /*Borrar*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Borrar")
        }
    }
}


@Composable
fun DrawerHeader() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Menú", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text)
    }
}

@Composable
fun ContentArea(navController: NavController,  padding: PaddingValues) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

    }
}
@Composable
fun EstudianteItem(estudiante: Estudiante) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Nombre: ${estudiante.nombre}",
            style = MaterialTheme.typography.displaySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "Apellido: ${estudiante.apellido}",
            style = MaterialTheme.typography.displaySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate("student_list_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Todos los Estudiantes")
        }
        Button(
            onClick = { navController.navigate("course_list_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Mostrar Cursos")
        }
        // Agrega más botones para las otras funciones según sea necesario
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Aplicacion_androidTheme {
        MainScreen(rememberNavController())
    }
}



