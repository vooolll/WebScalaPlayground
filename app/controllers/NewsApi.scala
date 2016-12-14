package controllers

import actors._
import akka.actor._
import akka.pattern._
import akka.util.Timeout
import com.google.inject._
import com.google.inject.name._
import configs.AppConfig
import play.api.libs.json._
import helpers.LoggingHelper._
import play.api.mvc._

import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

class NewsApi @Inject()(@Named("news-actor") newsWorker: ActorRef, appConfig: AppConfig)
                       (implicit ec: ExecutionContext) extends Controller {

  import controllers.json.ArticleJsonParser._

  val timeout = Timeout(appConfig.askTimeout millis)

  def index = Action.async {
    val futureNews = (newsWorker ? RequestNews)(timeout)
    futureNews map {
      case Articles(articles) =>
        Ok(Json.toJson(articles)).as("application/json")
    } recover { case t => logException(t) match {
      case e: AskTimeoutException => InternalServerError(json.error("news unavailable"))
      case e: Throwable => InternalServerError(json.error("unknown error"))
    }}
  }
}

case class Articles(articles: Seq[Article])