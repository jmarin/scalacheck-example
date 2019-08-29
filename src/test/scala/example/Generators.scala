package example

import org.scalacheck.Gen

object Generators {

  implicit def stringOfN(n: Int, genChar: Gen[Char]): Gen[String] =
    Gen.listOfN(n, genChar).map(_.mkString)

  implicit def phoneGen: Gen[String] = {
    for {
      p1 <- stringOfN(3, Gen.numChar)
      p2 <- stringOfN(3, Gen.numChar)
      p3 <- stringOfN(4, Gen.numChar)
      sep = "-"
    } yield List(p1, p2, p3).mkString(sep)
  }

  implicit def emailGen: Gen[String] = {
    for {
      name <- Gen.alphaStr.filter(_.nonEmpty)
      at = "@"
      domain <- Gen.alphaStr.filter(_.nonEmpty)
      suffix <- Gen.oneOf(".com", ".org")
    } yield List(name, at, domain, suffix).mkString
  }

  val userFirstNames = List("John", "Mary", "Billy", "April")
  val userLastNames = List("Smith", "Doe")

  implicit def userGen: Gen[User] = {
    for {
      firstName <- Gen.oneOf(userFirstNames)
      lastName <- Gen.oneOf(userLastNames)
      phoneNumber <- phoneGen
      email <- emailGen
    } yield User(firstName, lastName, phoneNumber, email)
  }

}
