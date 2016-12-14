package controllers.json

import actors.Article
import controllers.Articles
import play.api.libs.json.Json

object ArticleJsonParser {
  implicit val formatArticle = Json.format[Article]
  implicit val formatArticles = Json.format[Articles]
}
