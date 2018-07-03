package persistence.db.data

import persistence.db.connection.{DBModule, H2DBModule}

import scala.concurrent.Future

trait FoodDAO extends FoodTable{ this:DBModule =>

  import profile.api._

  def create(foodItem: FoodItem):Future[Long] = db.run(foodTableAutoInc += foodItem)

  def update(foodItem: FoodItem):Future[Long] = db.run(foodTableQuery.filter(_.id === foodItem.id.get).update(foodItem)).mapTo[Long]

  def getById(id:Long):Future[Option[FoodItem]] = db.run(foodTableQuery.filter(_.id === id).result.headOption)

  def getAll(): Future[List[FoodItem]] = db.run(foodTableQuery.to[List].result)

  def delete(id: Long): Future[Long] = db.run(foodTableQuery.filter(_.id === id).delete).mapTo[Long]
}

private[data] trait FoodTable extends PersonTable { this:DBModule =>

  import profile.api._

  class FoodTable(tag:Tag) extends Table[FoodItem](tag,"food_item") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def personId = column[Long]("person")

    def name = column[String]("name")

    def * = (name, personId, id.?) <> (FoodItem.tupled, FoodItem.unapply)

    def person = foreignKey("person_fk", personId, personTableQuery)(_.id)
  }
  protected val foodTableQuery = TableQuery[FoodTable]

  protected def foodTableAutoInc = foodTableQuery returning foodTableQuery.map(_.id)

}
object FoodDAO extends FoodDAO with H2DBModule

final case class FoodItem(name:String, personId:Long, id:Option[Long] = None)
