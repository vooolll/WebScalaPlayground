package bootstrap
import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, Suite}

trait ActorSystemCreated extends Suite with BeforeAndAfterAll {

  implicit val actorSystem = ActorSystem("base_testing_actor_system")

  override def afterAll {
    TestKit.shutdownActorSystem(actorSystem)
  }

}
