import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}

import http.routes

object MainController extends App {
  implicit val system = ActorSystem("food-service-api")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val conf = ConfigFactory.load()

  val bindingFuture = Http().bindAndHandle(routes, "0.0.0.0", conf.getInt("http.port"))
}
