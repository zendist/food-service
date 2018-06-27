import slick.lifted.TableQuery

package object persistence {
  lazy val persons = TableQuery[PersonTable]
  lazy val foods = TableQuery[FoodTable]

  final case class Person(name: String, id: Long = 0L)
  final case class FoodItem(name:String, id:Long = 0L)
}
