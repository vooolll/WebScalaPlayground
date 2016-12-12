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
    futureNews map {
      case Articles(articles) =>
        Ok(Json.toJson(articles)).as("application/json")
    } recover { case t => logException(t) match {
      case e: AskTimeoutException => InternalServerError(jsonError("news unavailable"))
      case e: Throwable => InternalServerError(jsonError("unknown error"))
    }}
  }
}

case class Articles(articles: Seq[Article])