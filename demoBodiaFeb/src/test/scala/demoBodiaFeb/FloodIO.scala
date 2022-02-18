package demoBodiaFeb

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import requests._

class FloodIO extends Simulation {

	val th_min = 1
	val th_max = 2
	val test_duration = System.getProperty("duration", "60").toInt
	val test_users = System.getProperty("users", "5").toInt

	val httpProtocol = http
		.baseUrl("https://challenge.flood.io")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""",""".*css.*""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:97.0) Gecko/20100101 Firefox/97.0")
		.disableFollowRedirect


	val scn = scenario("DemoRecorded")
		.exec(HomePage.openHomePage).pause(th_min,th_max)
		.exec(User.postClickStartButton).pause(th_min,th_max)
		.exec(User.getAgeFromDropdown).pause(th_min,th_max)
		.exec(User.postClickNextButton).pause(th_min,th_max)
		.exec(User.getLargestOrder).pause(th_min,th_max)
		.exec(User.postClickNextButton2).pause(th_min,th_max)
		.exec(User.getCollectedOrders).pause(th_min,th_max)
		.exec(User.postClickNextButton3).pause(th_min,th_max)
		.exec(User.getSaveCodeValue).pause(th_min,th_max)
		.exec(User.getOneTimeToken).pause(th_min,th_max)
		.exec(User.postClickNextButton4).pause(th_min,th_max)
		.exec(User.getDoneTest).pause(th_min,th_max)


	setUp(scn.inject(constantUsersPerSec(test_users).during(test_duration))).protocols(httpProtocol)
		.assertions(
		global.responseTime.max.lt(75),
		global.successfulRequests.percent.gt(95)
	)
}