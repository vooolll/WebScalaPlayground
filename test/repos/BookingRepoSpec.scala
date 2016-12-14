package repos

import bootstrap.AsyncActorSpec

class BookingRepoSpec extends AsyncActorSpec {
  "BookingRepo#allBookings" must {
    "return sequence of Booking objects" in {
      val bookingRepo = new BookingRepo()
      bookingRepo.allBookings().map(bookings =>
        bookings mustBe Nil
      )
    }
  }
}
