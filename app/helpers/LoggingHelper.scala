package helpers

import play.api.Logger

object LoggingHelper {
  def logException(t: Throwable): Throwable = {
    Logger.error(t.getMessage)
    Logger.trace(t.getMessage, t)
    t
  }
}
