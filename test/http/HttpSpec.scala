package http

import org.scalatestplus.play.PlaySpec
import play.api.libs.ws.WSResponse

trait HttpSpec extends PlaySpec{
  def testForStatusCode(statusCode: Int, expectedStatusCode: Int) =
    s"have status code $expectedStatusCode" in {
      statusCode mustBe expectedStatusCode
    }

  def testForContentType(contentType: Option[String], expectedContentType: String) =
    s"have content-type $expectedContentType" in {
      contentType mustBe Some(expectedContentType)
    }

  def testForBody(body: String, nonEmpty: Boolean = true) =
    s"have ${if (nonEmpty) "non empty" else "empty"} body" in {
      body.nonEmpty mustBe nonEmpty
    }

  def testResponseFor(response: WSResponse, statusCode: Int, contentType: String) = {
    testForStatusCode(response.status, statusCode)
    testForContentType(response.header("content-type"), contentType)
    testForBody(response.body)
  }

  def testResponseFor(httpResponse: HttpResponse, statusCode: Int, contentType: String) = {
    testForStatusCode(httpResponse.statusCode, statusCode)
    testForContentType(httpResponse.contentType, contentType)
    testForBody(httpResponse.bodyAsString)
  }
}
