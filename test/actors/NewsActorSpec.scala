package actors

import java.util.NoSuchElementException

import akka.actor.ActorSystem
import akka.pattern._
import akka.testkit._
import configs.{AppConfig, News}
import controllers.Articles
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import play.api.libs.ws._
import play.api.test.Helpers._

import scala.language.reflectiveCalls
import scala.concurrent.Future

class NewsActorSpec extends PlaySpec with MockitoSugar {

  //TODO use scalatest pool instead
  import scala.concurrent.ExecutionContext.Implicits._
  import actors.ArticleJsonParser._

  implicit val actorSystem = ActorSystem("testing_actor_system")

  val wsClient = mock[WSClient]
  val wsResponse = mock[WSResponse]
  val wSRequest = mock[WSRequest]
  val appConfig = mock[AppConfig]

  def articleFixture = new {
    val article = Article("test article", "http://localhost:9000",
      "http://localhost:9000/image", "Valeryi", "10.1.1993", "some description")
  }

  when(appConfig.newsApiKey).thenReturn("test_news_key")

  val query = Seq("source" -> News.SOURCE, "apiKey" -> appConfig.newsApiKey)
  when(wsResponse.body).thenReturn(Json.toJson(Articles(Seq(articleFixture.article))).toString)
  when(wSRequest.withQueryString(query:_*)).thenReturn(wSRequest)
  when(wSRequest.get()).thenReturn(Future.successful(wsResponse))
  when(wsClient.url(News.ARTICLES)).thenReturn(wSRequest)

  val actorRef = TestActorRef(new NewsActor(wsClient, appConfig))

  s"news actor" must {
    "return sequence of articles" in {
      val futureArticles = (actorRef ? RequestNews).mapTo[Articles]
      val (article::Nil) = await(futureArticles).articles
      article mustBe articleFixture.article
    }

    "return failed future with IllegalArgumentException on unknown message" in {
      an[NoSuchElementException] mustBe thrownBy(await(actorRef ? ""))
    }
  }

}
