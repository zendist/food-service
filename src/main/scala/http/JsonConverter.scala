package http

import java.sql.Date

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import domain.customers.Customer
import domain.dto.{CustomersList, FoodItemsList, OrdersList}
import domain.fooditems.FoodItem
import domain.orders.Order
import spray.json.{DefaultJsonProtocol, JsNumber, JsValue, JsonFormat, deserializationError}

object JsonConverter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val foodFormat = jsonFormat2(FoodItem)
  implicit val personFormat = jsonFormat3(Customer)
  implicit val orderFormat = jsonFormat4(Order)
  implicit val foodListFormat = jsonFormat1(FoodItemsList)
  implicit val ordersListFormat = jsonFormat1(OrdersList)
  implicit val customersListFormat = jsonFormat1(CustomersList)

  implicit object DateFormat extends JsonFormat[Date] {
    def write(date:Date) = JsNumber(date.getTime)
    def read(json:JsValue) = json match {
      case JsNumber(n) => new Date(n.longValue())
      case error => deserializationError(s"Expected JsNumber, got $error")
    }
  }
}
