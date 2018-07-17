package persistence

import slick.jdbc.JdbcProfile

trait DBModule {

  val profile:JdbcProfile

  import profile.api._

  val db:Database
}
