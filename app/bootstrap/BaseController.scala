package bootstrap

import akka.util.Timeout
import com.google.inject.Inject
import configs.AppConfig
import play.api.mvc.Controller

import scala.language.postfixOps
import scala.concurrent.duration._

class BaseController @Inject()(appConfig: AppConfig) extends Controller {
  implicit val askGlobalTimeout = Timeout(appConfig.askTimeout milliseconds)
}