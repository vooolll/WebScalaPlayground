package controllers

import akka.pattern.AskTimeoutException
import helpers.LoggingHelper._
import play.api.mvc.Results._

package object handlers {
  def handleInternalServerError(t: Throwable) = {
    logException(t)
    t match {
      case e: AskTimeoutException => InternalServerError(json.error("news unavailable"))
      case e: Throwable => InternalServerError(json.error("unknown error"))
    }
  }
}
