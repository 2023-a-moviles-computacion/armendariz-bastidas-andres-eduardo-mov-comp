import java.io.File
import java.util.Date

data class Edificio(
    val identificadorEdificio: Int,
    val nombreEdificio: String,
    val aguaPotable: Boolean,
    val predial: Double,
)

data class Departamento(
    val identificadorDepartamento: Int,
    val identificadorEdificio: Int,
    val nombreInquilino: String,
    val arriendo: Double,
    val estacionamiento: Boolean,
    val amoblado: Boolean,
)

class Control_Edificios{
    //Crear fichero
    private val file = File("edificios.txt")
    init{
        if (!file.exists()) {
            file.createNewFile()
        }
    }
    //Funcion para crear un edificio
    fun crearEdificio(edificioEjemplo:Edificio){
        file.appendText("${edificioEjemplo.identificadorEdificio},${edificioEjemplo.nombreEdificio},${edificioEjemplo.aguaPotable},${edificioEjemplo.predial}\n")
    }

    fun leerEdificio(): List<Edificio> {
        return file.readLines().map { line ->
            val propiedades = line.split(",")
            Edificio(
                propiedades[0].toInt(),
                propiedades[1],
                propiedades[2].toBoolean(),
                propiedades[3].toDouble()
            )
        }
    }
    fun actualizarEdificio(edificioEjemplo: Edificio){
        var lines = file.readLines()
        file.writeText("")
        lines.forEach{ line ->
            val propiedades = line.split(",")
            if(propiedades[0].toInt()==edificioEjemplo.identificadorEdificio){
                file.appendText("${edificioEjemplo.identificadorEdificio},${edificioEjemplo.nombreEdificio},${edificioEjemplo.aguaPotable},${edificioEjemplo.predial}")
            }else{
                file.appendText("$line\n")
            }
        }
    }
    fun eliminarEdificio(identificador: Int){
        val lines = file.readLines()
        file.writeText("")
        lines.forEach { line ->
            val propiedades = line.split(",")
            if(propiedades[0].toInt() != identificador){
                file.appendText("$line\n")
            }
        }
    }
}

class Control_Departamento{
    //Crear fichero
    private val file = File("departamentos.txt")
    init{
        if (!file.exists()) {
            file.createNewFile()
        }
    }
    //Funcion para crear un departamento
    fun crearDepartamento(departamentoEjemplo:Departamento){
        file.appendText("${departamentoEjemplo.identificadorDepartamento},${departamentoEjemplo.identificadorEdificio},${departamentoEjemplo.nombreInquilino},${departamentoEjemplo.arriendo}," +
                "${departamentoEjemplo.estacionamiento},${departamentoEjemplo.amoblado}\n")
    }

    fun leerDepartamento(): List<Departamento> {
        return file.readLines().map { line ->
            val propiedades = line.split(",")
            Departamento(
                propiedades[0].toInt(),
                propiedades[1].toInt(),
                propiedades[2],
                propiedades[3].toDouble(),
                propiedades[4].toBoolean(),
                propiedades[5].toBoolean()
            )
        }
    }
    fun actualizardepartamento(departamentoEjemplo: Departamento){
        var lines = file.readLines()
        file.writeText("")
        lines.forEach{ line ->
            val propiedades = line.split(",")
            if(propiedades[0].toInt()==departamentoEjemplo.identificadorDepartamento){
                file.appendText("${departamentoEjemplo.identificadorDepartamento},${departamentoEjemplo.identificadorEdificio},${departamentoEjemplo.nombreInquilino},${departamentoEjemplo.arriendo}," +
                        "${departamentoEjemplo.estacionamiento},${departamentoEjemplo.amoblado}")
            }else{
                file.appendText("$line\n")
            }
        }
    }
    fun eliminarDepartamento(identificador: Int){
        val lines = file.readLines()
        file.writeText("")
        lines.forEach { line ->
            val propiedades = line.split(",")
            if(propiedades[0].toInt() != identificador){
                file.appendText("$line\n")
            }
        }
    }
}

