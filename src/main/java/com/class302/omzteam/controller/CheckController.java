package com.class302.omzteam.controller;

import com.class302.omzteam.member.model.CheckDto;
import com.class302.omzteam.member.model.MemberDto;
import com.class302.omzteam.mybatis.MemberDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/check")
@Log4j2
public class CheckController {

    @Autowired
    MemberDao memberDao;

    @GetMapping
    public String check() {
        return "check/checkForm";
    }

    @PostMapping("/in")
    public ResponseEntity<Map<String, Object>> checkIn(@RequestBody Map<String, Long> requestBody, Model model, RedirectAttributes rattr) {
        Long no = requestBody.get("no");
        Long isLate = requestBody.get("isLate");

        LocalDate nowDate = LocalDate.now();

        if (memberDao.hasCheckedIn(no, nowDate) == 1) {
            Time getTime = memberDao.selectByInTime(no);
            log.info("------- get time : {}", getTime);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("inTime", getTime.toString());
            responseData.put("msg", "CHK_IN_ALD");
            return ResponseEntity.ok(responseData);
        }

        int rowCnt = memberDao.insertCheck(no, isLate);
        if (rowCnt == 1) {
            Time getTime = memberDao.selectByInTime(no);

            // 응답 데이터를 JSON 형식으로 생성
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("inTime", getTime.toString()); // 시간 데이터를 문자열로 변환해서 전달
            responseData.put("msg", "CHK_IN_OK");
            return ResponseEntity.ok(responseData);
        } else {
            rattr.addFlashAttribute("msg", "CHK_ERR");
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/out")
    public ResponseEntity<Map<String, Object>> checkOut(@RequestBody Map<String, Long> requestBody, Model model, RedirectAttributes rattr) {
        Long no = requestBody.get("no");

        LocalDate nowDate = LocalDate.now();

        if (memberDao.hasCheckedOut(no, nowDate) == 1) {
            Time getTime = memberDao.selectByOutTime(no);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("outTime", getTime.toString());
            responseData.put("msg", "CHK_OUT_ALD");
            return ResponseEntity.ok(responseData);
        }

        int rowCnt = memberDao.updateCheckOut(no, nowDate);
        if (rowCnt == 1) {
            Time getTime = memberDao.selectByOutTime(no);

            // 응답 데이터를 JSON 형식으로 생성
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("outTime", getTime.toString()); // 시간 데이터를 문자열로 변환해서 전달
            responseData.put("msg", "CHK_OUT_OK");
            return ResponseEntity.ok(responseData);
        } else {
            rattr.addFlashAttribute("msg", "CHK_ERR");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/calendar")
    public String checkCalendar() {
        Long no = 111L;
        LocalDate date = LocalDate.now();
        System.out.println("date: "+date);
        List<CheckDto> list = memberDao.myCheckList(no, date);
        for (CheckDto dto : list) {
            System.out.println("dto: "+dto);
        }
        return "check/checkForm";
    }


//    @PostMapping("/in")
//    public void checkIn(@RequestBody Map<String, Long> requestBody) {
//        // 1. dates 현재 날짜의 date_id 가져오기
//        Long dateId = memberDao.getDateId();
//        // 2-1. 등록 o - update / check_in
//        Long no = requestBody.get("no");
//        int rowCnt = memberDao.upCheckInOnEvents(no, dateId);
//        // 2-2. 등록 x - insert / member_id, check_in
//        // 3.
//    }
//
//    @PostMapping("/out")
//    public void checkIn(@RequestBody Map<String, Long> requestBody) {
//        // 1. dates 현재 날짜의 date_id 가져오기
//        Long dateId = memberDao.getDateId();
//        // 2-1. 등록 o - update / check_in
//        Long no = requestBody.get("no");
//        int rowCnt = memberDao.upCheckInOnEvents(no, dateId);
//        // 2-2. 등록 x - insert / member_id, check_in
//        // 3.
//    }
}

