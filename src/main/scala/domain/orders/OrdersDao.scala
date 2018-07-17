package domain.orders

import java.util.Date

import persistence.{DBModule, PostgresDBModule}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

trait OrdersDao extends OrdersPersistence{ this:DBModule =>

  def create(order: Order):Future[Long] = db.run(orderTableAutoInc += order)

  def update(order: Order):Future[Long] = db.run(orderTableQuery.filter(_.id === order.id.get).update(order)).mapTo[Long]

  def findById(id:Long):Future[Option[Order]] = db.run(orderTableQuery.filter(_.id === id).result.headOption)

  def getAll(): Future[List[Order]] = db.run(orderTableQuery.to[List].result)

  def delete(id: Long): Future[Long] = db.run(orderTableQuery.filter(_.id === id).delete).mapTo[Long]
}

private[data] trait OrdersPersistence{ this:DBModule =>

  private class OrderTable(tag:Tag) extends Table[Order](tag,"orders") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def sum = column[Double]("sum")

//    def created = column[Date]("created")

    def * = (sum, id.?) <> (Order.tupled, Order.unapply)
  }
  protected val orderTableQuery = TableQuery[OrderTable]

  protected def orderTableAutoInc = orderTableQuery returning orderTableQuery.map(_.id)

}
object OrdersDao extends OrdersDao with PostgresDBModule //H2DBModule

final case class Order(sum:Double, id:Option[Long] = None)
