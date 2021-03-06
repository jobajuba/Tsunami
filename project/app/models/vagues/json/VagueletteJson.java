package models.vagues.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.lang.Class;
import java.lang.reflect.Field;


import models.User;
import models.vagues.VagueParticipant;
import models.vagues.Vaguelette;
import models.vagues.VagueletteHistory;


public class VagueletteJson {
    
    public Long id;
    public String body;
    public Long creationDate;
    public Long lastActivityDate;
    public Long parentId;
    public int version;
    public List<VagueletteParticipantJson> participants;
    public List<VagueletteHistoryJson> histories;
    
    public VagueletteJson(Vaguelette v) {
        this(v, false);
    }
    
    public VagueletteJson(Vaguelette v, boolean withHistories) {
        id = v.id;
        parentId = v.parentId==null ? 0 : v.parentId;
        body = v.body;
        creationDate = v.creationDate.getMillis();
        lastActivityDate = v.lastActivityDate.getMillis();
        version = v.version;
        participants = VagueletteParticipantJson.getParticipants(v);
        if(withHistories)
            histories = VagueletteHistoryJson.getHistories(v);
    }
    
    public Map<String,Object> getMap() {
        Map<String,Object> map = new HashMap<String, Object>();
        for(Field f : this.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                map.put(f.getName(),f.get(this));
            } catch(IllegalAccessException e) {
                // Should never happen.
                throw new IllegalAccessError(e.getMessage());
            } catch (IllegalArgumentException e) {
                throw new IllegalAccessError(e.getMessage());
            }
        }
        
        return map;
    }
}