import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import persistence.db.data.{FoodDAO, PersonDAO}

package object http {
  import JsonConverter._
  val routes =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    } ~
    path("customers"){
      get {
        complete(PersonDAO.getAll())
      }
    } ~
    path("menu") {
      get {
        complete(FoodDAO.getAll())
      }
    }
}
