package es.uv.uvlive.data.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import es.uv.uvlive.data.database.UVLiveDB;

@Table(database = UVLiveDB.class)
public class ConversationTable extends BaseModel {

    @PrimaryKey
    protected int id;
    @Column
    protected String name;
    @Column
    protected String participant1;
    @Column
    protected String getParticipant2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParticipant1() {
        return participant1;
    }

    public void setParticipant1(String participant1) {
        this.participant1 = participant1;
    }

    public String getGetParticipant2() {
        return getParticipant2;
    }

    public void setGetParticipant2(String getParticipant2) {
        this.getParticipant2 = getParticipant2;
    }
}
