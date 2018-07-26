package domain.orders

import java.sql.Date

import domain.customers.CustomerPersistence
import domain.fooditems.FoodPersistence
import persistence.{DBModule, PostgresDBModule}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

trait OrdersDao extends OrdersPersistence{ this:DBModule =>

  def create(order: Order):Future[Long] = db.run(orderTableQuery += order).mapTo[Long]

  def update(order: Order):Future[Long] = db.run(orderTableQuery.filter(o =>
    o.created === order.created &&
      o.foodItemId === order.foodItemId &&
      o.customerId === order.customerId)
    .update(order))
    .mapTo[Long]

  def findByDate(date:Date):Future[Option[Order]] = db.run(orderTableQuery.filter(_.created === date).result.headOption)

  def getAll(): Future[List[Order]] = db.run(orderTableQuery.to[List].result)

  def delete(order: Order): Future[Long] = db.run(orderTableQuery.filter(o =>
    o.customerId === order.customerId &&
      o.foodItemId === order.foodItemId &&
      o.created === order.created)
    .delete)
    .mapTo[Long]
}

private trait OrdersPersistence extends CustomerPersistence with FoodPersistence { this:DBModule =>

  private class OrderTable(tag:Tag) extends Table[Order](tag,"orders") {
    def sum = column[Double]("sum")

    def created = column[Date]("created")

    def customerId = column[Long]("customer_id")

    def foodItemId = column[Long]("food_item_id")

    def * = (sum, created, customerId, foodItemId) <> (Order.tupled, Order.unapply)

    def customer_fk = foreignKey("customer_fk", customerId, customerTableQuery)(_.id)

    def food_fk = foreignKey("food_fk", foodItemId, foodTableQuery)(_.id)

    def pk = primaryKey("order_pk", (created, customerId, foodItemId))
  }
  protected val orderTableQuery = TableQuery[OrderTable]

}
object OrdersDao extends OrdersDao with PostgresDBModule //H2DBModule

final case class Order(sum:Double, created: Date, customerId:Long, foodItemId:Long)
