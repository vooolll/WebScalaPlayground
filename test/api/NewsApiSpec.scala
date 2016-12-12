package api

import actors.RequestNews
import akka.actor.{ActorRef, ActorSystem}
import akka.pattern._
import akka.testkit.TestProbe
import configs._
import http.HttpHelper._
import http._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.inject.bind
import org.mockito.Mockito._
import play.api.libs.ws.WSClient
import play.api.test.Helpers._
import repos.NewsRepo

import scala.concurrent.{ExecutionContext, Future}

class NewsApiSpec extends HttpSpec with BaseAppSpec {

  val query = Seq("source" -> News.SOURCE, "apiKey" -> appConfig.newsApiKey)
  val newsVendorResponse = sendRequest(News.ARTICLES, query:_*)

  s"${News.ARTICLES} with $query" must {
    testResponseFor(newsVendorResponse, OK, "application/json; charset=utf-8")
  }

  s"${NewsApiSpecProps.techNewAddress}" must {
    val techNewsResponse = sendFakeRequest(NewsApiSpecProps.techNewAddress)
    testResponseFor(techNewsResponse, OK, "application/json")
  }

}

class NewsApiThrowable extends PlaySpec with HttpSpec with OneAppPerSuite with MockitoSugar {
  implicit val ac = ActorSystem("testing_actor_system")
  val mockActorRef = spy(TestProbe().ref)

  override implicit lazy val app = new GuiceApplicationBuilder()
    .overrides(bind[ActorRef].toInstance(mockActorRef)).build()

  implicit val ws = app.injector.instanceOf[WSClient]
  implicit val ec = app.injector.instanceOf[ExecutionContext]

  s"${NewsApiSpecProps.techNewAddress}" must {
    val techNewsResponse = sendFakeRequest(NewsApiSpecProps.techNewAddress)
    testResponseFor(techNewsResponse, INTERNAL_SERVER_ERROR, "application/json")
  }
}

class NewsApiAckTimeout extends PlaySpec with HttpSpec with OneAppPerSuite with MockitoSugar {

  val newsRepo = mock[NewsRepo]

  when(newsRepo.getArticles()).thenReturn(Future.failed(new IllegalArgumentException("")))

  override implicit lazy val app = new GuiceApplicationBuilder()
    .overrides(bind[NewsRepo].toInstance(newsRepo)).build()

  implicit val ws = app.injector.instanceOf[WSClient]
  implicit val ec = app.injector.instanceOf[ExecutionContext]

  s"${NewsApiSpecProps.techNewAddress}" must {
    val techNewsResponse = sendFakeRequest(NewsApiSpecProps.techNewAddress)
    testResponseFor(techNewsResponse, INTERNAL_SERVER_ERROR, "application/json")
  }
}

object NewsApiSpecProps {
  val techNewAddress = controllers.routes.NewsApi.index().url
}