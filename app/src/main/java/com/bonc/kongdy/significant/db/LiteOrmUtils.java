package com.bonc.kongdy.significant.db;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;

/**
 * Created by kongdy on 2016/12/13.
 */
public class LiteOrmUtils {
    private static LiteOrm liteOrm ;

    public static void initDB(DataBaseConfig config){
        if (liteOrm == null) {
            liteOrm = LiteOrm.newSingleInstance(config);
        }
    }

    public static LiteOrm getCascadeInstance(){
        return liteOrm.cascade();
    }

    public static LiteOrm getSingleInstance(){
        return liteOrm.single();
    }

}
