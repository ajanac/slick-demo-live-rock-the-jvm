/**
 * @author : ajanasathian
 * @mailto : ajanacs@gmail.com
 * @created : 7/13/22, Wednesday
 *          To change this template use File | Settings | File and Code Templates
 * */

import slick.jdbc.PostgresProfile.api._

object Connecction
{
  val db = Database.forConfig("postgres")
}
