package db.entities

import org.joda.time.DateTime
import slick.driver.H2Driver.api._
import com.github.tototoshi.slick.H2JodaSupport._

case class MasterRow(id: Option[Long], firstName: String, lastName: String,
                     portfolioId: Option[Long], createdAt: DateTime, updatedAt: DateTime)

class Masters(tag: Tag) extends Table[MasterRow](tag, "masters") {

  def id = column[Long]("id", O.AutoInc, O.PrimaryKey)

  def firstName = column[String]("first_name")

  def lastName = column[String]("last_name")

  def portfolioId = column[Long]("portfolio_id")

  def createdAt = column[DateTime]("created_at")

  def updatedAt = column[DateTime]("updated_at")

  override def * = {
    (id.?, firstName, lastName, portfolioId.?, createdAt, updatedAt) <> (MasterRow.tupled, MasterRow.unapply)
  }

}