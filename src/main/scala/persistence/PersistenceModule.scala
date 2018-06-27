import persistence._
import slick.jdbc.JdbcProfile

import scala.concurrent.duration._
import scala.concurrent.Await

trait PersistenceModule {

  val profile:JdbcProfile

  import profile.api._

  val testFoods = Seq(
    FoodItem("Omurise"),
    FoodItem("Hamburger"),
    FoodItem("Nuggets")
  )

  val db = Database.forConfig("foodBase")

  def exec[T](program:DBIO[T]):T = Await.result(db.run(program), 2 seconds)

  exec(foods.schema.create >>
    (foods ++= testFoods)
  )

  val menu = exec(foods.result).map(_.name).mkString(""," | ","")
}