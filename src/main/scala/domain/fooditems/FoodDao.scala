package domain.fooditems

import persistence.{DBModule, PostgresDBModule}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

trait FoodDao extends FoodPersistence{ this:DBModule =>

  def create(foodItem: FoodItem):Future[Long] = db.run(foodTableAutoInc += foodItem)

  def update(foodItem: FoodItem):Future[Long] = db.run(foodTableQuery.filter(_.id === foodItem.id.get).update(foodItem)).mapTo[Long]

  def getById(id:Long):Future[Option[FoodItem]] = db.run(foodTableQuery.filter(_.id === id).result.headOption)

  def getAll(): Future[List[FoodItem]] = db.run(foodTableQuery.to[List].result)

  def delete(id: Long): Future[Long] = db.run(foodTableQuery.filter(_.id === id).delete).mapTo[Long]
}

private[domain] trait FoodPersistence { this:DBModule =>

  private class FoodTable(tag:Tag) extends Table[FoodItem](tag,"food_item") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (name, id.?) <> (FoodItem.tupled, FoodItem.unapply)
  }
  protected val foodTableQuery = TableQuery[FoodTable]

  protected def foodTableAutoInc = foodTableQuery returning foodTableQuery.map(_.id)

}
object FoodDao extends FoodDao with PostgresDBModule //H2DBModule

final case class FoodItem(name:String, id:Option[Long] = None)
