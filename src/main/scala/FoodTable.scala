import Entities.FoodItem
import slick.jdbc.H2Profile.api._

final class FoodTable(tag:Tag) extends Table[FoodItem](tag,"food_item"){
  def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")

  def * = (name,id).mapTo[FoodItem]
}
