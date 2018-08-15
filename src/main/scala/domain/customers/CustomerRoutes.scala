package domain.customers

import akka.http.scaladsl.server.Directives._
import http.JsonConverter._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import domain.dto.CustomersList
import spray.json.DefaultJsonProtocol._

import scala.util.{Failure, Success}

object CustomerRoutes {

  private val getCustomers =
    get {
      onComplete(CustomerService.getList()) {
        case Success(customers) => complete(CustomersList(customers))
        case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred ${ex.getMessage}")
      }
    }
  private val postCustomer =
    post {
      entity(as[Customer]) { newCustomer =>
        onComplete(CustomerService.add(newCustomer)) {
          case Success(id) => complete(StatusCodes.Created)
          case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred ${ex.getMessage}")
        }
      }
    }
  private val putCustomer =
    put {
      entity(as[Customer]) { newCustomer =>
        onComplete(CustomerService.update(newCustomer)) {
          case Success(id) => complete(StatusCodes.Accepted)
          case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred ${ex.getMessage}")
        }
      }
    }

  val customerRoutes: Route =
    pathPrefix("api") {
      path("customers") {
        getCustomers ~
          postCustomer ~
          putCustomer ~
          pathPrefix(IntNumber) { id =>
            get {
              onComplete(CustomerService.getBy(id)) {
                case Success(custOpt) => custOpt match {
                  case Some(cust) => complete(cust)
                  case None => complete(StatusCodes.NotFound, "Customer doesn't exist")
                }
                case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred ${ex.getMessage}")
              }
            }
          }
      }
    }
}
