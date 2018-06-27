import persistence.Person
import slick.jdbc.H2Profile.api._

final class PersonTable(tag:Tag) extends Table[Person](tag,"person"){
  def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")

  def * = (name, id).mapTo[Person]
}
