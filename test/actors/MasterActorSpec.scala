package actors

import bootstrap.AsyncActorSpec
import repos.Master

class MasterActorSpec extends AsyncActorSpec {

  val fixture = new {
    val yana = Master("Yana", "Chirkova", None)
  }
//
//  "MasterActor#tell(GiveAllMasters)" must {
//    s"must recieve message and return list with one master ${fixture.yana}" in {
//
//    }
//  }

}
