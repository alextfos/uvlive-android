package es.uv.uvlive.data.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import es.uv.uvlive.data.database.UVLiveDB;

/**
 * Created by atraver on 21/03/17.
 */
@Table(database = UVLiveDB.class)
public class ConversationTable extends BaseModel {

    @PrimaryKey
    long id;

    @Column
    String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
