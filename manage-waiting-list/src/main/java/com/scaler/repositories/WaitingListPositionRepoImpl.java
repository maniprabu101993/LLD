package com.scaler.repositories;

import com.scaler.models.WaitListPosition;

import java.util.*;

public class WaitingListPositionRepoImpl implements WaitListPositionRepository {
    Map<Long, WaitListPosition> map = new HashMap<>();

    private static int idCounter = 0;

    @Override
    public WaitListPosition save(WaitListPosition waitListPosition) {
        if(waitListPosition.getId() == 0){
            waitListPosition.setId(++idCounter);
        }
        map.put(waitListPosition.getId(), waitListPosition);
        return waitListPosition;
    }

    @Override
    public List<WaitListPosition> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public WaitListPosition delete(WaitListPosition waitListPosition) {
        map.remove(waitListPosition.getId());
        return waitListPosition;
    }
}