fun String.toBooleanOrNull(): Boolean? {
    return when (this.toLowerCase()) {
        "true" -> true
        "false" -> false
        else -> null
    }
}

fun main(){
    val controlEdificio = Control_Edificios()
    val controlDepartamento = Control_Departamento()

    //Menu
    while(true){
        println("--- Seleccione una de las siguientes opciones ---")
        println("1. Edificio")
        println("2. Departamento")
        println("3. Salir")

        when (readLine()?.toIntOrNull()){
            1 -> {
                while(true){
                    println("--- Operaciones CRUD de Edificios ---")
                    println("1. Crear edificio")
                    println("2. Leer edificio")
                    println("3. Actualizar edificio")
                    println("4. Eliminar edificio")
                    println("5. Salir")

                    when (readln()?.toIntOrNull()){
                        1 -> {
                            println("- Ingrese los datos del Edificio - ")
                            print("ID: ")
                            val id = readLine()?.toIntOrNull()?:continue
                            print("Nombre del edificio: ")
                            val nombre = readLine()?:continue
                            print("¿El edificio cuenta con agua potable?: ")
                            val agua = readLine()?.toBooleanOrNull()?:continue
                            print("Valor del predio: ")
                            val predio = readLine()?.toDoubleOrNull()?:continue
                            val edificio = Edificio(id,nombre, agua, predio)
                            controlEdificio.crearEdificio(edificio)
                            println("Edificio creado satisfactoriamente")
                            println()
                        }
                        2 -> {
                            val edificios = controlEdificio.leerEdificio()
                            if(edificios.isNotEmpty()){
                                println("- Lista de edificios -")
                                edificios.forEach { edificio ->
                                    println("ID: ${edificio.identificadorEdificio}")
                                    println("Nombre edificio: ${edificio.nombreEdificio}")
                                    println("¿Cuenta con agua potable?: ${edificio.aguaPotable}")
                                    println("Valor del predio: ${edificio.predial}")
                                    println("-----------------------------------------")
                                    println()
                                }
                            }else{
                                println("No hay edificios que mostrar")
                                println()
                            }
                        }
                        3 -> {
                            println("- Ingrese el ID del Edificio -")
                            val id = readLine()?.toIntOrNull()?:continue
                            val edificios = controlEdificio.leerEdificio()
                            val edificio = edificios.find { it.identificadorEdificio == id }
                            if(edificio != null){
                                println("Ingrese los nuevos valores del edificio: ")
                                println("Nombre actual del edificio: ${edificio.nombreEdificio}")
                                val nombre = readLine()?:continue
                                println("¿El edificio cuenta con agua potable?: ${edificio.aguaPotable} ")
                                val agua = readLine()?.toBooleanOrNull()?:continue
                                println("Valor actual del predio: ${edificio.predial}")
                                val predio = readLine()?.toDoubleOrNull()?:continue
                                val nuevoEdificio = Edificio(id, nombre, agua, predio)
                                controlEdificio.actualizarEdificio(nuevoEdificio)
                                println("Edificio Actualizado")
                                println()
                            }else{
                                println("No existe registro del edificio con el ID proporcionado")
                            }
                        }
                        4 -> {
                            println("- Ingrese el ID del edificio a ser eliminado -")
                            val id = readLine()?.toIntOrNull()?:continue
                            controlEdificio.eliminarEdificio(id)
                            println("Edificio eliminado satisfactoriamente")
                        }
                        5 -> {
                            println("Regresando al menu pirncipal")
                            break
                        }
                        else -> {
                            println("Opción ingresada no es aceptable, vuelva a intentar")
                        }
                    }
                }
            }
            2 -> {
                while(true){
                    println("--- Operaciones CRUD de Departamentos ---")
                    println("1. Crear departamento")
                    println("2. Leer departamento")
                    println("3. Actualizar departamento")
                    println("4. Eliminar departamento")
                    println("5. Salir")

                    when (readln()?.toIntOrNull()){
                        1 -> {
                            println("- Ingrese los datos del departamento -")
                            print("ID del departamento: ")
                            val id = readLine()?.toIntOrNull()?:continue
                            print("ID del edificio: ")
                            val idEdificio = readLine()?.toIntOrNull()?:continue
                            print("Nombre del Inquilino: ")
                            val nombre = readLine()?:continue
                            print("Valor del arriendo: ")
                            val arriendo = readLine()?.toDoubleOrNull()?:continue
                            print("¿Tiene estacionamiento?: ")
                            val estacionamiento = readLine()?.toBooleanOrNull()?:continue
                            print("¿Es amoblado?: ")
                            val amoblado = readLine()?.toBooleanOrNull()?:continue
                            val departamento = Departamento(id,idEdificio,nombre,arriendo,estacionamiento, amoblado)
                            controlDepartamento.crearDepartamento(departamento)
                            println("Departamento creado satisfactoriamente")
                            println()
                        }
                        2 -> {
                            val departamentos = controlDepartamento.leerDepartamento()
                            if(departamentos.isNotEmpty()){
                                println("- Lista de departamentos -")
                                departamentos.forEach { departamento ->
                                    println("ID: ${departamento.identificadorDepartamento}")
                                    println("ID del edificio: ${departamento.identificadorEdificio}")
                                    println("Nombre del inquilino: ${departamento.nombreInquilino}")
                                    println("Valor del arriendo: ${departamento.arriendo}")
                                    println("¿Tiene estacionamiento?: ${departamento.estacionamiento}")
                                    println("¿Es amoblado?: ${departamento.amoblado}")
                                    println("-----------------------------------------")
                                    println()
                                }
                            }else{
                                println("No hay departamentos que mostrar")
                                println()
                            }
                        }
                        3 -> {
                            println("- Ingrese el ID del departamento -")
                            val id = readLine()?.toIntOrNull()?:continue
                            val departamentos = controlDepartamento.leerDepartamento()
                            val departamento = departamentos.find { it.identificadorDepartamento == id }
                            if(departamento != null){
                                println("Ingrese los nuevos valores del departamento: ")
                                println("ID actual del edificio: ${departamento.identificadorEdificio}")
                                val idEdificio = readLine()?.toIntOrNull()?:continue
                                println("Nombre actual del Inquilino: ${departamento.nombreInquilino}")
                                val nombre = readLine()?:continue
                                println("Valor actual del arriendo: ${departamento.arriendo}")
                                val arriendo = readLine()?.toDoubleOrNull()?:continue
                                println("¿Tiene estacionamiento?: ${departamento.estacionamiento}")
                                val estacionamiento = readLine()?.toBooleanOrNull()?:continue
                                println("¿Es amoblado?: ${departamento.amoblado}")
                                val amoblado = readLine()?.toBooleanOrNull()?:continue
                                val nuevoDepartamento = Departamento(id, idEdificio, nombre, arriendo,estacionamiento, amoblado)
                                controlDepartamento.actualizardepartamento(nuevoDepartamento)
                                println("Departamento Actulizado")
                                println()
                            }else{
                                println("No existe registro del departamento con el ID proporcionado")
                            }
                        }
                        4 -> {
                            println("- Ingrese el ID del departamento a ser eliminado -")
                            val id = readLine()?.toIntOrNull()?:continue
                            controlDepartamento.eliminarDepartamento(id)
                            println("Departamento eliminado satisfactoriamente")
                        }
                        5 -> {
                            println("Regresando al menu pirncipal")
                            break
                        }
                        else -> {
                            println("Opción ingresada no es aceptable, vuelva a intentar")
                        }
                    }
                }
            }
            3 -> {
                println("Saliendo del programa")
                return
            }
            else -> {
                println("Opción ingresada no es aceptable, vuelva a intentar")
            }
        }
        println()
    }
}