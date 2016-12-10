package api

import configs._
import http.HttpHelper._
import http._
import play.api.libs.ws._
import play.api.test.Helpers._

class NewsApiSpec extends HttpSpec with BaseAppSpec {
  implicit val wsClient = instanceOf[WSClient]

  val query = Seq("source" -> News.SOURCE, "apiKey" -> appConfig.newsApiKey)
  val newsVendorResponse = sendRequest(News.ARTICLES, query:_*)

  s"${News.ARTICLES} with $query" must {
    testResponseFor(newsVendorResponse, OK, "application/json; charset=utf-8")
  }

  //TODO: invent better test case
  s"${NewsApiSpecProps.techNewAddress}" must {
    val techNewsResponse = sendFakeRequest(NewsApiSpecProps.techNewAddress)
    testResponseFor(techNewsResponse, OK, "application/json")
  }

}

object NewsApiSpecProps {
  val techNewAddress = controllers.routes.NewsApi.index().url
}