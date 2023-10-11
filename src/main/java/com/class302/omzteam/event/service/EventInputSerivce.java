package com.class302.omzteam.event.service;

import com.class302.omzteam.event.model.EventModel;
import com.class302.omzteam.mybatis.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

@Service
public class EventInputSerivce {

    @Autowired
    EventDao eventDao;

    @Transactional
    public boolean inputService(EventModel eventModel){


        if(eventModel.getDate() != null || eventModel.getEventDescription() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(eventModel.getDate());

            if(eventDao.SearchDate(formattedDate) != null){
                eventDao.insertDescription(eventDao.SearchDate(formattedDate).getDate_id(), eventModel.getEventDescription());
            }else{
                eventDao.insertDate(formattedDate);
                long lastDateId = eventDao.lastInsertDateId();
                eventDao.insertDescription(lastDateId, eventModel.getEventDescription());
            }

            return true;

        }

        return false;
    }








}
