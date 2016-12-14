package controllers

import play.api.libs.json.Json

package object json {
  def error(message: String) = {
    Json.obj("error" -> message)
  }
}
