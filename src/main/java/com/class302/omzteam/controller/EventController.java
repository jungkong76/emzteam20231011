package com.class302.omzteam.controller;

import com.class302.omzteam.event.model.EventModel;
import com.class302.omzteam.event.service.EventInputSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/calendarEvent")
public class EventController {

    @Autowired
    EventInputSerivce eventInputSerivce;
    @GetMapping("/eventInputForm")
    public void eventInput(){
    }

    @PostMapping("/eventInputForm")
    @ResponseBody
    public boolean newEventInput(@RequestBody EventModel eventModel) {

        return eventInputSerivce.inputService(eventModel);
    }

}
