package actors

import akka.pattern._
import akka.testkit._
import bootstrap.AsyncActorSpec
import controllers.Articles
import org.mockito.Mockito._
import play.api.test.Helpers._
import repos.NewsRepo

import scala.concurrent.Future
import scala.language.reflectiveCalls

class NewsActorSpec extends AsyncActorSpec {

  def articleFixture = new {
    val articles = Articles(Seq(Article("test article", "http://localhost:9000",
      "http://localhost:9000/image", "Valeryi", "10.1.1993", "some description")))
  }

  val repo = mock[NewsRepo]

  when(repo.articles()).thenReturn(Future.successful(articleFixture.articles))

  val actorRef = TestActorRef(new NewsActor(repo))

  s"NewsActor#ack(RequestNews)" must {
    "return sequence of articles" in {
      val futureArticles = (actorRef ? RequestNews).mapTo[Articles]
      futureArticles.map(articles => articles mustBe articleFixture.articles)
    }
  }

}
