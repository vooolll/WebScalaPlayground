package controllers

import actors._
import akka.actor._
import akka.pattern._
import akka.util.Timeout
import com.google.inject._
import com.google.inject.name._
import play.api.libs.json._
import helpers.LoggingHelper._
import play.api.mvc._

import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

class NewsApi @Inject()(@Named("news-actor") newsWorker: ActorRef)
                       (implicit ec: ExecutionContext) extends Controller {

  import ArticleJsonParser._
  implicit val newsRequestTimeout = Timeout(5 seconds)

  def index = Action.async {

    val futureNews = (newsWorker ? RequestNews)(newsRequestTimeout)
    //TODO: Write test case for this
    futureNews.failed map (t => logException(t) match {
      case a: AskTimeoutException => InternalServerError(jsonError("news unavailable"))
      case t: Throwable => InternalServerError(jsonError("unknown error"))
    })
    futureNews map {
      case Articles(articles) =>
        Ok(Json.toJson(articles)).as("application/json")
    }
  }
}

case class Articles(articles: Seq[Article])