package example

import scala.util.matching.Regex

object RegExUtil {

  val emailRegEx =
    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".r

  val phoneRegEx = "^\\d{3}-\\d{3}-\\d{4}$".r

  def validate(str: String, regEx: Regex) = regEx.pattern.matcher(str).matches()
}
