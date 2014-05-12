package com.secretclient.stress.dao.thrift.generic;


import com.secretclient.stress.dao.thrift.GenericCassandraDao;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.AbstractComposite;
import me.prettyprint.hector.api.beans.Composite;

public class OffersDao extends GenericCassandraDao<String, Composite, String> {

    public static final String OFFERS = "offers";
    public static final String PARTITION_KEY = "OFFERS";


    public OffersDao(Keyspace keyspace) {
        super(keyspace);

        columnFamily = OFFERS;
        keySerializer = STRING_SRZ;
        columnNameSerializer = COMPOSITE_SRZ;
        valueSerializer = STRING_SRZ;
    }

    public void getOfferById(String offerId) {
        final Composite columnName = new Composite();
        columnName.addComponent(0, offerId, AbstractComposite.ComponentEquality.EQUAL);
        this.getValue(PARTITION_KEY, columnName);
    }
}
