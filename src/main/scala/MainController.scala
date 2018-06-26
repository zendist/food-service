import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn

object MainController extends App {
  implicit val system = ActorSystem("food-service-api")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val routes =
    path("persons") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    } ~
    path("menu") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, Persistence.menu))
      }
    }

  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)

  println(s"Server online")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
