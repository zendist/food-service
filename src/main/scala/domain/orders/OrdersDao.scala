package domain.orders

import java.sql.Date

import domain.customers.CustomerPersistence
import domain.fooditems.FoodPersistence
import persistence.{DBModule, PostgresDBModule}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object OrdersDao extends OrdersPersistence with PostgresDBModule {

  def create(order: Order): Future[Int] = db.run(orderTableQuery += order)

  def update(order: Order): Future[Int] = db.run(orderTableQuery.filter(o =>
    o.created === order.created &&
      o.foodItemId === order.foodItemId &&
      o.customerId === order.customerId)
    .update(order))

  def findByDate(date: Date): Future[Option[Order]] = db.run(orderTableQuery.filter(_.created === date).result.headOption)

  def getAll(): Future[List[Order]] = db.run(orderTableQuery.to[List].result)

  def delete(order: Order): Future[Int] = db.run(orderTableQuery.filter(o =>
    o.customerId === order.customerId &&
      o.foodItemId === order.foodItemId &&
      o.created === order.created)
    .delete)
}

trait OrdersPersistence extends CustomerPersistence with FoodPersistence {
  this: DBModule =>

  class OrderTable(tag: Tag) extends Table[Order](tag, "orders") {

    def created = column[Date]("created")

    def foodItemQty = column[Int]("food_item_qty")

    def customerId = column[Int]("customer_id")

    def foodItemId = column[Int]("food_item_id")

    def * = (foodItemQty, created, customerId, foodItemId) <> (Order.tupled, Order.unapply)

    def customer_fk = foreignKey("customer_fk", customerId, customerTableQuery)(_.id)

    def food_fk = foreignKey("food_fk", foodItemId, foodTableQuery)(_.id)

    def pk = primaryKey("order_pk", (created, customerId, foodItemId))
  }

  val orderTableQuery = TableQuery[OrderTable]

}

final case class Order(foodItemQty: Int, created: Date, customerId: Int, foodItemId: Int)
