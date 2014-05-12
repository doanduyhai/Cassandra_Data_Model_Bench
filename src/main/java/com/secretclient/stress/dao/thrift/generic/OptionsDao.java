package com.secretclient.stress.dao.thrift.generic;

import com.secretclient.stress.dao.thrift.GenericCassandraDao;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.AbstractComposite;
import me.prettyprint.hector.api.beans.Composite;

public class OptionsDao extends GenericCassandraDao<String, Composite, String> {

    public static final String OPTIONS = "options";
    public static final String PARTITION_KEY = "OPTIONS";

    public OptionsDao(Keyspace keyspace) {
        super(keyspace);

        columnFamily = OPTIONS;
        keySerializer = STRING_SRZ;
        columnNameSerializer = COMPOSITE_SRZ;
        valueSerializer = STRING_SRZ;
    }

    public void getOptionByCode(String code) {
        final Composite columnName = new Composite();
        columnName.addComponent(0, code, AbstractComposite.ComponentEquality.EQUAL);
        this.getValue(PARTITION_KEY, columnName);
    }
}
