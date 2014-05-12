package com.secretclient.stress.dao.thrift;

import static me.prettyprint.hector.api.factory.HFactory.createSliceQuery;
import java.util.ArrayList;
import java.util.List;
import me.prettyprint.cassandra.serializers.CompositeSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;

public abstract class GenericCassandraDao<K, N, V> {

    public static final StringSerializer STRING_SRZ = StringSerializer.get();
    public static final CompositeSerializer COMPOSITE_SRZ = CompositeSerializer.get();

    public Keyspace keyspace;

    protected Serializer<K> keySerializer;
    protected Serializer<N> columnNameSerializer;
    protected Serializer<V> valueSerializer;
    protected String columnFamily;

    protected GenericCassandraDao(Keyspace keyspace) {
        this.keyspace = keyspace;
    }

    public V getValue(K key, N name) {
        V result = null;
        HColumn<N, V> column = HFactory
                .createColumnQuery(keyspace, keySerializer, columnNameSerializer, valueSerializer)
                .setColumnFamily(columnFamily).setKey(key).setName(name).execute().get();
        if (column != null) {
            result = column.getValue();
        }
        return result;
    }


    public List<Pair<N, V>> findColumnsRange(K key, N startName, N endName, boolean reverse, int count) {
        List<HColumn<N, V>> results = createSliceQuery(keyspace, keySerializer, columnNameSerializer, valueSerializer)
                .setColumnFamily(columnFamily).setKey(key).setRange(startName, endName, reverse, count).execute()
                .get().getColumns();

        List<Pair<N, V>> columns = new ArrayList<Pair<N, V>>();
        for (HColumn<N, V> column : results) {
            columns.add(Pair.create(column.getName(), column.getValue()));
        }

        return columns;
    }
}
