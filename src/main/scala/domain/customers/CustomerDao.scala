package domain.customers

import persistence.{DBModule, PostgresDBModule}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

trait CustomerDao extends CustomerPersistence {
  this: DBModule =>

  def create(person: Customer): Future[Long] = db.run(personTableAutoInc += person)

  def update(person: Customer) = db.run(personTableQuery.filter(_.id === person.id.get).update(person)).mapTo[Long]

  def getById(id: Long) = db.run(personTableQuery.filter(_.id === id).result.headOption)

  def getAll() = db.run(personTableQuery.to[List].result)

  def delete(id: Long) = db.run(personTableQuery.filter(_.id === id).delete).mapTo[Long]

}

private[data] trait CustomerPersistence {
  this: DBModule =>

  class PersonTable(tag: Tag) extends Table[Customer](tag, "customer") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def balance = column[Double]("balance")

    def * = (name, balance, id.?) <> (Customer.tupled, Customer.unapply)
  }

  protected val personTableQuery = TableQuery[PersonTable]

  protected def personTableAutoInc = personTableQuery returning personTableQuery.map(_.id)
}

object CustomerDao extends CustomerDao with PostgresDBModule //H2DBModule
final case class Customer(name: String, balance: Double, id: Option[Long] = None)
