package modules

import actors.NewsActor
import com.google.inject._
import play.api.libs.concurrent._

class BaseModule extends AbstractModule with AkkaGuiceSupport {
  def configure() = {
    bindActor[NewsActor]("news-actor")
  }
}