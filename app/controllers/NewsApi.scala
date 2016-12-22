package controllers

import actors._
import akka.actor._
import akka.pattern._
import com.google.inject._
import com.google.inject.name._
import configs.AppConfig
import controllers.json.ArticleJsonParser._
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent._
import scala.language.postfixOps

class NewsApi @Inject()(@Named("news-actor") newsWorker: ActorRef, appConfig: AppConfig)
                       (implicit ec: ExecutionContext) extends Controller {

  def index = Action.async {
    val futureNews = newsWorker.ask(RequestNews)(appConfig.askGlobalTimeout)
    futureNews map {
      case Articles(articles) =>
        Ok(Json.toJson(articles)).as("application/json")
    } recover {
      case (t: Throwable) => handlers.handleInternalServerError(t)
    }
  }
}