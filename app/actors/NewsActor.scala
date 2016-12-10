package actors

import akka.actor.Status.Failure
import akka.actor._
import akka.event._
import akka.pattern._
import com.google.inject._
import configs._
import controllers.Articles
import play.api.Logger
import play.api.libs.json._
import play.api.libs.ws._

import scala.concurrent._

class NewsActor @Inject()(wsClient: WSClient, appConfig: AppConfig)
                         (implicit ec: ExecutionContext) extends Actor {

  import ArticleJsonParser._

  override def receive = LoggingReceive {
    case RequestNews =>
      val futureArticles = wsClient.url(News.ARTICLES)
        .withQueryString("source" -> News.SOURCE, "apiKey" -> appConfig.newsApiKey)
        .get() map { response =>
          Articles((Json.parse(response.body) \ "articles").as[Seq[Article]])
      }
      pipe(futureArticles).to(sender())
    case _ =>
      Logger.error("message not supported")
      sender() ! Failure(new NoSuchElementException("message not supported"))
  }
}

case class Article(title: String, url: String, urlToImage: String, author: String,
                   publishedAt: String, description: String)

case object RequestNews


