import org.sqlite.SQLiteDataSource
import java.sql.Connection
import java.sql.DriverManager


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

class Control_Edificios {
    private val dataSource: SQLiteDataSource

    init {
        dataSource = SQLiteDataSource()
        dataSource.url = "jdbc:sqlite:edificios.db"
        createEdificioTable()
    }

    private fun createEdificioTable() {
        dataSource.connection.use { connection ->
            val statement = connection.createStatement()
            val createTableSQL = """
                CREATE TABLE IF NOT EXISTS edificios (
                    identificadorEdificio INTEGER PRIMARY KEY,
                    nombreEdificio TEXT,
                    aguaPotable BOOLEAN,
                    predial DOUBLE
                );
            """
            statement.executeUpdate(createTableSQL)
        }
    }

    fun existsEdificio(id: Int): Boolean {
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT COUNT(*) FROM edificios WHERE identificadorEdificio = ?"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                return resultSet.getInt(1) > 0
            }
        }
    }

    fun create(edificio: Edificio) {
        if (existsEdificio(edificio.identificadorEdificio)) {
            println("El identificador de edificio esta en uso")
            return
        }
        dataSource.connection.use { connection ->
            val insertSQL = """
                INSERT INTO edificios(identificadorEdificio, nombreEdificio, aguaPotable, predial)
                VALUES (?, ?, ?, ?);
            """
            connection.prepareStatement(insertSQL).use { preparedStatement ->
                preparedStatement.setInt(1, edificio.identificadorEdificio)
                preparedStatement.setString(2, edificio.nombreEdificio)
                preparedStatement.setBoolean(3, edificio.aguaPotable)
                preparedStatement.setDouble(4, edificio.predial)
                preparedStatement.executeUpdate()
            }
        }
        println("Panadería creada correctamente.")
    }

    fun readAll(): List<Edificio> {
        val edificios = mutableListOf<Edificio>()
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT * FROM edificios"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    edificios.add(
                        Edificio(
                            resultSet.getInt("identificadorEdificio"),
                            resultSet.getString("nombreEdificio"),
                            resultSet.getBoolean("aguaPotable"),
                            resultSet.getDouble("predial")
                        )
                    )
                }
            }
        }
        return edificios
    }

    fun update(edificio: Edificio) {
        dataSource.connection.use { connection ->
            val updateSQL = """
                UPDATE edificios
                SET nombreEdificio = ?, aguaPotable = ?, predial = ?
                WHERE identificadorEdificio = ?;
            """
            connection.prepareStatement(updateSQL).use { preparedStatement ->
                preparedStatement.setString(1, edificio.nombreEdificio)
                preparedStatement.setBoolean(2, edificio.aguaPotable)
                preparedStatement.setDouble(3, edificio.predial)
                preparedStatement.setInt(4, edificio.identificadorEdificio)
                preparedStatement.executeUpdate()
            }
        }
        println("Los datos del edificio han sido actualizados exitosamente")
    }

    fun delete(id: Int) {
        dataSource.connection.use { connection ->
            val deleteSQL = "DELETE FROM edificios WHERE identificadorEdificio = ?"
            connection.prepareStatement(deleteSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.executeUpdate()
            }
        }
        println("Edificio eliminado.")
    }
}

class Control_Departamento {
    private val dataSource: SQLiteDataSource

    init {
        dataSource = SQLiteDataSource()
        dataSource.url = "jdbc:sqlite:departamentos.db"
        createDepartamentoTable()
    }

    private fun createDepartamentoTable() {
        dataSource.connection.use { connection ->
            val statement = connection.createStatement()
            val createTableSQL = """
                CREATE TABLE IF NOT EXISTS departamentos (
                    identificadorDepartamento INTEGER PRIMARY KEY,
                    identificadorEdificio INTEGER,
                    nombreInquilino TEXT,
                    arriendo DOUBLE,
                    estacionamiento BOOLEAN,
                    amoblado BOOLEAN
                );
            """
            statement.executeUpdate(createTableSQL)
        }
    }

