package com.secretclient.stress.service;

import com.secretclient.stress.dao.cql3.CQL3Dao;
import com.secretclient.stress.dao.thrift.cql3flat.OffersByCountryCQL3FlatDao;
import com.secretclient.stress.dao.thrift.cql3flat.OffersCQL3FlatDao;
import com.secretclient.stress.dao.thrift.cql3flat.OptionByCountryCQL3FlatDao;
import com.secretclient.stress.dao.thrift.cql3flat.OptionsCQL3FlatDao;
import com.secretclient.stress.dao.thrift.cql3json.OffersByCountryCQL3JsonDao;
import com.secretclient.stress.dao.thrift.cql3json.OffersCQL3JsonDao;
import com.secretclient.stress.dao.thrift.cql3json.OptionByCountryCQL3JsonDao;
import com.secretclient.stress.dao.thrift.cql3json.OptionsCQL3JsonDao;
import com.secretclient.stress.dao.thrift.generic.OffersByCountryDao;
import com.secretclient.stress.dao.thrift.generic.OffersDao;
import com.secretclient.stress.dao.thrift.generic.OptionByCountryDao;
import com.secretclient.stress.dao.thrift.generic.OptionsDao;
import com.secretclient.stress.init.CQL3Bootstrapper;
import com.secretclient.stress.init.HectorBootstrapper;
import me.prettyprint.hector.api.Keyspace;

public class StressService {

    // Thrift
    private final OffersDao offersDao;
    private final OffersByCountryDao offersByCountryDao;
    private final OptionsDao optionsDao;
    private final OptionByCountryDao optionByCountryDao;

    // CQL3 Json via Thrift
    private final OffersCQL3JsonDao offersCQL3JsonDao;
    private final OffersByCountryCQL3JsonDao offersByCountryCQL3JsonDao;
    private final OptionsCQL3JsonDao optionsCQL3JsonDao;
    private final OptionByCountryCQL3JsonDao optionByCountryCQL3JsonDao;

    // CQL3 Flat via Thrift
    private final OffersCQL3FlatDao offersCQL3FlatDao;
    private final OffersByCountryCQL3FlatDao offersByCountryCQL3FlatDao;
    private final OptionsCQL3FlatDao optionsCQL3FlatDao;
    private final OptionByCountryCQL3FlatDao optionByCountryCQL3FlatDao;


    // CQL3
    private final CQL3Dao cql3Dao;

    public StressService() {
        final HectorBootstrapper hectorBootstrapper = new HectorBootstrapper();
        final CQL3Bootstrapper cql3Bootstrapper = new CQL3Bootstrapper();
        final Keyspace keyspace = hectorBootstrapper.getKeyspace();

        offersDao = new OffersDao(keyspace);
        offersByCountryDao = new OffersByCountryDao(keyspace);
        optionsDao = new OptionsDao(keyspace);
        optionByCountryDao = new OptionByCountryDao(keyspace);

        offersCQL3JsonDao = new OffersCQL3JsonDao(keyspace);
        offersByCountryCQL3JsonDao = new OffersByCountryCQL3JsonDao(keyspace);
        optionsCQL3JsonDao = new OptionsCQL3JsonDao(keyspace);
        optionByCountryCQL3JsonDao = new OptionByCountryCQL3JsonDao(keyspace);

        offersCQL3FlatDao = new OffersCQL3FlatDao(keyspace);
        offersByCountryCQL3FlatDao = new OffersByCountryCQL3FlatDao(keyspace);
        optionsCQL3FlatDao = new OptionsCQL3FlatDao(keyspace);
        optionByCountryCQL3FlatDao = new OptionByCountryCQL3FlatDao(keyspace);

        cql3Dao = new CQL3Dao(cql3Bootstrapper);
    }

    // Thrift
    public void getOfferThriftById(String offerId) {
        offersDao.getOfferById(offerId);
    }

    public void getAvailableOffersThriftByCountry(String countryIsoCode) {
        offersByCountryDao.getOffersByCountry(countryIsoCode);
    }

    public void getOptionThriftByCode(String code) {
        optionsDao.getOptionByCode(code);
    }

    public void getAvailableOptionThriftByCountry(String countryIsoCode) {
        optionByCountryDao.getOptionByCountry(countryIsoCode);
    }


    // CQL3 Json via Thrift
    public void getOfferCQL3JsonViaThriftById(String offerId) {
        offersCQL3JsonDao.getOfferById(offerId);
    }

    public void getAvailableOffersCQL3JsonViaThriftByCountry(String countryIsoCode) {
        offersByCountryCQL3JsonDao.getOffersByCountry(countryIsoCode);
    }

    public void getOptionCQL3JsonViaThriftByCode(String code) {
        optionsCQL3JsonDao.getOptionByCode(code);
    }

    public void getAvailableOptionCQL3JsonViaThriftByCountry(String countryIsoCode) {
        optionByCountryCQL3JsonDao.getOptionByCountry(countryIsoCode);
    }

    // CQL3 Flat via Thrift
    public void getOfferCQL3FlatViaThriftById(String offerId) {
        offersCQL3FlatDao.getOfferById(offerId);
    }

    public void getAvailableOffersCQL3FlatViaThriftByCountry(String countryIsoCode) {
        offersByCountryCQL3FlatDao.getOffersByCountry(countryIsoCode);
    }

    public void getOptionCQL3FlatViaThriftByCode(String code) {
        optionsCQL3FlatDao.getOptionByCode(code);
    }

    public void getAvailableOptionCQL3FlatViaThriftByCountry(String countryIsoCode) {
        optionByCountryCQL3FlatDao.getOptionByCountry(countryIsoCode);
    }

    // CQL3 JSON
    public void getOfferCQL3JsonById(String offerId) {
        cql3Dao.getOfferJsonById(offerId);
    }

    public void getAvailableOffersCQL3JsonByCountry(String countryIsoCode) {
        cql3Dao.getAvailableOffersJsonByCountry(countryIsoCode);
    }

    public void getOptionCQL3JsonByCode(String code) {
        cql3Dao.getOptionJsonByCode(code);
    }

    public void getAvailableOptionCQL3JsonByCountry(String countryIsoCode) {
        cql3Dao.getAvailableOptionJsonByCountry(countryIsoCode);
    }

    // CQL3 Flat
    public void getOfferCQL3FlatById(String offerId) {
        cql3Dao.getOfferFlatById(offerId);
    }

    public void getAvailableOffersCQL3FlatByCountry(String countryIsoCode) {
        cql3Dao.getAvailableOffersFlatByCountry(countryIsoCode);
    }

    public void getOptionCQL3FlatByCode(String code) {
        cql3Dao.getOptionFlatByCode(code);
    }

    public void getAvailableOptionCQL3FlatByCountry(String countryIsoCode) {
        cql3Dao.getAvailableOptionFlatByCountry(countryIsoCode);
    }
}
