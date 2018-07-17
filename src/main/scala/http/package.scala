import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import domain.customers.CustomerDao
import domain.fooditems.FoodDao

package object http {
  import JsonConverter._
  val customerRoutes =
    path("customers"){
      get {
        complete(CustomerDao.getAll())
      }
    }
  val foodRoutes =
    path("menu") {
      get {
        complete(FoodDao.getAll())
      }
    }
  val routes =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    } ~
    foodRoutes ~
    customerRoutes
}
