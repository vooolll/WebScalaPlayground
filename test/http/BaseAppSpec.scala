package http

import akka.actor.ActorSystem
import configs.AppConfig
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}

import scala.concurrent.ExecutionContext
import scala.reflect.ClassTag

trait BaseAppSpec extends PlaySpec with OneAppPerSuite {
  val injector = app.injector

  implicit val ec = injector.instanceOf[ExecutionContext]
  implicit val appConfig = injector.instanceOf[AppConfig]
  implicit val actorSystem = injector.instanceOf[ActorSystem]

  def instanceOf[T: ClassTag]: T = injector.instanceOf[T]
}
