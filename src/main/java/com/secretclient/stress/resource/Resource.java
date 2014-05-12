package com.secretclient.stress.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.secretclient.stress.service.StressService;
import com.sun.jersey.spi.resource.Singleton;

@Path("/")
@Singleton
public class Resource {

    private static final Logger PERF_LOGGER = LoggerFactory.getLogger("CASSANDRA_PERF");

    private static final String DONE = "DONE";

    private StressService service = new StressService();

    // Thrift
    @GET
    @Path("/thrift/offers/{offer-id}")
    @Produces("text/plain")
    public String stressThriftOfferById(@PathParam(value = "offer-id") String offerId) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-offer-by-id", PERF_LOGGER);
        service.getOfferThriftById(offerId);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/thrift/offers-by-country/{country}")
    @Produces("text/plain")
    public String stressThriftAvailableOffersByCountry(@PathParam(value = "country") String countryIsoCode) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-available-offers-by-country", PERF_LOGGER);
        service.getAvailableOffersThriftByCountry(countryIsoCode);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/thrift/options/{code}")
    @Produces("text/plain")
    public String stressThriftOptionByCode(@PathParam(value = "code") String code) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-option-by-code", PERF_LOGGER);
        service.getOptionThriftByCode(code);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/thrift/option-by-country/{country}")
    @Produces("text/plain")
    public String stressThriftAvailableOptionByCountry(@PathParam(value = "country") String countryIsoCode) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-option-by-country", PERF_LOGGER);
        service.getAvailableOptionThriftByCountry(countryIsoCode);
        stopWatch.stop();
        return DONE;
    }

    // CQL3 Json via Thrift
    @GET
    @Path("/thrift/json/offers/{offer-id}")
    @Produces("text/plain")
    public String stressCQL3JsonViaThriftOfferById(@PathParam(value = "offer-id") String offerId) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-offer-by-id", PERF_LOGGER);
        service.getOfferCQL3JsonViaThriftById(offerId);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/thrift/json/offers-by-country/{country}")
    @Produces("text/plain")
    public String stressCQL3JsonViaThriftAvailableOffersByCountry(@PathParam(value = "country") String countryIsoCode) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-available-offers-by-country", PERF_LOGGER);
        service.getAvailableOffersCQL3JsonViaThriftByCountry(countryIsoCode);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/thrift/json/options/{code}")
    @Produces("text/plain")
    public String stressCQL3JsonViaThriftOptionByCode(@PathParam(value = "code") String code) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-option-by-code", PERF_LOGGER);
        service.getOptionCQL3JsonViaThriftByCode(code);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/thrift/json/option-by-country/{country}")
    @Produces("text/plain")
    public String stressThriftAvailableOptionCQL3JsonViaByCountry(@PathParam(value = "country") String countryIsoCode) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-option-by-country", PERF_LOGGER);
        service.getAvailableOptionCQL3JsonViaThriftByCountry(countryIsoCode);
        stopWatch.stop();
        return DONE;
    }

    // CQL3 Flat via Thrift
    @GET
    @Path("/thrift/flat/offers/{offer-id}")
    @Produces("text/plain")
    public String stressCQL3FlatViaThriftOfferById(@PathParam(value = "offer-id") String offerId) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-offer-by-id", PERF_LOGGER);
        service.getOfferCQL3FlatViaThriftById(offerId);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/thrift/flat/offers-by-country/{country}")
    @Produces("text/plain")
    public String stressCQL3FlatViaThriftAvailableOffersByCountry(@PathParam(value = "country") String countryIsoCode) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-available-offers-by-country", PERF_LOGGER);
        service.getAvailableOffersCQL3FlatViaThriftByCountry(countryIsoCode);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/thrift/flat/options/{code}")
    @Produces("text/plain")
    public String stressCQL3FlatViaThriftOptionByCode(@PathParam(value = "code") String code) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-option-by-code", PERF_LOGGER);
        service.getOptionCQL3FlatViaThriftByCode(code);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/thrift/flat/option-by-country/{country}")
    @Produces("text/plain")
    public String stressThriftAvailableOptionCQL3FlatViaByCountry(@PathParam(value = "country") String countryIsoCode) {
        StopWatch stopWatch = new Slf4JStopWatch("thrift-option-by-country", PERF_LOGGER);
        service.getAvailableOptionCQL3FlatViaThriftByCountry(countryIsoCode);
        stopWatch.stop();
        return DONE;
    }

    // CQL3 JSON
    @GET
    @Path("/cql3/json/offers/{offer-id}")
    public String stressOfferCQL3JsonById(@PathParam(value = "offer-id") String offerId) {
        StopWatch stopWatch = new Slf4JStopWatch("cql3-json-offer-by-id", PERF_LOGGER);
        service.getOfferCQL3JsonById(offerId);
        stopWatch.stop();
        return DONE;
    }


    @GET
    @Path("/cql3/json/offers-by-country/{country}")
    public String stressAvailableOffersCQL3JsonByCountry(@PathParam(value = "country") String countryIsoCode) {
        StopWatch stopWatch = new Slf4JStopWatch("cql3-json-available-offers-by-country", PERF_LOGGER);
        service.getAvailableOffersCQL3JsonByCountry(countryIsoCode);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/cql3/json/options/{code}")
    public String stressOptionCQL3JsonByCode(@PathParam(value = "code") String code) {
        StopWatch stopWatch = new Slf4JStopWatch("cql3-json-option-by-code", PERF_LOGGER);
        service.getOptionCQL3JsonByCode(code);
        stopWatch.stop();
        return DONE;
    }


    @GET
    @Path("/cql3/json/option-by-country/{country}")
    public String stressAvailableOptionCQL3JsonByCountry(@PathParam(value = "country") String countryIsoCode) {
        StopWatch stopWatch = new Slf4JStopWatch("cql3-json-option-by-country", PERF_LOGGER);
        service.getAvailableOptionCQL3JsonByCountry(countryIsoCode);
        stopWatch.stop();
        return DONE;
    }

    // CQL3 Flat
    @GET
    @Path("/cql3/flat/offers/{offer-id}")
    public String stressOfferCQL3FlatById(@PathParam(value = "offer-id") String offerId) {
        StopWatch stopWatch = new Slf4JStopWatch("cql3-flat-offer-by-id", PERF_LOGGER);
        service.getOfferCQL3FlatById(offerId);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/cql3/flat/offers-by-country/{country}")
    public String stressAvailableOffersCQL3FlatByCountry(@PathParam(value = "country") String countryIsoCode) {
        StopWatch stopWatch = new Slf4JStopWatch("cql3-flat-available-offers-by-country", PERF_LOGGER);
        service.getAvailableOffersCQL3FlatByCountry(countryIsoCode);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/cql3/flat/options/{code}")
    public String stressOptionCQL3FlatByCode(@PathParam(value = "code") String code) {
        StopWatch stopWatch = new Slf4JStopWatch("cql3-flat-option-by-code", PERF_LOGGER);
        service.getOptionCQL3FlatByCode(code);
        stopWatch.stop();
        return DONE;
    }

    @GET
    @Path("/cql3/flat/option-by-country/{country}")
    public String stressAvailableOptionCQL3FlatByCountry(@PathParam(value = "country") String countryIsoCode) {
        StopWatch stopWatch = new Slf4JStopWatch("cql3-flat-option-by-country", PERF_LOGGER);
        service.getAvailableOptionCQL3FlatByCountry(countryIsoCode);
        stopWatch.stop();
        return DONE;
    }
}
