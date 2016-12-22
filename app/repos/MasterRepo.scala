package repos

import com.google.inject.Inject
import db.entities.Masters
import db.helper.DefaultDatabase

import scala.concurrent.{ExecutionContext, Future}


class MasterRepo @Inject()(db: DefaultDatabase)
                          (implicit ec: ExecutionContext) {
  import DefaultDatabase.defaultApi._

  val masters = TableQuery[Masters]

  def allMasters(): Future[Iterable[Master]] = {
    for {
      allMasters <- db.runQuery(masters.result)
    } yield allMasters.map(masterRow => Master(masterRow.firstName, masterRow.lastName, None))
  }
}

case class Master(firstName: String, lastName: String, portfolio: Option[Portfolio])