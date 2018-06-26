import Entities.FoodItem
import slick.jdbc.H2Profile.api._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await

object Persistence {

  val testFoods = Seq(
    FoodItem("Omurise"),
    FoodItem("Hamburger"),
    FoodItem("Nuggets")
  )

  lazy val foodSelect = TableQuery[FoodTable]
  val db = Database.forConfig("foodBase")

  def exec[T](program:DBIO[T]):T = Await.result(db.run(program), 2 seconds)

  exec(foodSelect.schema.create >>
    (foodSelect ++= testFoods) >>
    (foodSelect += FoodItem("Spaghetti"))
  )

  val menu = exec(foodSelect.result).map(_.name).mkString(""," | ","")
}
