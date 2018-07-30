package domain.customers

import akka.http.scaladsl.server.Directives._
import http.JsonConverter._

import spray.json.DefaultJsonProtocol._

class CustomerRoutes(custService:CustomerService){

  val customerRoutes =
    pathPrefix("api") {
      path("customers"){
        get {
          complete{
            custService.getList()
          }
        }
        ~
        post {
          entity(as[Customer]) { newCustomer =>
            complete {
              custService.add(newCustomer)
            }
          }
        }
        ~
        put {
          entity(as[Customer]) { newCustomer =>
            complete {
              custService.update(newCustomer)
            }
          }
        }
      }
      ~
      pathPrefix(IntNumber) { id =>
        get {
          complete{
            custService.getBy(id)
          }
        }
      }
    }
}
