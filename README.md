# Gestión de Tienda en Consola - Kotlin Tour

Este proyecto es una aplicación interactiva por consola desarrollada en **Kotlin** como parte de la práctica introductoria de fundamentos del lenguaje basada en el [Kotlin Tour](https://kotlinlang.org/docs/kotlin-tour-welcome.html).

El sistema simula la gestión de inventario y registro de ventas de un minimarket sin utilizar Programación Orientada a Objetos (sin clases), priorizando el uso de tipos básicos, control de flujo y colecciones nativas.

---

## 🚀 Conceptos del Kotlin Tour implementados

1. **Salida por consola e interpolación de cadenas:**
   - Uso de `println()` y `print()`.
   - Inyección de variables directas (`$variable`) y expresiones evaluadas (`${expresion}`).

2. **Tipos de datos básicos y mutabilidad:**
   - Variables inmutables (`val`): constantes de configuración (`nombreTienda`, `igv`).
   - Variables mutables (`var`): contadores de estado y opciones de menú (`transaccionesRealizadas`, `opcion`).
   - Tipos explícitos e inferidos: `String`, `Int`, `Double` y `Boolean`.

3. **Manejo de nulos (*Null Safety*):**
   - Lectura segura con `readLine()?.trim().orEmpty()`.
   - Conversiones seguras y operador Elvis (`?:`) para valores por defecto: `toIntOrNull()`, `toDoubleOrNull()`.

4. **Colecciones:**
   - **`Set` (`setOf`):** Colección inmutable para almacenar categorías únicas de productos, evitando duplicados.
   - **`List` (`mutableListOf`):** Listas paralelas mutables para almacenar secuencialmente nombres, precios y categorías.
   - **`Map` (`mutableMapOf`):** Diccionario clave-valor para asociar cada nombre de producto con su stock disponible.

5. **Estructuras de control:**
   - Bucle `while` interactivo para mantener la ejecución del menú.
   - Bifurcaciones con `when` idiomático de Kotlin.
   - Recorrido de colecciones con bucles `for` y rangos (`0 until size`), así como destructuración de pares clave-valor (`for ((producto, stock) in map)`).

---

## 🛠️ Requisitos previos

- Tener instalado el compilador de Kotlin (`kotlinc`) o el **Java Development Kit (JDK 17+)**.
- Opcional: **IntelliJ IDEA** o **VS Code** con la extensión de Kotlin.

---

## 💻 Instrucciones de ejecución

### Opción 1: Desde la terminal con `kotlinc`

1. Compila el archivo fuente a un archivo `.jar`:
   ```bash
   kotlinc Main.kt -include-runtime -d TiendaKotlin.jar
   ```

2. Ejecuta el archivo generado con la máquina virtual de Java:
   ```bash
   java -jar TiendaKotlin.jar
   ```

### Opción 2: Ejecución directa como script

Si tienes `kotlinc` en tu ruta del sistema (`PATH`):
```bash
kotlinc -script Main.kt
```

### Opción 3: IntelliJ IDEA

1. Abre la carpeta del proyecto en IntelliJ IDEA.
2. Abre el archivo `Main.kt`.
3. Haz clic en el ícono verde de **Run** (▶) situado a la izquierda de la función `fun main()`.

---

## 📋 Demostración del menú

```text
======================================
   ¡Bienvenido a la Tienda Kotlin!   
======================================
Tienda: MiniMarket Express | Estado abierta: true

--- MENÚ DE GESTIÓN ---
1. Registrar nuevo producto
2. Listar productos y calcular total
3. Ver inventario de stock (Map) y categorías (Set)
4. Salir
Selecciona una opción: 
```

---

## 👤 Autor

- **Usuario:** poma2020diego
- **Curso:** Desarrollo Móvil / Fundamentos de Kotlin