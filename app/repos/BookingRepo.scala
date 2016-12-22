package repos

import org.joda.time.DateTime

import scala.concurrent.Future

class BookingRepo {
  def allBookings() = Future.successful(List.empty[Booking])
}

object ServiceType extends Enumeration {
  val NailExtension = Value
  val Manicure = Value
  val GelLacquerCoating = Value
  val LacquerCoating = Value
}

case class Portfolio()

case class Service(sType: ServiceType.Value, comment: Option[String])

case class Booking(master: Master, service: Service, startAt: DateTime)