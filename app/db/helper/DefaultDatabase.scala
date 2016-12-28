package db.helper

import com.google.inject.Inject
import play.api.Logger
import slick.backend.DatabaseConfig
import slick.dbio.{DBIOAction, NoStream}
import slick.driver.{H2Driver, JdbcProfile}

import scala.concurrent.{ExecutionContext, Future}

class DefaultDatabase @Inject()(dbConfig: DatabaseConfig[JdbcProfile])
                               (implicit ec: ExecutionContext) {
  def runQuery[R](query: DBIOAction[R, NoStream, Nothing]): Future[R] = {
    val queryResult = dbConfig.db.run(query)
    queryResult.onFailure {
      case t: Throwable => Logger.error (s"error in query future ${t.getMessage}", t)
    }
    queryResult
  }
}

object DefaultDatabase {
  val defaultApi = H2Driver.api
}
