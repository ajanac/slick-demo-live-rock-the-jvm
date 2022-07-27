import java.time.LocalDate

/**
 * @author : ajanasathian
 * @mailto : ajanacs@gmail.com
 * @created : 7/13/22, Wednesday
 *          To change this template use File | Settings | File and Code Templates
 * */
case class Movie(id: Long , name: String, releaseDate: LocalDate, lengthInMin: Int)

object SlickTables
{
  import slick.jdbc.PostgresProfile.api._

  class MovieTable(tag: Tag) extends Table[Movie](tag, Some("movies"), "Movie")
  {
    def id = column[Long]("movie_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def releaseDate = column[LocalDate]("release_date")
    def lengthInMIn = column[Int]("length_in_min")
    
    override def * = (id, name, releaseDate, lengthInMIn) <> (Movie.tupled, Movie.unapply)
  }

  lazy val movieTable = TableQuery[MovieTable]
}
