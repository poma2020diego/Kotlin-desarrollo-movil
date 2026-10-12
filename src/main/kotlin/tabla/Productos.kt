package tabla

import org.jetbrains.exposed.sql.Table

object Productos : Table() {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 100)
    val precio = double("precio")
    val categoria = varchar("categoria", 50)
    val stock = integer("stock")
    override val primaryKey = PrimaryKey(id)
}
