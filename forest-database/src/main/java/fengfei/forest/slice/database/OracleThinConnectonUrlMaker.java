package fengfei.forest.slice.database;

public class OracleThinConnectonUrlMaker implements ConnectonUrlMaker {

    @Override
    public String makeUrl(ServerSlice slice) {
        String url = "jdbc:oracle:thin:@%s:%s:%s";
        return String.format(url, slice.getHost(), slice.getPort(), slice.getSchema());
    }

}