package ua.dominos.dao;

public final class Query {
    public static final String SELECT_FROM_DOMINO_TILES = "select * from domino_tiles";
    public static final String INSERT_INTO_CHAINS = "insert into chains values(default, ?)";
    public static final String INSERT_INTO_DOMINOES_CHAINS = "insert into dominoes_chains value(default, ?, ?, ?)";
    public static final String SELECT_FROM_DOMINOES_CHAINS = "select * from dominoes_chains";
}
