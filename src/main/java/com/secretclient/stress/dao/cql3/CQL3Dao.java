package com.secretclient.stress.dao.cql3;

import static com.secretclient.stress.init.CQL3Bootstrapper.QueryType.GET_AVAILABLE_OFFERS_FLAT;
import static com.secretclient.stress.init.CQL3Bootstrapper.QueryType.GET_AVAILABLE_OFFERS_JSON;
import static com.secretclient.stress.init.CQL3Bootstrapper.QueryType.GET_AVAILABLE_OPTION_FLAT;
import static com.secretclient.stress.init.CQL3Bootstrapper.QueryType.GET_AVAILABLE_OPTION_JSON;
import static com.secretclient.stress.init.CQL3Bootstrapper.QueryType.GET_OFFER_FLAT;
import static com.secretclient.stress.init.CQL3Bootstrapper.QueryType.GET_OFFER_JSON;
import static com.secretclient.stress.init.CQL3Bootstrapper.QueryType.GET_OPTION_FLAT;
import static com.secretclient.stress.init.CQL3Bootstrapper.QueryType.GET_OPTION_JSON;
import com.secretclient.stress.init.CQL3Bootstrapper;

public class CQL3Dao {

    private static final String OFFERS_PARTITION_KEY = "OFFERS";
    private static final String OPTIONS_PARTITION_KEY = "OPTIONS";
    private static final String OFFERS_BY_COUNTRY_PARTITION_KEY = "OFFERS_BY_COUNTRY";
    private static final String OPTION_BY_COUNTRY_PARTITION_KEY = "OPTION_BY_COUNTRY";

    private final CQL3Bootstrapper bootstrapper;

    public CQL3Dao(CQL3Bootstrapper bootstrapper) {
        this.bootstrapper = bootstrapper;
    }

    // JSON data model
    public void getOfferJsonById(String offerId) {
        bootstrapper.execute(GET_OFFER_JSON, OFFERS_PARTITION_KEY, offerId);
    }

    public void getOptionJsonByCode(String code) {
        bootstrapper.execute(GET_OPTION_JSON, OPTIONS_PARTITION_KEY, code);
    }

    public void getAvailableOffersJsonByCountry(String countryIsoCode) {
        bootstrapper.execute(GET_AVAILABLE_OFFERS_JSON, OFFERS_BY_COUNTRY_PARTITION_KEY, countryIsoCode);
    }

    public void getAvailableOptionJsonByCountry(String countryIsoCode) {
        bootstrapper.execute(GET_AVAILABLE_OPTION_JSON, OPTION_BY_COUNTRY_PARTITION_KEY, countryIsoCode);
    }

    //Flat data model
    public void getOfferFlatById(String offerId) {
        bootstrapper.execute(GET_OFFER_FLAT, OFFERS_PARTITION_KEY, offerId);
    }

    public void getOptionFlatByCode(String code) {
        bootstrapper.execute(GET_OPTION_FLAT, OPTIONS_PARTITION_KEY, code);
    }

    public void getAvailableOffersFlatByCountry(String countryIsoCode) {
        bootstrapper.execute(GET_AVAILABLE_OFFERS_FLAT, OFFERS_BY_COUNTRY_PARTITION_KEY, countryIsoCode);
    }

    public void getAvailableOptionFlatByCountry(String countryIsoCode) {
        bootstrapper.execute(GET_AVAILABLE_OPTION_FLAT, OPTION_BY_COUNTRY_PARTITION_KEY, countryIsoCode);
    }

}
