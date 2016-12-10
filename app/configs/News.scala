package configs

import com.google.inject.{Inject, Singleton}
import play.api.Configuration

object News {
  val ARTICLES = "https://newsapi.org/v1/articles"
  val SOURCE = "techcrunch"
}

@Singleton
class AppConfig @Inject()(configuration: Configuration) {

  val newsApiKey = configuration.getString("news.api.key").get

}