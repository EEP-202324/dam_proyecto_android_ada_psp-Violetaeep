package com.eep.dam.android.aplicacion_android
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eep.dam.android.aplicacion_android.network.Estudiante
import com.eep.dam.android.aplicacion_android.network.EstudianteApi
import com.eep.dam.android.aplicacion_android.ui.theme.Aplicacion_androidTheme
import com.epp.dam.android.aplicacion_android.R
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Aplicacion_androidTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "principal") {
                    composable("principal") {
                        HomeScreen(navController)
                    }
                    composable("pantalla_estudiantes") {
                        StudentListScreen(navController)
                    }
                    composable("pantalla_anadir") {
                        AddStudentScreen(navController)
                    }
                    composable("pantalla_estudiante/{id}"){
                        val id = it.arguments?.getString("id")
                        id?.let { idEstudiante ->
                            StudentScreen(navController, idEstudiante)
                        } ?: run {
                            HomeScreen(navController)
                        }
                    }
                    composable("pantalla_actualizar/{id}"){

                        val id = it.arguments?.getString("id")
                        id?.let { idEstudiante ->
                            UpdateScreen(navController, idEstudiante)
                        } ?: run{
                            HomeScreen(navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun StudentListScreen(navController: NavController) {

        val estudiantesState = remember { mutableStateOf<List<Estudiante>>(emptyList()) }

        LaunchedEffect(key1 = Unit) {
            try {
                val estudiantes: List<Estudiante> = View.compartido.getEstudiantes()
                estudiantesState.value = estudiantes

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
                    EstudianteItem(estudiante = estudiante,navController)
                }
            }
        }
    }

    @Composable
    fun EstudianteItem(estudiante: Estudiante, navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "${estudiante.nombre} ${estudiante.apellido}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Curso: ${estudiante.curso}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.Gray
                    )
                    Row(
                        modifier = Modifier.padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                View.compartido.deleteEstudiante(estudiante.idEstudiante)
                                navController.navigate("pantalla_estudiantes")
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text("Borrar")
                        }

                        Button(
                            onClick = {
                                navController.navigate("pantalla_estudiante/${estudiante.idEstudiante}")
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text("Ver más")
                        }
                    }
                    Row(
                        modifier = Modifier.padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("pantalla_actualizar/${estudiante.idEstudiante}")
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text("Actualizar")
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun StudentScreen(navController: NavController, id: String){

        val id = id.toInt()

        val estudianteState = remember { mutableStateOf<Estudiante>(Estudiante(0, "", "", "", "", null, null)) }

        Log.i("Estudiante", "$id")

        LaunchedEffect(key1 = Unit) {
            try {
                val estudiante = View.compartido.getEstudianteById(id)
                estudianteState.value = estudiante
                Log.i("Estudiante", "$estudianteState")
            } catch (e: Exception) {
                Log.e("ErrorRespuesta", "Estudiante no encontrado")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                shape = RoundedCornerShape(16.dp)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nombre: ${estudianteState.value.nombre} ${estudianteState.value.apellido}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Fecha de nacimiento: ${estudianteState.value.fechaNacimiento}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Email: ${estudianteState.value.email}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Curso: ${estudianteState.value.curso ?: "N/A"}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Grado: ${estudianteState.value.grado ?: "N/A"}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddStudentScreen(navController: NavController) {
    var estudiante by remember { mutableStateOf(Estudiante(0, "", "", "", "", null, null)) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Añadir estudiante",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estudiante.nombre,
            onValueChange = { estudiante = estudiante.copy(nombre = it) },
            label = { Text("Nombre") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estudiante.apellido,
            onValueChange = { estudiante = estudiante.copy(apellido = it) },
            label = { Text("Apellido") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estudiante.fechaNacimiento,
            onValueChange = { estudiante = estudiante.copy(fechaNacimiento = it) },
            label = { Text("Fecha de nacimiento") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estudiante.email,
            onValueChange = { estudiante = estudiante.copy(email = it) },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estudiante.curso ?: "",
            onValueChange = { estudiante = estudiante.copy(curso = it) },
            label = { Text("Curso") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estudiante.grado ?: "",
            onValueChange = { estudiante = estudiante.copy(grado = it) },
            label = { Text("Grado") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            View.compartido.sendEstudiante(estudiante)
            keyboardController?.hide()
            navController.navigate("main_screen")
        }) {
            Text("Enviar")
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.m6skhz3ptb6oeynjnwmw_oikrde9jk21wltuy),
            contentDescription = "Imagen de estudiante"
        )
        Button(
            onClick = { navController.navigate("pantalla_anadir") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Añadir estudiante")
        }
        Button(
            onClick = { navController.navigate("pantalla_estudiantes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Todos los Estudiantes")
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UpdateScreen(navController: NavController, id: String) {
    val estudianteId = id.toInt()
    var estudiante by remember { mutableStateOf(Estudiante(0, "", "", "", "", "", "")) }

    LaunchedEffect(key1 = estudianteId) {
        try {
            estudiante = View.compartido.getEstudianteById(estudianteId)
        } catch (e: Exception) {
            Log.e("ErrorRespuesta", "Estudiante no encontrado", e)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Actualizar",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estudiante.curso.toString(),
            onValueChange = { estudiante = estudiante.copy(curso = it) },
            label = { Text("Curso") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estudiante.grado.toString(),
            onValueChange = { estudiante = estudiante.copy(grado = it) },
            label = { Text("Grado") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            try {
                View.compartido.actualizarEstudiante(estudianteId, estudiante)
                navController.navigate("principal")
            } catch (e: Exception) {
                Log.e("ErrorRespuesta", "Error al actualizar estudiante", e)
            }
        }) {
            Text("Enviar")
        }
    }
}

class View : ViewModel() {

    companion object {
        val compartido: View by lazy { View() }
    }

    fun sendEstudiante(estudiante: Estudiante) {
        viewModelScope.launch {
            try {
                EstudianteApi.retrofitService.createEstudiante(estudiante)
                Log.i("Respuesta", "El estudiante se ha creado")

            } catch (e: Exception) {
                Log.e("ErrorRespuesta", e.stackTraceToString())
                Log.i("Respuesta", "El estudiante NO se ha creado")
            }
        }
    }

    suspend fun getEstudiantes(): List<Estudiante> {
        return try {
            val estudiantes = EstudianteApi.retrofitService.getEstudiantes()
            Log.i("Respuesta", "Los estudiantes se han capturado: $estudiantes")
            estudiantes
        } catch (e: Exception) {
            Log.e("ErrorRespuesta", "Error al obtener estudiantes: ${e.message}")
            emptyList()
        }
    }

    fun deleteEstudiante(id: Int){
        viewModelScope.launch {
            try{
                EstudianteApi.retrofitService.deleteEstudiante(id)
                Log.i("Respuesta", "El estudiante se ha eliminado")
            }catch (e: Exception){
                Log.e("ErrorRespuesta", "El estudiante no se ha eliminado")
            }
        }
    }

    suspend fun getEstudianteById(id: Int): Estudiante{
        return try{
            val estudiante = EstudianteApi.retrofitService.getEstudianteById(id)
            Log.i("Respuesta", "El estudiante se ha capturado")
            estudiante
        } catch (e: Exception){
            Log.e("ErrorRespuesta", "El estudiante NO se ha capturado: ${e.message}")
            Estudiante(0, "", "", "", "", null, null)
        }
    }

    fun actualizarEstudiante(id: Int, estudiante: Estudiante) {
        viewModelScope.launch {
            try {
                EstudianteApi.retrofitService.updateEstudiante(id, estudiante)
                Log.i("Respuesta", "El estudiante se ha actualizado")

            } catch (e: Exception) {
                Log.e("ErrorRespuesta", "El estudiante NO se ha actualizado: ${e.message}")
            }
        }
    }
}