package configs

import akka.util.Timeout
import play.api._
import com.google.inject._
import scala.concurrent.duration._

@Singleton
class AppConfig @Inject()(configuration: Configuration) {

  val Some(newsApiKey) = configuration.getString("news.api.key")
  val Some(askTimeout) = configuration.getMilliseconds("actor.ask.timeout")

  val askGlobalTimeout = Timeout(askTimeout milliseconds)
}