package http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import domain.customers.Customer
import domain.fooditems.FoodItem
import domain.orders.Order
import spray.json.DefaultJsonProtocol

object JsonConverter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val foodFormat = jsonFormat2(FoodItem)
  implicit val personFormat = jsonFormat3(Customer)
  implicit val orderFormat = jsonFormat4(Order)
}
