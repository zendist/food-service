package http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import persistence.db.data.{FoodItem, Person}
import spray.json.DefaultJsonProtocol

object JsonConverter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val foodFormat = jsonFormat3(FoodItem)
  implicit val personFormat = jsonFormat2(Person)
}
