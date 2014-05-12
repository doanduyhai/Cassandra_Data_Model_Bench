package com.secretclient.stress.dao.thrift.generic;

import com.secretclient.stress.dao.thrift.GenericCassandraDao;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.AbstractComposite;
import me.prettyprint.hector.api.beans.Composite;

public class OptionByCountryDao extends GenericCassandraDao<String, Composite, String> {

    public static final String OPTION_BY_COUNTRY = "option_by_country";
    public static final String PARTITION_KEY = "OPTION_BY_COUNTRY";

    public OptionByCountryDao(Keyspace keyspace) {
        super(keyspace);

        columnFamily = OPTION_BY_COUNTRY;
        keySerializer = STRING_SRZ;
        columnNameSerializer = COMPOSITE_SRZ;
        valueSerializer = STRING_SRZ;
    }

    public void getOptionByCountry(String countryCodeIso) {
        final Composite columnName = new Composite();
        columnName.addComponent(0, countryCodeIso, AbstractComposite.ComponentEquality.EQUAL);
        this.getValue(PARTITION_KEY, columnName);
    }
}
