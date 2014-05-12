package com.libon.server.gatling.simulation.cql3stress

import com.libon.server.gatling.helper.Conf
import com.libon.server.gatling.helper.ServerConf
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import org.joda.time.DateTime
import akka.util.duration.intToDurationInt
import bootstrap._


class CQL3FlatViaThriftStress extends Simulation {

	val totalDuration: Int = Integer.getInteger("duration", 1);
	val ramp: Int = Integer.getInteger("ramp", 0)
	val nbUsers: Int = Integer.getInteger("users", 1);
	val urlBases = System.getProperty("url_bases", "http://localhost:8080").split(",");

	var scn = scenario("CQL3 Flat via Thrift stress").feed(csv("countryIsoCodes.csv").circular).
		during(totalDuration minutes) {
		exec(http("/thrift/flat/offers/id").get("/thrift/flat/offers/offer05"))
			.pause(2 millis, 10 millis)
			.exec(http("/thrift/flat/options/code").get("/thrift/flat/options/vp03"))
			.pause(2 millis, 10 millis)
			.exec(http("/thrift/flat/offers-by-country/country_iso_code").get("/thrift/flat/offers-by-country/${country_iso_code}"))
			.pause(2 millis, 10 millis)
			.exec(http("/thrift/flat/option-by-country/country_iso_code").get("/thrift/flat/option-by-country/${country_iso_code}"))
			.pause(100 millis, 200 millis)
	}
	setUp(scn.users(nbUsers).ramp(ramp).protocolConfig(httpConfig.baseURLs(urlBases(0),urlBases(1))))

}
