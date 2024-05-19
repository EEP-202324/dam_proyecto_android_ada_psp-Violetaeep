Mi aplicación esta pensada para un profesor o persona que este ahí en aula y vengan aulos a preguntar por los cursos.Los datos de esos aulumnos son introduccidos en mi aplicacion donde se quedaran sus datos registrados en la base de datos.
En Java lo que he echo es :
Crear 4 cñases y una interfaz, las clases son:
-EstudianteJava, en la cual he creado los atributos que nosotros vamos a utilizar en nuestra base de datos y los getter y setter.
-EstudianteApplication,que es donde esta la función mai.
-EstudianteRepository, esta es una interfaz que hereda de JpaRepository.
-EstudianteService, Aqui se encuentran todas las funciones que van con la base de datos.
-EstudianteControler, En esta clase lo que hago es definir las rutas.He creado varios métodos.
He creado nueve test.
En Android la primera pantalla que he creado consta de : un vector y dos botones u que son : añadir estudiante y todos los estudiantes. al apresionar sobre añadir estudiantes te sale otra pantalla que consta de un cuestionario en el cual rellenas los datos y los mandas, donde llegan y quedan grabados en mi base de datos.
Al presionar sobre el botón todos los estudiantes te lleva a otra pantalla donde aparecen todos los estudiantes que tengo en mí base de datos.En cada estudiante hay tres botones que son : borrar que cuando le presionas te borar el estudiante , ver mas  que cuando le presionas te sale toda la información de ese estudiante y actualizar que cuando le presiona te sale curso y grado los cuales los puedes cambiar y un botón con enviar que al presionarlo envias la informión cambiada.
