package persistence.db

import persistence.data.FoodDAO.{FoodItem, foods}
import slick.jdbc.JdbcProfile

import scala.concurrent.Await
import scala.concurrent.duration._

trait DBModule {

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
}
