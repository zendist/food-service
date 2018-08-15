package domain.fooditems

import persistence.{DBModule, PostgresDBModule}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

object FoodDao extends FoodPersistence with PostgresDBModule {

  def create(foodItem: FoodItem):Future[Int] = db.run(foodTableAutoInc += foodItem)

  def update(foodItem: FoodItem):Future[Int] = db.run(foodTableQuery.filter(_.id === foodItem.id).update(foodItem))

  def getById(id:Int):Future[Option[FoodItem]] = db.run(foodTableQuery.filter(_.id === id).result.headOption)

  def getAll():Future[List[FoodItem]] = db.run(foodTableQuery.to[List].result)

  def delete(id: Int):Future[Int] = db.run(foodTableQuery.filter(_.id === id).delete)
}

trait FoodPersistence { this:DBModule =>

  class FoodTable(tag:Tag) extends Table[FoodItem](tag,"food_items") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def price = column[Int]("price")

    def name = column[String]("name")

    def * = (name, price, id) <> (FoodItem.tupled, FoodItem.unapply)
  }
  val foodTableQuery = TableQuery[FoodTable]

  def foodTableAutoInc = foodTableQuery returning foodTableQuery.map(_.id)

}

final case class FoodItem(name:String, price:Int, id:Int = 0)
