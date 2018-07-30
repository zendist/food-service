package http

import java.sql.Date

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import domain.customers.Customer
import domain.fooditems.FoodItem
import domain.orders.Order
import spray.json.{DefaultJsonProtocol, JsNumber, JsValue, JsonFormat, deserializationError}

object JsonConverter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val foodFormat = jsonFormat2(FoodItem)
  implicit val personFormat = jsonFormat3(Customer)
  implicit val orderFormat = jsonFormat4(Order)

  implicit object DateFormat extends JsonFormat[Date] {
    def write(date:Date) = JsNumber(date.getTime)
    def read(json:JsValue) = json match {
      case JsNumber(n) => new Date(n.longValue())
      case error => deserializationError(s"Expected JsNumber, got $error")
    }
  }
}
