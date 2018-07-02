import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import persistence.data.{FoodDAO, PersonDAO}

package object http {
  val routes =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    } ~
    path("customers"){
      get {
        complete(HttpEntity(ContentTypes.`application/json`, PersonDAO.nameList))
      }
    } ~
    path("menu") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, FoodDAO.menu))
      }
    }
}
