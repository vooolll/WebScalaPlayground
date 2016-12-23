package controllers

import actors.NewsActor.RequestNews
import actors._
import akka.actor.ActorRef
import akka.pattern._
import bootstrap.{BaseAction, BaseController}
import com.google.inject._
import com.google.inject.name.Named
import controllers.json.ArticleJsonParser._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._

import scala.language.postfixOps

class NewsApi @Inject()(@Named("news-actor") newsWorker: ActorRef, appConfig: configs.AppConfig)
  extends BaseController(appConfig) {

  def index = BaseAction.async {
    newsWorker.ask(RequestNews) map {
      case Articles(articles) =>
        Ok(Json.toJson(articles)).as("application/json")
    }
  }
}

case class Articles(articles: Seq[Article])