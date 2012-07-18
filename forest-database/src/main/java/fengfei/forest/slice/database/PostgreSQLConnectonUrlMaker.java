package fengfei.forest.slice.database;

public class PostgreSQLConnectonUrlMaker implements ConnectonUrlMaker {

    @Override
    public String makeUrl(ServerSlice slice) {
        String url = "jdbc:postgresql://%s:%s/%s";
        return String.format(url, slice.getHost(), slice.getPort(), slice.getSchema());
    }

}