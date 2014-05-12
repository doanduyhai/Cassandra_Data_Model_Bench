package com.secretclient.stress.dao.thrift.cql3json;

import com.secretclient.stress.dao.thrift.GenericCassandraDao;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.AbstractComposite;
import me.prettyprint.hector.api.beans.Composite;

public class OptionsCQL3JsonDao extends GenericCassandraDao<String, Composite, String> {

    public static final String OPTIONS = "options_json";
    public static final String PARTITION_KEY = "OPTIONS";
    public static final String PAYLOAD = "payload";

    public OptionsCQL3JsonDao(Keyspace keyspace) {
        super(keyspace);

        columnFamily = OPTIONS;
        keySerializer = STRING_SRZ;
        columnNameSerializer = COMPOSITE_SRZ;
        valueSerializer = STRING_SRZ;
    }

    public void getOptionByCode(String code) {
        final Composite columnName = new Composite();
        columnName.addComponent(0, code, AbstractComposite.ComponentEquality.EQUAL);
        columnName.addComponent(1, PAYLOAD, AbstractComposite.ComponentEquality.EQUAL);
        this.getValue(PARTITION_KEY, columnName);
    }
}