    fun existsDepartamento(id: Int): Boolean {
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT COUNT(*) FROM departamentos WHERE identificadorDepartamento = ?"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                return resultSet.getInt(1) > 0
            }
        }
    }

    fun existsEdificio(id: Int, controlEdificios: Control_Edificios): Boolean {
        return controlEdificios.existsEdificio(id)
    }

    fun create(departamento: Departamento, controlEdificios: Control_Edificios) {
        if (!existsEdificio(departamento.identificadorDepartamento, controlEdificios)) {
            println("No existe el departamento con ese ID en el edificio proporcionado")
            return
        }
        if (existsDepartamento(departamento.identificadorDepartamento)) {
            println("Ya existe un departamento con el mismo ID")
            return
        }
        dataSource.connection.use { connection ->
            val insertSQL = """
                INSERT INTO departamentos(identificadorDepartamento, identificadorEdificio, nombreInquilino, arriendo, estacionamiento, amoblado)
                VALUES (?, ?, ?, ?, ?, ?);
            """
            connection.prepareStatement(insertSQL).use { preparedStatement ->
                preparedStatement.setInt(1, departamento.identificadorDepartamento)
                preparedStatement.setInt(2, departamento.identificadorEdificio)
                preparedStatement.setString(3, departamento.nombreInquilino)
                preparedStatement.setDouble(4, departamento.arriendo)
                preparedStatement.setBoolean(5, departamento.estacionamiento)
                preparedStatement.setBoolean(6, departamento.amoblado)
                preparedStatement.executeUpdate()
            }
        }
        println("Departamento creado ")
    }

    fun readAll(): List<Departamento> {
        val departamentos = mutableListOf<Departamento>()
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT * FROM departamentos"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    departamentos.add(
                        Departamento(
                            resultSet.getInt("identificadorDepartamento"),
                            resultSet.getInt("identificadorEdificio"),
                            resultSet.getString("nombreInquilino"),
                            resultSet.getDouble("arriendo"),
                            resultSet.getBoolean("estacionamiento"),
                            resultSet.getBoolean("amoblado")
                        )
                    )
                }
            }
        }
        return departamentos
    }

    fun update(departamento: Departamento) {
        dataSource.connection.use { connection ->
            val updateSQL = """
                UPDATE departamentos
                SET identificadorEdificio = ?, nombreInquilino = ?, arriendo = ?, estacionamiento = ?, amoblado = ?
                WHERE identificadorDepartamento = ?;
            """
            connection.prepareStatement(updateSQL).use { preparedStatement ->
                preparedStatement.setInt(1, departamento.identificadorDepartamento)
                preparedStatement.setString(2, departamento.nombreInquilino)
                preparedStatement.setDouble(3, departamento.arriendo)
                preparedStatement.setBoolean(4, departamento.estacionamiento)
                preparedStatement.setBoolean(5, departamento.amoblado)
                preparedStatement.setInt(6, departamento.identificadorEdificio)
                preparedStatement.executeUpdate()
            }
        }
        println("Departamento Actualizado.")
    }

    fun delete(id: Int) {
        dataSource.connection.use { connection ->
            val deleteSQL = "DELETE FROM departamentos WHERE identificadorDepartamento = ?"
            connection.prepareStatement(deleteSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.executeUpdate()
            }
        }
        println("Departamento Eliminado")
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
                            controlEdificio.create(edificio)
                            println("Edificio creado satisfactoriamente")
                            println()
                        }
                        2 -> {
                            val edificios = controlEdificio.readAll()
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
                            val edificios = controlEdificio.readAll()
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
                                controlEdificio.update(nuevoEdificio)
                                println("Edificio Actualizado")
                                println()
                            }else{
                                println("No existe registro del edificio con el ID proporcionado")
                            }
                        }
                        4 -> {
                            println("- Ingrese el ID del edificio a ser eliminado -")
                            val id = readLine()?.toIntOrNull()?:continue
                            controlEdificio.delete(id)
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
                            controlDepartamento.create(departamento, controlEdificio)
                            println("Departamento creado satisfactoriamente")
                            println()
                        }
                        2 -> {
                            val departamentos = controlDepartamento.readAll()
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
                            val departamentos = controlDepartamento.readAll()
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
                                controlDepartamento.update(nuevoDepartamento)
                                println("Departamento Actulizado")
                                println()
                            }else{
                                println("No existe registro del departamento con el ID proporcionado")
                            }
                        }
                        4 -> {
                            println("- Ingrese el ID del departamento a ser eliminado -")
                            val id = readLine()?.toIntOrNull()?:continue
                            controlDepartamento.delete(id)
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
