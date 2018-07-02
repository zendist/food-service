package persistence.data

import persistence.db.DBModule
import persistence.data.PersonDAO.people

import slick.jdbc.H2Profile
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

object FoodDAO extends DBModule {

  override val profile = H2Profile

  class FoodTable(tag:Tag) extends Table[FoodItem](tag,"food_item"){
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def personId = column[Long]("person")
    def name = column[String]("name")

    def * = (name,id).mapTo[FoodItem]

    def person = foreignKey("person_fk",personId, people)(_.id)
  }
  final case class FoodItem(name:String, id:Long = 0L)

  val foods = TableQuery[FoodTable]
  val menu = exec(foods.result).map(_.name).mkString(""," | ","")
}
