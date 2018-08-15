import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import domain.customers.CustomerRoutes
import domain.dto.{FoodItemsList, OrdersList}
import domain.fooditems.FoodDao
import domain.orders.OrdersDao

import scala.util.{Failure, Success}
import http.JsonConverter._

package object http {
  private val foodRoutes =
    path("menu") {
      get {
        onComplete(FoodDao.getAll()){
          case Success(foodList) => complete(FoodItemsList(foodList))
          case Failure(ex) => complete(StatusCodes.InternalServerError, s"Error fetching food list -> ${ex.getMessage}")
        }
      }
    }
  private val orderRoutes =
    path("order") {
      get {
        onComplete(OrdersDao.getAll()){
          case Success(orderList) => complete(OrdersList(orderList))
          case Failure(ex) => complete(StatusCodes.InternalServerError, s"Error fetching food list -> ${ex.getMessage}")
        }
      }
    }

  val routes =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    } ~
    foodRoutes ~
    CustomerRoutes.customerRoutes ~
    orderRoutes
}
