package actors

import akka.actor._
import akka.event._
import akka.pattern._
import com.google.inject._
import repos.NewsRepo

import scala.concurrent._

class NewsActor @Inject()(newsRepo: NewsRepo)
                         (implicit ec: ExecutionContext) extends Actor {

  override def receive = LoggingReceive {
    case RequestNews =>
      pipe(newsRepo.articles()).to(sender())
  }
}