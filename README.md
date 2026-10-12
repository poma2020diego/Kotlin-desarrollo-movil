# SIGEVE - Sistema de Gestion de Ventas

Aplicacion de consola en **Kotlin** que gestiona productos de un minimarket con persistencia en **SQLite** usando el ORM **Exposed** de JetBrains.

---

## Conceptos implementados

### Semana 1-3: Fundamentos de Kotlin
- Tipos de datos, mutabilidad (`val`/`var`), null safety
- Colecciones: `Set`, `List`, `Map`
- Control de flujo: `while`, `when`, `for`
- Interpolacion de cadenas y entrada por consola

### Semana 4: Base de Datos con SQLite + ORM
1. **Conexion a base de datos SQLite** via JDBC (`org.xerial:sqlite-jdbc`)
2. **ORM Exposed** (`org.jetbrains.exposed`):
   - Definicion de tablas como objetos singleton (`object Productos : Table()`)
   - Operaciones CRUD completas dentro de bloques `transaction { }`
3. **Operadores de consulta ORM**:
   - `eq` (igual), `neq` (distinto), `greater` (mayor que)
   - `like` (contiene texto), `inList` (dentro de una lista)
4. **Ordenamiento y limites**:
   - `orderBy` con `SortOrder.ASC` / `SortOrder.DESC`
   - `limit` para restringir resultados
5. **Persistencia**: los datos sobreviven entre ejecuciones del programa

---

## Estructura del proyecto

```
sigeve/
├── build.gradle.kts          # Configuracion Gradle con dependencias
├── settings.gradle.kts       # Configuracion del proyecto
├── gradle.properties         # Configuracion JVM para Gradle
├── gradlew / gradlew.bat     # Gradle wrapper
├── data/                     # Base de datos SQLite (gitignored)
│   └── sigeve.db
└── src/main/kotlin/
    ├── Main.kt               # Programa principal con menu CRUD
    └── tabla/
        └── Productos.kt      # Tabla ORM (objeto singleton)
```

---

## Dependencias

| Libreria | Version | Proposito |
|----------|---------|-----------|
| `sqlite-jdbc` | 3.49.1.0 | Driver JDBC para SQLite |
| `exposed-core` | 0.47.0 | ORM Exposed - nucleo |
| `exposed-dao` | 0.47.0 | ORM Exposed - DAO |
| `exposed-jdbc` | 0.47.0 | ORM Exposed - integracion JDBC |
| `slf4j-simple` | 2.0.16 | Logging requerido por Exposed |

---

## Requisitos previos

- **JDK 21** o superior
- **IntelliJ IDEA** (recomendado) o terminal con Gradle

---

## Instrucciones de ejecucion

### Opcion 1: IntelliJ IDEA (recomendado)

1. Abrir la carpeta del proyecto en IntelliJ IDEA
2. IntelliJ detectara el proyecto Gradle y sincronizara automaticamente
3. Ejecutar `Main.kt` con el boton Run

### Opcion 2: Terminal con Gradle

```bash
./gradlew run --console=plain -q
```

En Windows:
```bash
gradlew.bat run --console=plain -q
```

> **Nota sobre SSL**: Si Gradle no puede descargar dependencias por errores de certificados, asegurate de que `gradle.properties` contenga:
> ```
> org.gradle.jvmargs=-Djavax.net.ssl.trustStoreType=WINDOWS-ROOT
> ```

---

## Menu del sistema

```
==============================================
   Bienvenido a MiniMarket Express - SIGEVE
   Sistema con Base de Datos SQLite + ORM
==============================================

--- MENU DE GESTION ---
1. Registrar nuevo producto          (INSERT)
2. Listar productos y calcular total (SELECT ALL)
3. Ver inventario de stock           (SELECT + GROUP BY)
4. Buscar productos                  (SELECT WHERE con operadores)
5. Actualizar producto               (UPDATE)
6. Eliminar producto                 (DELETE)
7. Consultas ordenadas               (ORDER BY, LIMIT)
8. Salir
```

---

## Exploracion de la base de datos

Para inspeccionar los datos guardados, descarga [DB Browser for SQLite](https://sqlitebrowser.org/dl/) y abre el archivo `data/sigeve.db`.

---

## Autor

- **Usuario:** poma2020diego
- **Curso:** Desarrollo de Sistemas Moviles / UNMSM
