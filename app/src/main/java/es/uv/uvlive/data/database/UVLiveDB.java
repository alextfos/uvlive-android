package es.uv.uvlive.data.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by atraver on 21/03/17.
 */
@Database(name = UVLiveDB.DATABASE_NAME, version = UVLiveDB.VERSION)
public class UVLiveDB {

    public static final String DATABASE_NAME = "UVLive";
    public static final int VERSION = 1;
}
