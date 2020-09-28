package utils

import org.slf4j.{Logger, LoggerFactory}
/*This Class takes input from the user for the Paas simulation*/
class PaasInput {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  val helper: DataCenterHelper = new DataCenterHelper

  println("Choose the programming language for Paas- 1. Scala, 2. Java, 3. Python, 4. JavaScript : ")
  var plang = scala.io.StdIn.readLine()

  println("Choose the Framework according to programming language - 1. Akka Http, 2. Spring Boot, 3. DJango, 4. NodeJs :")
  var framework = scala.io.StdIn.readLine()

  println("Choose the DataBase Management Software - 1. MongoDB, 2. MYSQL, 3. PostgresSQL :")
  var db = scala.io.StdIn.readLine()

}
