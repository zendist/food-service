package persistence.db.data

import persistence.db.connection.{DBModule, H2DBModule}
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

trait PersonDAO extends PersonTable { this:DBModule =>

  import profile.api._

  def create(person: Person): Future[Long] = db.run(personTableAutoInc += person)

  def update(person:Person) = db.run(personTableQuery.filter(_.id === person.id.get).update(person)).mapTo[Long]

  def getById(id:Long) = db.run(personTableQuery.filter(_.id === id).result.headOption)

  def getAll() = db.run(personTableQuery.to[List].result)

  def delete(id:Long) = db.run(personTableQuery.filter(_.id === id).delete).mapTo[Long]

}
private [data] trait PersonTable { this:DBModule =>

  import profile.api._

  class PersonTable(tag:Tag) extends Table[Person](tag,"person"){
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")

    def * = (name, id.?) <> (Person.tupled, Person.unapply)
  }
  protected val personTableQuery = TableQuery[PersonTable]
  protected def personTableAutoInc = personTableQuery returning personTableQuery.map(_.id)
}
object PersonDAO extends PersonDAO with H2DBModule
final case class Person(name: String, id:Option[Long] = None)
