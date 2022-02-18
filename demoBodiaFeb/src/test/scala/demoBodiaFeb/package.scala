
import io.gatling.core.Predef._
import io.gatling.http.Predef._

package object requests {

  val headers_0 = Map(
    "Sec-Fetch-Dest" -> "document",
    "Sec-Fetch-Mode" -> "navigate",
    "Sec-Fetch-Site" -> "none",
    "Sec-Fetch-User" -> "?1",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_1 = Map(
    "Origin" -> "https://challenge.flood.io",
    "Sec-Fetch-Dest" -> "document",
    "Sec-Fetch-Mode" -> "navigate",
    "Sec-Fetch-Site" -> "same-origin",
    "Sec-Fetch-User" -> "?1",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_5 = Map(
    "Accept" -> "*/*",
    "Sec-Fetch-Dest" -> "empty",
    "Sec-Fetch-Mode" -> "cors",
    "Sec-Fetch-Site" -> "same-origin",
    "X-Requested-With" -> "XMLHttpRequest")

  object HomePage {
    val openHomePage = exec(http("Open Host Page")
      .get("/")
      .headers(headers_0)
      .check(regex("""<title>(.*?)</title>""").is("Flood IO Script Challenge"))
      .check(regex("name=\"authenticity_token\" type=\"hidden\" value=\"(.*?)\"").find.saveAs("authenticity_token"))
      .check(regex(".*step_id.*value=\"(.*?)\"").find.saveAs("step_id")))
  }

  object User {
    val postClickStartButton = exec(http("Click Start Button")
      .post("/start")
      .headers(headers_1)
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${authenticity_token}")
      .formParam("challenger[step_id]", "${step_id}")
      .formParam("challenger[step_number]", "1")
      .formParam("commit", "Start")
      .check(status.is(302)))

    val getAgeFromDropdown = exec(http("Select Age from dropdown menu")
      .get("/step/2")
      .headers(headers_0)
      .check(status.is(200))
      .check(regex(".*step_id.*value=\"(.*?)\"").find.saveAs("step_id")))

    val postClickNextButton = exec(http("Click Next Button")
      .post("/start")
      .headers(headers_1)
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${authenticity_token}")
      .formParam("challenger[step_id]", "${step_id}")
      .formParam("challenger[step_number]", "2")
      .formParam("challenger[age]", "25")
      .formParam("commit", "Next")
      .check(status.is(302)))

    val getLargestOrder =exec(http("Select Largest Order")
      .get("/step/3")
      .headers(headers_0)
      .check(status.is(200))
      .check(regex(".*step_id.*value=\"(.*?)\"").find.saveAs("step_id"))
      .check(regex(".*challenger_order.*\">(.*?)<\\/label>").findAll.transform(list => list.map(_.toInt).max).saveAs("largest_order"))
      .check(regex(".*order_selected.*value=\"(.*?)\".*\">${largest_order}<\\/label>").find.saveAs("order_selected")))

    val postClickNextButton2 = exec(http("Click Next Button 2")
      .post("/start")
      .headers(headers_1)
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${authenticity_token}")
      .formParam("challenger[step_id]", "${step_id}")
      .formParam("challenger[step_number]", "3")
      .formParam("challenger[largest_order]", "${largest_order}")
      .formParam("challenger[order_selected]", "${order_selected}")
      .formParam("commit", "Next")
      .check(status.is(302)))

    val getCollectedOrders = exec(http("Collecting several Orders")
      .get("/step/4")
      .headers(headers_0)
      .check(status.is(200))
      .check(regex(".*step_id.*value=\"(.*?)\"").find.saveAs("step_id"))
      .check(regex(".*challenger_order_.*value=\"(.*?)\"").findRandom.saveAs("challenger_order_value"))
      .check(regex(".*challenger_order_.*name=\"(.*?)\"").findAll.saveAs("orders_name")))

    val postClickNextButton3 = exec(http("Click Next Button 3")
      .post("/start")
      .headers(headers_1)
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${authenticity_token}")
      .formParam("challenger[step_id]", "${step_id}")
      .formParam("challenger[step_number]", "4")
      .formParam("${orders_name(0)}", "${challenger_order_value}")
      .formParam("${orders_name(1)}", "${challenger_order_value}")
      .formParam("${orders_name(2)}", "${challenger_order_value}")
      .formParam("${orders_name(3)}", "${challenger_order_value}")
      .formParam("${orders_name(4)}", "${challenger_order_value}")
      .formParam("${orders_name(5)}", "${challenger_order_value}")
      .formParam("${orders_name(6)}", "${challenger_order_value}")
      .formParam("${orders_name(7)}", "${challenger_order_value}")
      .formParam("${orders_name(8)}", "${challenger_order_value}")
      .formParam("${orders_name(9)}", "${challenger_order_value}")
      .formParam("commit", "Next")
      .check(status.is(302)))

    val getSaveCodeValue = exec(http("Saving Code Value")
      .get("/code")
      .headers(headers_5)
      .check(status.is(200))
      .check(jsonPath("$..code").ofType[Int].saveAs("code")))

    val getOneTimeToken = exec(http("One Time Token")
      .get("/step/5")
      .headers(headers_0)
      .check(status.is(200))
      .check(regex(".*step_id.*value=\"(.*?)\"").find.saveAs("step_id")))

    val postClickNextButton4 = exec(http("Click Next Button 4")
      .post("/start")
      .headers(headers_1)
      .formParam("utf8", "✓")
      .formParam("authenticity_token", "${authenticity_token}")
      .formParam("challenger[step_id]", "${step_id}")
      .formParam("challenger[step_number]", "5")
      .formParam("challenger[one_time_token]", "${code}")
      .formParam("commit", "Next")
      .check(status.is(302)))

    val getDoneTest = exec(http("Done")
      .get("/done")
      .headers(headers_0)
      .check(status.is(200)))
  }

}
