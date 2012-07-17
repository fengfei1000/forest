package fengfei.forest.slice.database.utils;

import fengfei.forest.slice.database.ServerSlice;

public class SliceUtils {

    public static String getValidationQuery(ServerSlice slice) {
        String query = slice.getExtraInfo().get("validationQuery");
        if (query == null)
            query = "select 1";
        return query;
    }

    public static boolean getDefaultBoolean(ServerSlice slice, String name) {
        return getDefaultBoolean(slice, name, true);
    }

    public static boolean getDefaultBoolean(ServerSlice slice, String name, boolean def) {
        boolean b = def;
        String bString = slice.getExtraInfo().get(name);
        if (bString != null) {
            b = Boolean.parseBoolean(bString);
        }
        return b;
    }

    public static int getDefaultInt(ServerSlice slice, String name, int def) {
        int b = def;
        String bString = slice.getExtraInfo().get(name);
        if (bString != null) {
            b = Integer.parseInt(bString);
        }
        return b;
    }

}
