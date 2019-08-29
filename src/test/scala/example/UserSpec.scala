package example
import org.scalatest.{PropSpec, Matchers}
import org.scalatest.prop.PropertyChecks
import org.scalacheck.Gen
import RegExUtil._
import Generators._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

class UserSpec extends PropSpec with PropertyChecks with Matchers {

  property("All valid emails must pass the email regex") {
    val testCases = List(
      "what.e.v.e.r@example.co.uk",
      "6237468@example.gov",
      "f@a.ke",
      "lol@12345.lol",
      "hi__mom@example.club",
      "this+that@there.then",
      "_@here.or",
      "-@there.and",
      "+@12.three",
      "a@-.bc"
    )

    testCases.foreach(validate(_, emailRegEx) shouldBe true)
  }

  property("An alphanumeric string will fail the email regex") {
    forAll(Gen.alphaStr) { str =>
      validate(str, emailRegEx) shouldBe false
    }
  }

  property("All generated emails must pass the email regex") {
    forAll(emailGen) { email =>
      validate(email, emailRegEx) shouldBe true
    }
  }

  property(
    "All improperly formatted phone numbers must fail the phone number regex"
  ) {
    val phoneNumbers = List(
      "123-456-789",
      "--",
      " - - ",
      "0-0-0",
      "abc-def-ghij",
      "(123) 456-7890",
      "123 456 7890",
      "1-800-123-4567"
    )

    phoneNumbers.foreach(validate(_, phoneRegEx) shouldBe false)
  }

  property("A numeric string will fail the phone regex") {
    forAll(Gen.numStr) { phone =>
      validate(phone, phoneRegEx) shouldBe false
    }
  }

  property("An empty string will fail the phone regex") {
    validate("", phoneRegEx) shouldBe false
  }

  property("A valid phone number must pass the phone regex") {
    forAll(phoneGen) { phone =>
      validate(phone, phoneRegEx) shouldBe true
    }
  }

  property("Generated users must be valid") {
    forAll(userGen) { user =>
      userFirstNames.contains(user.firstName) shouldBe true
      userLastNames.contains(user.lastName) shouldBe true
      validate(user.phoneNumber, phoneRegEx) shouldBe true
      validate(user.email, emailRegEx) shouldBe true
    }
  }

  property("Generated user must encode to and from JSON") {
    forAll(userGen) { user =>
      decode[User](user.asJson.noSpaces) shouldBe Right(user)
    }
  }

}
