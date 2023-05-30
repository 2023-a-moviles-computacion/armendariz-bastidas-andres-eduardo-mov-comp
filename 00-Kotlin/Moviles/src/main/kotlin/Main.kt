import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")

    //Tipos de variantes

    //INMUTABLES: (no se registran "=");
    val inmutable: String = "Andres";
    //inmutable = "Armendariz";

    //MUTABLES (Re asignar)
    var mutable: String = "Armendariz"
    mutable = "Andres"

    //Siempre preferir usar val antes que var
    // Val para algo fijo
    //Var para algo que cambia

    val ejemploVariable = "Andres Armendariz"
    val edadEjemplo: Int = 12

    //Varaibles primitivas
    val nombreProfesor: String = "Andres Armendariz"
    val sueldo: Double  = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clases java
    val fechaNacimiento: Date = Date()

    //SWITCH NO EXISTE
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val coqueteo = if(estadoCivilWhen =="S") "Si" else "No"


    calcularSueldo(10.00)
    calcularSueldo(10.00, 12.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) //Named parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa=14.00) //Parametros nombrados


    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //ARREGLOS
    //Tipos de Arreglos
    //ARREGLO ESTATICO
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    //ARREGLO DINAMICO
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //OPERADORES -> Sirven para los arreglos estativos y dinamicos

    //FOR EACH -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach{valorActual:Int ->
        println("Valor Actual: ${valorActual}")
    }

    //otra forma
    arregloDinamico.forEach(println(it))//it significa el elemnto del arreglo

    //-----------------------------------------------------------------------
    arregloEstatico
        .forEachIndexed { indice:Int, valorActual:Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)


    //Map -> Muta el arreglo (Cambia el arreglo)
    // 1) enviamos el nuevo valor de la iteracion
    // 2) nos devuelve es un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map {valorActual:Int ->
            return@map valorActual.toDouble() + 100.00
        }

    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map{it +15}

    //Filter -> Filtrar el arreglo
    // 1) Devolver una expresion (TRUE o FAlSE)
    // 2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
    .filter{valorActual:Int->
        val mayoresACinco: Boolean = valorActual>5 //Expression Condicion
        return@filter mayoresACinco
    }

    //OR AND
    // OR -> any (Alguno cumple?)
    // AND -> all (Todos cumplen?)
    println("OR and AND")
    println("OR")
    val respuestaAny: Boolean = arregloDinamico
        .any{valorActual:Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) //true

    println("AND")
    val respuestaAll:Boolean = arregloDinamico
        .all { valorActual:Int ->
            return@all (valorActual>5)
        }
    println(respuestaAll)//False
}



//FUNTION
//Void == unit
fun imprimirNombre(nombre: String): Unit{
    println("Nombre: ${nombre}")
}
fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null, //Opcion null => nulleable
): Double{
    //Int -> Int?
    //String -> String?
    //Date = Date?
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    } else {
        return sueldo * (100/tasa)+bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos:Int
    ){//Bloque de codido del
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")}
}

abstract class Numeros(//Constructor Primario
    //EJEMPLO
    //uno: Int, (Parametros (sin modificador de acceso)
    //private var uno: Int, //Propiedad Publica de la clase (numeros.uno)
    //var uno: Int, //Propiedad de la clase - Por defecto es Public
    protected val numeroUno: Int, //Propiedad de la clase protected - numeros.numeroUno
    protected val numeroDos: Int //Propiedad de la clase protected - numeros.numeroDos
){
    //var cedula:string = "(public is por defecto)"
    //private valorCalculado: Init = 0 (private)
    init{
        this.numeroUno; this.numeroDos; //this es opcional
        numeroUno; numeroDos; //sin el this
        println("Inicializando")
    }
}

class Suma( //Constructor Primario Suma
    uno: Int, //Parametro
    dos:Int //Parametro
): Numeros(uno, dos){//<-Constructor del Padre
init {//Bloque constructor primario
    this.numeroUno; numeroUno
    this.numeroDos; numeroDos
}

    constructor(//Segundo Constructor
        uno: Int?, //parametro
        dos: Int //parametro
    ):this(//LLamada constructor primario
        if(uno == null) 0 else uno,
        dos
    ){ //Si necesitamos bloqiue de codigo lo usamos
        numeroUno;
    }

    constructor(//tercer constructor
        uno: Int, //parametro
        dos: Int? //parametr0
    ): this ( //LLamada constructor primario
        uno,
        if(dos == null ) 0 else uno
    ) // Si no lo necesitamos al bloque de codigo "{}" lo omitimos

    constructor(//cuarto constructor
        uno: Int?, //parametro
        dos: Int? //parametr0
    ): this ( //LLamada constructor primario
        if(uno == null ) 0 else uno,
        if(dos == null ) 0 else uno
    )
    public fun sumar(): Int{ //public por defecto, a usar private o protected
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{ // Atributos y Metodos compartidos
        //entre las instancias
        val pi =  3.14
        fun elevarAlCuadrado(num: Int): Int{
            return num*num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }


}

