package configs

import com.google.inject.{Inject, Singleton}
import play.api.Configuration

@Singleton
class AppConfig @Inject()(configuration: Configuration) {

  val Some(newsApiKey) = configuration.getString("news.api.key")
  val Some(askTimeout) = configuration.getMilliseconds("actor.ask.timeout")

}