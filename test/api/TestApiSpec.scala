package api

import play.api.test.Helpers._
import org.scalatestplus.play._
import http.HttpHelper._
import play.api.libs.ws.WSClient

class TestApiSpec extends PlaySpec with OneAppPerSuite {

  implicit val wsClient = app.injector.instanceOf[WSClient]

  "TestApi" must {
    "respond with OK and 'test' word" in {
      val testResponse = sendFakeRequest("/test")
      testResponse.statusCode mustBe OK
      testResponse.bodyAsString mustBe "test"
      testResponse.contentType mustBe Some("text/plain")
    }
  }
}
