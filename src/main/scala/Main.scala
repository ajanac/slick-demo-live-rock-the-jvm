/**
 * @author : ajanasathian
 * @mailto : ajanacs@gmail.com
 * @created : 7/7/22, Thursday
 *          To change this template use File | Settings | File and Code Templates
 * */

import java.time.LocalDate
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object PrivateExecutionContext
{
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(executor)
}

object Main
{
  import slick.jdbc.PostgresProfile.api._
  import PrivateExecutionContext._

  val shawshankRedemption = Movie(1L, "The Shawshank Redemption", LocalDate.of(1994, 9, 23), 162)
  val theMatrix = Movie(2L, "The Matrix", LocalDate.of(1999, 3, 31), 134)

  def demoInsertMovie(): Unit =
    {
      val queryDescription = SlickTables.movieTable += shawshankRedemption
      val futureId: Future[Int] = Connecction.db.run(queryDescription)
      futureId.onComplete{
        case Success(newMovieId) => println(s"Query was successful, new id is $newMovieId")
        case Failure(ex) => println("Query failed, reason $ex")
      }
      Thread.sleep(100000)
    }

  val db = Database.forConfig("postgres")

  def demoReadAllMovie(): Unit = {
    val resultFuture: Future[Seq[Movie]] = Connecction.db.run(SlickTables.movieTable.result)
    resultFuture.onComplete {
      case Success(movies) => println(s"Fetched: ${movies.mkString(",")}")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    Thread.sleep(10000)
  }

  def demoReadSomeMovies(): Unit = {
    val resultFuture: Future[Seq[Movie]] = Connecction.db.run(SlickTables.movieTable.filter(_.name.like("%Matrix%")).result)
    resultFuture.onComplete {
      case Success(movies) => println(s"Fetched: ${movies.mkString(",")}")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    Thread.sleep(10000)
  }

  def demoUpdate(): Unit =
    {
      val queryDescriptor = SlickTables.movieTable.filter(_.id === 1L).update(shawshankRedemption.copy(lengthInMin = 150))
      val futureId: Future[Int] = Connecction.db.run(queryDescriptor)
      futureId.onComplete{
        case Success(newMovieId) => println(s"Query was successful, new id is $newMovieId")
        case Failure(ex) => println("Query failed, reason $ex")
      }
      Thread.sleep(100000)
    }

  def demoDelete(): Unit =
    {
      Connecction.db.run(SlickTables.movieTable.filter(_.name.like("%Matrix%")).delete)
      Thread.sleep(5000)
    }


  def main(args: Array[String]): Unit =
    {
      demoDelete()
    }
}
