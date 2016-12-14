package repos

import bootstrap.AsyncActorSpec
import configs._
import controllers.Articles
import org.mockito.Mockito._
import play.api.libs.json.Json
import play.api.libs.ws._

import scala.concurrent.Future

class NewsRepoSpec extends AsyncActorSpec {

  import actors.ArticleJsonParser._

  val wsClient = mock[WSClient]
  val wsResponse = mock[WSResponse]
  val wSRequest = mock[WSRequest]
  val appConfig = mock[AppConfig]

  val query = Seq("source" -> News.SOURCE, "apiKey" -> appConfig.newsApiKey)
  when(wsResponse.body).thenReturn(Json.toJson(Articles(Seq())).toString)
  when(wSRequest.withQueryString(query:_*)).thenReturn(wSRequest)
  when(wSRequest.get()).thenReturn(Future.successful(wsResponse))
  when(wsClient.url(News.ARTICLES)).thenReturn(wSRequest)

  "NewsRepo#getArticles" must {
    "return Nil" in {
      new NewsRepo(wsClient, appConfig).getArticles.map { case Articles(articles) =>
        articles mustBe Nil
      }
    }
  }
}
