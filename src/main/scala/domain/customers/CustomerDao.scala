package domain.customers

import persistence.{DBModule, PostgresDBModule}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

trait CustomerDao extends CustomerPersistence {
  this: DBModule =>

  def create(customer:Customer): Future[Int] = db.run(customerTableAutoInc += customer)

  def update(customer:Customer):Future[Int] = db.run(customerTableQuery.filter(_.id === customer.id).update(customer))

  def getById(id:Int):Future[Option[Customer]] = db.run(customerTableQuery.filter(_.id === id).result.headOption)

  def getAll():Future[List[Customer]] = db.run(customerTableQuery.to[List].result)

  def delete(id:Int):Future[Int] = db.run(customerTableQuery.filter(_.id === id).delete)

}

private[domain] trait CustomerPersistence {
  this: DBModule =>

  class CustomerTable(tag: Tag) extends Table[Customer](tag, "customer") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def balance = column[Int]("balance")

    def * = (name, balance, id) <> (Customer.tupled, Customer.unapply)
  }

  protected val customerTableQuery = TableQuery[CustomerTable]

  protected def customerTableAutoInc = customerTableQuery returning customerTableQuery.map(_.id)
}

object CustomerDao extends CustomerDao with PostgresDBModule //H2DBModule
final case class Customer(name: String, balance: Int, id: Int = 0)
