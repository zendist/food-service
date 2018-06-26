import slick.jdbc.H2Profile.api._

final class FoodTable(tag:Tag) extends Table[FoodItem](tag,"food_item"){
  def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
  def personId = column[Long]("person")
  def name = column[String]("name")

  def * = (name,id).mapTo[FoodItem]

//  def person = foreignKey("person_fk",personId)(_.id)
}
lazy val foodSelect = TableQuery[FoodTable]
final case class FoodItem(name:String, id:Long = 0L)