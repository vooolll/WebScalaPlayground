package bootstrap

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

trait ActorSpec extends WordSpec with MustMatchers with OptionValues with MockitoSugar with BeforeAndAfterAll {
  implicit val actorSystem = ActorSystem("base_testing_actor_system")

  override def afterAll {
    TestKit.shutdownActorSystem(actorSystem)
  }
}

