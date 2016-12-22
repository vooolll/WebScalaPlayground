package repos

import actors.Article
import com.google.inject.Inject
import configs._
import controllers.Articles
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import controllers.json.ArticleJsonParser._

import scala.concurrent._

class NewsRepo @Inject()(wsClient: WSClient, appConfig: AppConfig) {

  //TODO add automatically logging of resource requests
  def articles()(implicit ec: ExecutionContext): Future[Articles] = {
    wsClient.url(News.articles)
      .withQueryString("source" -> News.source, "apiKey" -> appConfig.newsApiKey)
      .get() map { response =>
      Articles((Json.parse(response.body) \ "articles").as[Seq[Article]])
    }
  }

}
