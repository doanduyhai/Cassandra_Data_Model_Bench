package com.secretclient.stress.dao.thrift.cql3flat;


import com.secretclient.stress.dao.thrift.GenericCassandraDao;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.AbstractComposite;
import me.prettyprint.hector.api.beans.Composite;

public class OffersByCountryCQL3FlatDao extends GenericCassandraDao<String, Composite, String> {

    public static final String OFFERS_BY_COUNTRY = "offers_by_country_flat";
    public static final String PARTITION_KEY = "OFFERS_BY_COUNTRY";

    public OffersByCountryCQL3FlatDao(Keyspace keyspace) {
        super(keyspace);

        columnFamily = OFFERS_BY_COUNTRY;
        keySerializer = STRING_SRZ;
        columnNameSerializer = COMPOSITE_SRZ;
        valueSerializer = STRING_SRZ;
    }

    public void getOffersByCountry(String countryIsoCode) {
        final Composite start = new Composite();
        final Composite end = new Composite();
        start.addComponent(0, countryIsoCode, AbstractComposite.ComponentEquality.EQUAL);
        start.addComponent(1, "", AbstractComposite.ComponentEquality.EQUAL);
        end.addComponent(0, countryIsoCode, AbstractComposite.ComponentEquality.EQUAL);
        end.addComponent(1, "zzzzzz", AbstractComposite.ComponentEquality.LESS_THAN_EQUAL);
        this.findColumnsRange(PARTITION_KEY, start, end, false, 100);
    }
}
