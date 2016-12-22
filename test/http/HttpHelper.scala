package http

import play.api._
import play.api.mvc._
import play.api.test._
import play.api.libs.ws._
import play.api.libs.json._
import play.api.test.Helpers._

import scala.concurrent.Future

object HttpHelper {

  def sendRequest(address: String, query: (String, String)*)
                 (implicit wsClient: WSClient): WSResponse =
    await(wsClient.url(address).withQueryString(query: _*).get())

  def sendFakeRequest(address: String, query: (String, String)*)
                     (implicit app: Application, wsClient: WSClient): HttpResponse =
    response(route(app, FakeRequest("GET", address).withFormUrlEncodedBody(query: _*)))

  def withUrl(url: String): Request[AnyContentAsEmpty.type] = FakeRequest("GET", url)

  def response(mayBeFutureResult: Option[Future[Result]]): HttpResponse =
    mayBeFutureResult match {
      case Some(futureResult) => HttpResponse(status(futureResult), contentAsString(futureResult),
        headers(futureResult), contentType(futureResult))
      case None => throw new RuntimeException("test failed")
    }
}

case class HttpResponse(statusCode: Int, bodyAsString: String, headers: Map[String, String],
                        contentType: Option[String]) {
  def jsonBody = Json.toJson(bodyAsString)
}