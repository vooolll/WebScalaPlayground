import play.api.libs.json.Json

package object controllers {
  def jsonError(message: String) = {
    Json.obj("error" -> message)
  }
}
