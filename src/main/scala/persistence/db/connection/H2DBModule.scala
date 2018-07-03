package persistence.db.connection

trait H2DBModule extends DBModule {
  override val profile = slick.jdbc.H2Profile

  override val db = H2DB.connectionPool
}

private object H2DB {
  import slick.jdbc.H2Profile.api._

  val connectionPool = Database.forConfig("foodBase")
}
