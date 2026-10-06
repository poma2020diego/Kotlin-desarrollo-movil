fun main() {
    val nombreTienda: String = "MiniMarket Express"
    val igv: Double = 0.18
    var transaccionesRealizadas: Int = 0
    val tiendaAbierta: Boolean = true

    println("Tienda: $nombreTienda | Estado abierta: $tiendaAbierta")

    val categoriasValidas: Set<String> = setOf("Abarrotes", "Bebidas", "Limpieza")

    val nombresProductos = mutableListOf<String>()
    val preciosProductos = mutableListOf<Double>()
    val categoriasProductos = mutableListOf<String>()

    val stockProductos = mutableMapOf<String, Int>()

    var opcion: Int? = 0

    while (opcion != 4) {
        println("\n--- MENÚ DE GESTIÓN ---")
        println("1. Registrar nuevo producto")
        println("2. Listar productos y calcular total")
        println("3. Ver inventario de stock y categorías")
        println("4. Salir")
        print("Selecciona una opción: ")

        val entrada = readLine()
        opcion = entrada?.toIntOrNull()

        when (opcion) {
            1 -> {
                println("\n[Registrar Producto]")
                print("Nombre del producto: ")
                val nombre: String = readLine()?.trim().orEmpty()

                print("Precio: ")
                val precio: Double = readLine()?.toDoubleOrNull() ?: 0.0

                println("Categorías disponibles: $categoriasValidas")
                print("Categoría: ")
                val categoria: String = readLine()?.trim().orEmpty()

                print("Cantidad en stock: ")
                val cantidad: Int = readLine()?.toIntOrNull() ?: 1

                nombresProductos.add(nombre)
                preciosProductos.add(precio)
                categoriasProductos.add(categoria)
                stockProductos[nombre] = cantidad

                transaccionesRealizadas++
                println("-> Producto '$nombre' registrado con éxito.")
            }
            2 -> {
                println("\n[Lista de Productos Registrados]")
                if (nombresProductos.isEmpty()) {
                    println("No hay productos registrados aún.")
                } else {
                    var sumaPrecios = 0.0

                    for (i in 0 until nombresProductos.size) {
                        val nom = nombresProductos[i]
                        val pre = preciosProductos[i]
                        val cat = categoriasProductos[i]
                        sumaPrecios += pre

                        println("${i + 1}. $nom | Categoría: $cat | Precio: S/. $pre")
                    }

                    val totalConImpuesto = sumaPrecios * (1.0 + igv)
                    println("--------------------------------------")
                    println("Subtotal: S/. $sumaPrecios")
                    println("Total estimado (+ IGV ${igv * 100}%): S/. $totalConImpuesto")
                }
            }
            3 -> {
                println("\n[Resumen de Inventario y Colecciones]")
                println("Categorías válidas del sistema: $categoriasValidas")

                if (stockProductos.isEmpty()) {
                    println("El mapa de stock está vacío.")
                } else {
                    println("Productos registrados [Producto -> Stock]:")
                    for ((producto, stock) in stockProductos) {
                        println(" - $producto: $stock unidades")
                    }
                }
            }
            4 -> {
                println("\nGracias por usar el sistema de $nombreTienda.")
                println("Operaciones registradas en la sesión: $transaccionesRealizadas")
            }
            else -> {
                println("Opción no válida. Intenta de nuevo.")
            }
        }
    }
}