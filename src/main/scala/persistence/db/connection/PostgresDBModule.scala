package persistence.db.connection

trait PostgresDBModule extends DBModule {
  override val profile = slick.jdbc.PostgresProfile

  override val db = PostgresDB.connectionPool
}
private object PostgresDB {
  import slick.jdbc.PostgresProfile.api._

  val connectionPool = Database.forConfig("postgresFoodBase")
}
