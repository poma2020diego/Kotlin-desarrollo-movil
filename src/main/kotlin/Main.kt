import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import tabla.Productos

fun main() {
    val nombreTienda = "MiniMarket Express"
    val igv = 0.18
    var transaccionesRealizadas = 0

    val categoriasValidas: Set<String> = setOf("Abarrotes", "Bebidas", "Limpieza")

    Database.connect("jdbc:sqlite:data/sigeve.db", driver = "org.sqlite.JDBC")

    transaction {
        SchemaUtils.create(Productos)
    }

    println("==============================================")
    println("   Bienvenido a $nombreTienda - SIGEVE")
    println("   Sistema con Base de Datos SQLite + ORM")
    println("==============================================")

    var opcion: Int? = 0

    while (opcion != 8) {
        println("\n--- MENU DE GESTION ---")
        println("1. Registrar nuevo producto")
        println("2. Listar productos y calcular total")
        println("3. Ver inventario de stock y categorias")
        println("4. Buscar productos (operadores ORM)")
        println("5. Actualizar producto")
        println("6. Eliminar producto")
        println("7. Consultas ordenadas")
        println("8. Salir")
        print("Selecciona una opcion: ")

        val entrada = readLine()
        opcion = entrada?.toIntOrNull()

        when (opcion) {
            1 -> {
                println("\n[Registrar Producto]")
                print("Nombre del producto: ")
                val nombre = readLine()?.trim().orEmpty()

                print("Precio: ")
                val precio = readLine()?.toDoubleOrNull() ?: 0.0

                println("Categorias disponibles: $categoriasValidas")
                print("Categoria: ")
                val categoria = readLine()?.trim().orEmpty()

                print("Cantidad en stock: ")
                val stock = readLine()?.toIntOrNull() ?: 1

                transaction {
                    Productos.insert {
                        it[Productos.nombre] = nombre
                        it[Productos.precio] = precio
                        it[Productos.categoria] = categoria
                        it[Productos.stock] = stock
                    }
                }

                transaccionesRealizadas++
                println("-> Producto '$nombre' registrado con exito en la base de datos.")
            }

            2 -> {
                println("\n[Lista de Productos Registrados]")
                transaction {
                    val productos = Productos.selectAll().toList()
                    if (productos.isEmpty()) {
                        println("No hay productos registrados aun.")
                    } else {
                        var sumaPrecios = 0.0

                        for (row in productos) {
                            val id = row[Productos.id]
                            val nom = row[Productos.nombre]
                            val pre = row[Productos.precio]
                            val cat = row[Productos.categoria]
                            val stk = row[Productos.stock]
                            sumaPrecios += pre

                            println("$id. $nom | Categoria: $cat | Precio: S/. $pre | Stock: $stk")
                        }

                        val totalConImpuesto = sumaPrecios * (1.0 + igv)
                        println("--------------------------------------")
                        println("Subtotal: S/. $sumaPrecios")
                        println("Total estimado (+ IGV ${igv * 100}%): S/. ${"%.2f".format(totalConImpuesto)}")
                    }
                }
            }

            3 -> {
                println("\n[Resumen de Inventario y Categorias]")
                println("Categorias validas del sistema: $categoriasValidas")
                transaction {
                    val productos = Productos.selectAll().toList()
                    if (productos.isEmpty()) {
                        println("No hay productos en la base de datos.")
                    } else {
                        println("\nProductos registrados [Producto -> Stock]:")
                        for (row in productos) {
                            println(" - ${row[Productos.nombre]}: ${row[Productos.stock]} unidades")
                        }

                        println("\nProductos por categoria:")
                        val porCategoria = productos.groupBy { it[Productos.categoria] }
                        for ((cat, prods) in porCategoria) {
                            println(" [$cat]")
                            for (p in prods) {
                                println("   - ${p[Productos.nombre]} (S/. ${p[Productos.precio]})")
                            }
                        }
                    }
                }
            }

            4 -> {
                println("\n[Buscar Productos - Operadores ORM]")
                println("1. Buscar por ID (eq)")
                println("2. Buscar por nombre distinto de (neq)")
                println("3. Buscar por precio mayor a (greater)")
                println("4. Buscar por nombre que contenga (like)")
                println("5. Buscar por IDs especificos (inList)")
                print("Selecciona tipo de busqueda: ")

                val tipoBusqueda = readLine()?.toIntOrNull()

                transaction {
                    when (tipoBusqueda) {
                        1 -> {
                            print("Ingresa el ID: ")
                            val idBuscar = readLine()?.toIntOrNull() ?: 0
                            Productos.selectAll().where { Productos.id eq idBuscar }.forEach {
                                println("Resultado: ${it[Productos.id]} - ${it[Productos.nombre]} | S/. ${it[Productos.precio]}")
                            }
                        }
                        2 -> {
                            print("Nombre a excluir: ")
                            val nombreExcluir = readLine()?.trim().orEmpty()
                            Productos.selectAll().where { Productos.nombre neq nombreExcluir }.forEach {
                                println("Resultado: ${it[Productos.id]} - ${it[Productos.nombre]}")
                            }
                        }
                        3 -> {
                            print("Precio mayor a: ")
                            val precioMinimo = readLine()?.toDoubleOrNull() ?: 0.0
                            Productos.selectAll().where { Productos.precio greater precioMinimo }.forEach {
                                println("Resultado: ${it[Productos.id]} - ${it[Productos.nombre]} | S/. ${it[Productos.precio]}")
                            }
                        }
                        4 -> {
                            print("Texto a buscar en nombre: ")
                            val texto = readLine()?.trim().orEmpty()
                            Productos.selectAll().where { Productos.nombre like "%$texto%" }.forEach {
                                println("Resultado: ${it[Productos.id]} - ${it[Productos.nombre]}")
                            }
                        }
                        5 -> {
                            print("Ingresa IDs separados por coma (ej: 1,2,3): ")
                            val ids = readLine()?.split(",")?.mapNotNull { it.trim().toIntOrNull() } ?: emptyList()
                            Productos.selectAll().where { Productos.id inList ids }.forEach {
                                println("Resultado: ${it[Productos.id]} - ${it[Productos.nombre]} | S/. ${it[Productos.precio]}")
                            }
                        }
                        else -> println("Tipo de busqueda no valido.")
                    }
                }
            }

            5 -> {
                println("\n[Actualizar Producto]")
                transaction {
                    Productos.selectAll().forEach {
                        println("${it[Productos.id]}. ${it[Productos.nombre]} | S/. ${it[Productos.precio]} | Stock: ${it[Productos.stock]}")
                    }
                }

                print("Ingresa el ID del producto a actualizar: ")
                val idActualizar = readLine()?.toIntOrNull() ?: 0

                print("Nuevo nombre (dejar vacio para no cambiar): ")
                val nuevoNombre = readLine()?.trim().orEmpty()

                print("Nuevo precio (dejar vacio para no cambiar): ")
                val nuevoPrecio = readLine()?.toDoubleOrNull()

                print("Nueva categoria (dejar vacio para no cambiar): ")
                val nuevaCategoria = readLine()?.trim().orEmpty()

                print("Nuevo stock (dejar vacio para no cambiar): ")
                val nuevoStock = readLine()?.toIntOrNull()

                transaction {
                    val actualizado = Productos.update({ Productos.id eq idActualizar }) {
                        if (nuevoNombre.isNotEmpty()) it[nombre] = nuevoNombre
                        if (nuevoPrecio != null) it[precio] = nuevoPrecio
                        if (nuevaCategoria.isNotEmpty()) it[categoria] = nuevaCategoria
                        if (nuevoStock != null) it[stock] = nuevoStock
                    }
                    if (actualizado > 0) {
                        println("-> Producto actualizado con exito.")
                    } else {
                        println("-> No se encontro producto con ID $idActualizar.")
                    }
                }
                transaccionesRealizadas++
            }

            6 -> {
                println("\n[Eliminar Producto]")
                transaction {
                    Productos.selectAll().forEach {
                        println("${it[Productos.id]}. ${it[Productos.nombre]}")
                    }
                }

                print("Ingresa el ID del producto a eliminar: ")
                val idEliminar = readLine()?.toIntOrNull() ?: 0

                print("Confirmar eliminacion? (s/n): ")
                val confirmar = readLine()?.trim()?.lowercase()

                if (confirmar == "s") {
                    transaction {
                        val eliminado = Productos.deleteWhere { Productos.id eq idEliminar }
                        if (eliminado > 0) {
                            println("-> Producto eliminado con exito.")
                        } else {
                            println("-> No se encontro producto con ID $idEliminar.")
                        }
                    }
                    transaccionesRealizadas++
                } else {
                    println("-> Eliminacion cancelada.")
                }
            }

            7 -> {
                println("\n[Consultas Ordenadas]")
                println("1. Ordenar por nombre ASC")
                println("2. Ordenar por precio DESC (top 5)")
                println("3. Productos con stock > N ordenados por nombre")
                print("Selecciona consulta: ")

                val tipoOrden = readLine()?.toIntOrNull()

                transaction {
                    when (tipoOrden) {
                        1 -> {
                            println("\nProductos ordenados por nombre ASC:")
                            Productos.selectAll()
                                .orderBy(Productos.nombre to SortOrder.ASC)
                                .forEach {
                                    println("${it[Productos.id]} - ${it[Productos.nombre]} | S/. ${it[Productos.precio]}")
                                }
                        }
                        2 -> {
                            println("\nTop 5 productos por precio DESC:")
                            Productos.selectAll()
                                .orderBy(Productos.precio to SortOrder.DESC)
                                .limit(5)
                                .forEach {
                                    println("${it[Productos.id]} - ${it[Productos.nombre]} | S/. ${it[Productos.precio]}")
                                }
                        }
                        3 -> {
                            print("Stock mayor a: ")
                            val stockMinimo = readLine()?.toIntOrNull() ?: 0
                            println("\nProductos con stock > $stockMinimo, ordenados por nombre DESC:")
                            Productos.selectAll()
                                .where { Productos.stock greater stockMinimo }
                                .orderBy(Productos.nombre to SortOrder.DESC)
                                .forEach {
                                    println("${it[Productos.id]} - ${it[Productos.nombre]} | Stock: ${it[Productos.stock]}")
                                }
                        }
                        else -> println("Opcion no valida.")
                    }
                }
            }

            8 -> {
                println("\nGracias por usar el sistema de $nombreTienda.")
                println("Operaciones registradas en la sesion: $transaccionesRealizadas")
                println("Los datos han sido guardados en la base de datos SQLite.")
            }

            else -> {
                println("Opcion no valida. Intenta de nuevo.")
            }
        }
    }
}
