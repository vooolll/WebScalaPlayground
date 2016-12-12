package http

import akka.actor.ActorSystem
import configs.AppConfig
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext
import scala.reflect.ClassTag

trait BaseAppSpec extends PlaySpec with OneAppPerSuite {
  val injector = app.injector

  implicit val wsClient = instanceOf[WSClient]
  implicit val ec = injector.instanceOf[ExecutionContext]
  implicit val appConfig = injector.instanceOf[AppConfig]
  implicit val actorSystem = injector.instanceOf[ActorSystem]

  def instanceOf[T: ClassTag]: T = injector.instanceOf[T]
}
