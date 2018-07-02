package persistence.data

import persistence.db.DBModule

import slick.jdbc.H2Profile
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

object PersonDAO extends DBModule{

  override val profile = H2Profile

  class PersonTable(tag:Tag) extends Table[Person](tag,"person"){
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")

    def * = (name, id).mapTo[Person]
  }
  final case class Person(name: String, id: Long = 0L)

  val people = TableQuery[PersonTable]
  val nameList = exec(people.result).map(_.name).mkString(""," | ","")
}
