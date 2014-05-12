package com.secretclient.stress.init;


import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.policies.Policies;

public class CQL3Bootstrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CQL3Bootstrapper.class);

    private static final String LOGIN = "cassandra.login";
    private static final String PASSWORD = "cassandra.password";
    private static final String CQL3_HOSTNAMES = "cassandra.cql3.hostnames";
    private static final String CQL_PORT = "cassandra.cql3.port";
    private static final String KEYSPACE_NAME = "cassandra.keyspace";


    private PropertyLoader propertyLoader = new PropertyLoader();

    private final Session session;

    private final Map<QueryType, PreparedStatement> psMap;


    public CQL3Bootstrapper() {

        final String[] hostnames = StringUtils.split(propertyLoader.getString(CQL3_HOSTNAMES), ",");
        final Integer cqlPort = propertyLoader.getInt(CQL_PORT);
        final String login = propertyLoader.getString(LOGIN);
        final String password = propertyLoader.getString(PASSWORD);
        final String keyspaceName = propertyLoader.getString(KEYSPACE_NAME);

        LOGGER.info("Build CQL3 cluster");
        final Cluster cluster = Cluster.builder()
                .addContactPoints(hostnames)
                .withPort(cqlPort)
                .withCredentials(login, password)
                .withCompression(ProtocolOptions.Compression.NONE)
                .withLoadBalancingPolicy(Policies.defaultLoadBalancingPolicy())
                .withSocketOptions(new SocketOptions().setKeepAlive(true))
                .build();

        session = cluster.connect(keyspaceName);
        LOGGER.info("Build CQL3 native session and connect to keyspace : " + session.getLoggedKeyspace());

        psMap = prepareStatements(session);
        LOGGER.info("Prepare CQL3 statements");

    }

    private Map<QueryType, PreparedStatement> prepareStatements(Session session) {
        Map<QueryType, PreparedStatement> psMap = new HashMap<>();
        for (QueryType queryType : QueryType.values()) {
            final PreparedStatement preparedStatement = session.prepare(queryType.statement);
            psMap.put(queryType, preparedStatement);
        }
        return psMap;
    }

    public static enum QueryType {
        GET_OFFER_JSON("SELECT payload FROM offers_json WHERE partition_key=? AND id=?"),
        GET_OPTION_JSON("SELECT payload FROM options_json WHERE partition_key=? AND code=?"),
        GET_AVAILABLE_OFFERS_JSON("SELECT payload FROM offers_by_country_json WHERE partition_key=? AND country=? ORDER BY country,id ASC"),
        GET_AVAILABLE_OPTION_JSON("SELECT payload FROM option_by_country_json WHERE partition_key=? AND country=?"),

        //

        GET_OFFER_FLAT("SELECT * FROM offers_flat WHERE partition_key=? AND id=?"),
        GET_OPTION_FLAT("SELECT * FROM options_flat WHERE partition_key=? AND code=?"),
        GET_AVAILABLE_OFFERS_FLAT("SELECT * FROM offers_by_country_flat WHERE partition_key=? AND country_code_iso=? ORDER BY country_code_iso,offer_id ASC"),
        GET_AVAILABLE_OPTION_FLAT("SELECT * FROM option_by_country_flat WHERE partition_key=? AND country_code_iso=?");

        private final String statement;

        QueryType(String statement) {
            this.statement = statement;
        }
    }

    public void execute(QueryType queryType, Object... boundValues) {
        if (psMap.containsKey(queryType)) {
            final PreparedStatement preparedStatement = psMap.get(queryType);
            final BoundStatement boundStatement = preparedStatement.bind(boundValues);
            session.execute(boundStatement);
        }
    }

}
