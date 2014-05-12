package com.libon.server.gatling.simulation.cql3stress


class ThriftStress extends Simulation {

	val totalDuration: Int = Integer.getInteger("duration", 1);
	val ramp: Int = Integer.getInteger("ramp", 0)
	val nbUsers: Int = Integer.getInteger("users", 1);
	val urlBases = System.getProperty("url_bases", "http://localhost:8080").split(",");

	var scn = scenario("Thrift stress").feed(csv("countryIsoCodes.csv").circular).
		during(totalDuration minutes) {
		exec(http("/thrift/offers/id").get("/thrift/offers/offer05"))
			.pause(2 millis, 10 millis)
			.exec(http("/thrift/options/code").get("/thrift/options/vp03"))
			.pause(2 millis, 10 millis)
			.exec(http("/thrift/offers-by-country/country_iso_code").get("/thrift/offers-by-country/${country_iso_code}"))
			.pause(2 millis, 10 millis)
			.exec(http("/thrift/option-by-country/country_iso_code").get("/thrift/option-by-country/${country_iso_code}"))
			.pause(100 millis, 200 millis)
	}
	setUp(scn.users(nbUsers).ramp(ramp).protocolConfig(httpConfig.baseURLs(urlBases(0), urlBases(1))))

}
