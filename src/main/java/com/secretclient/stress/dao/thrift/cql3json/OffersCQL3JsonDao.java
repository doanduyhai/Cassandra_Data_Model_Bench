package com.secretclient.stress.dao.thrift.cql3json;


import com.secretclient.stress.dao.thrift.GenericCassandraDao;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.AbstractComposite;
import me.prettyprint.hector.api.beans.Composite;

public class OffersCQL3JsonDao extends GenericCassandraDao<String, Composite, String> {

    public static final String OFFERS = "offers_json";
    public static final String PARTITION_KEY = "OFFERS";
    public static final String PAYLOAD = "payload";


    public OffersCQL3JsonDao(Keyspace keyspace) {
        super(keyspace);

        columnFamily = OFFERS;
        keySerializer = STRING_SRZ;
        columnNameSerializer = COMPOSITE_SRZ;
        valueSerializer = STRING_SRZ;
    }

    public void getOfferById(String offerId) {
        final Composite columnName = new Composite();
        columnName.addComponent(0, offerId, AbstractComposite.ComponentEquality.EQUAL);
        columnName.addComponent(1, PAYLOAD, AbstractComposite.ComponentEquality.EQUAL);
        this.getValue(PARTITION_KEY, columnName);
    }
}
