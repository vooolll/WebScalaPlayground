package bootstrap

import controllers.json
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.Results._
import play.api.mvc.{ActionBuilder, Request, Result}

import scala.concurrent.Future

object BaseAction extends ActionBuilder[Request] {
  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    block(request) recover {
      case (t: Throwable) =>
        Logger.error(t.getMessage)
        Logger.trace(t.getMessage, t)
        InternalServerError(json.error("unknown error"))
    }
  }
}
