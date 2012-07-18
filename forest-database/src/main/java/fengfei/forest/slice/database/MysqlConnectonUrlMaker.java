package fengfei.forest.slice.database;

public class MysqlConnectonUrlMaker implements ConnectonUrlMaker {

    @Override
    public String makeUrl(ServerSlice slice) {

        String characterEncoding = slice.getExtraInfo().get("characterEncoding");

        if (null == characterEncoding || "".equals(characterEncoding)) {
            String url = "jdbc:mysql://%s:%s/%s";
            return String.format(url, slice.getHost(), slice.getPort(), slice.getSchema());
        } else {
            String url = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=%s";
            return String.format(
                url,
                slice.getHost(),
                slice.getPort(),
                slice.getSchema(),
                characterEncoding);
        }

    }
}