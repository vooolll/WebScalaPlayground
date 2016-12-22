package bootstrap

import org.scalatest._
import org.scalatest.mockito.MockitoSugar

trait AsyncUnitSpec extends WordSpec
  with MustMatchers with OptionValues
  with MockitoSugar with BeforeAndAfterAll {

}
