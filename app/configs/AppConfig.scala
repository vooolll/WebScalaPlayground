package configs

import play.api._
import com.google.inject._

@Singleton
class AppConfig @Inject()(configuration: Configuration) {

  val Some(newsApiKey) = configuration.getString("news.api.key")
  val Some(askTimeout) = configuration.getMilliseconds("actor.ask.timeout")

}