package domain.orders

import java.sql.Timestamp

import domain.customers.CustomerPersistence
import domain.fooditems.FoodPersistence
import persistence.{DBModule, PostgresDBModule}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

trait OrdersDao extends OrdersPersistence {
  this: DBModule =>

  def create(order: Order): Future[Long] = db.run(orderTableQuery += order)

  def update(order: Order): Future[Long] = db.run(orderTableQuery.filter(_.id === order.id).update(order)).mapTo[Long]

  def findById(id: Long): Future[Option[Order]] = db.run(orderTableQuery.filter(_.id === id).result.headOption)

  def getAll(): Future[List[Order]] = db.run(orderTableQuery.to[List].result)

  def delete(id: Long): Future[Long] = db.run(orderTableQuery.filter(_.id === id).delete).mapTo[Long]
}

trait OrdersPersistence extends CustomerPersistence with FoodPersistence {
  this: DBModule =>

  private class OrderTable(tag: Tag) extends Table[Order](tag, "orders") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def sum = column[Double]("sum")

    def created = column[Timestamp]("created")

    def customerId = column[Long]("customer_id")

    def * = (sum, created, customerId, id) <> (Order.tupled, Order.unapply)

    def customerFk = foreignKey("customer_fk",customerId,customerTableQuery)(_.id)
  }

  private class FoodOrderTable(tag: Tag) extends Table[FoodOrder](tag, "food_order") {
    def foodItemId = column[Long]("food_item_id")

    def orderId = column[Long]("order_id")

    def * = (foodItemId, orderId) <> (FoodOrder.tupled, FoodOrder.unapply)

    def foodItemFk = foreignKey("food_item_fk", foodItemId, foodTableQuery)(_.id)

    def orderFk = foreignKey("order_fk", orderId, orderTableQuery)(_.id)
  }

  protected val orderTableQuery = TableQuery[FoodOrderTable]

  protected def orderTableAutoInc = orderTableQuery returning orderTableQuery.map(_.id)

}

final case class FoodOrder(orderId:Long, foodItemId: Long)

object OrdersDao extends OrdersDao with PostgresDBModule //H2DBModule

final case class Order(sum: Double, created: Timestamp, customerId: Long, id: Long = 0)
