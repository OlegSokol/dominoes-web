package ua.dominos.dao;

public final class Query {
    public static final String GET_ALL_DOMINO_TILES = "select * from domino_tiles";
    public static final java.lang.String INSERT_CHAIN = "insert into chains values(default, ?)";
    public static final java.lang.String INSERT_DOMINOES_CHAINS = "insert into dominoes_chains value(default, ?, ?, ?, ?)";
}
