package api

import actors.NewsActor
import akka.actor.Actor
import akka.testkit.TestActorRef
import akka.util.Timeout
import bootstrap.ActorSpec
import configs._
import controllers.NewsApi
import http.HttpHelper._
import http._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import play.api.test.FakeRequest
import play.api.test.Helpers._
import scala.concurrent.duration._
import scala.language.postfixOps
import repos.NewsRepo

import scala.concurrent.ExecutionContext
import scala.util.Failure

class NewsApiSpec extends HttpSpec with BaseAppSpec {

  val techNewAddress = controllers.routes.NewsApi.index().url

  val query = Seq("source" -> News.source, "apiKey" -> appConfig.newsApiKey)
  val newsVendorResponse = sendRequest(News.articles, query:_*)

  s"${News.articles} with $query" must {
    testResponseFor(newsVendorResponse, OK, "application/json; charset=utf-8")
  }

  s"$techNewAddress" must {
    val techNewsResponse = sendFakeRequest(techNewAddress)
    testResponseFor(techNewsResponse, OK, "application/json")
  }

}

class NewsApiThrowable extends ActorSpec with MockitoSugar{
  implicit val ec = ExecutionContext.Implicits.global

  val timeoutRepo = mock[NewsRepo]
  when(timeoutRepo.articles()).thenThrow(new RuntimeException("any exception"))

  val actorRef = TestActorRef(new IllegalThrowingActor())
  val timeoutActorRef = TestActorRef(new NewsActor(timeoutRepo))

  val appConfig = mock[AppConfig]
  val testTimeoutInMillis = 10
  when(appConfig.askGlobalTimeout).thenReturn(Timeout(testTimeoutInMillis milliseconds))

  val newsApi = new NewsApi(actorRef, appConfig)
  val timeoutNewsApi = new NewsApi(timeoutActorRef, appConfig)

  s"NewsApi#index" must {
    "return 500 because of IlligalArgumentException" in {
      status(newsApi.index(FakeRequest())) mustBe INTERNAL_SERVER_ERROR
    }

    "return 500 because of AskTimeoutException" in {
      status(timeoutNewsApi.index(FakeRequest())) mustBe INTERNAL_SERVER_ERROR
    }
  }

}

class IllegalThrowingActor extends Actor {
  override def receive: Receive = {
    case _ =>
      sender() ! Failure(new IllegalArgumentException("test"))
  }
}