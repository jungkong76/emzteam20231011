package com.class302.omzteam;

import com.class302.omzteam.member.model.CheckDto;
import com.class302.omzteam.member.model.MemberDto;
import com.class302.omzteam.mybatis.MemberDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
class OmzteamApplicationTests {

    @Autowired
    MemberDao memberDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void getDateTime() {
        Time dt = memberDao.selectByInTime(111L);
        System.out.println(dt);
    }

    @Test
    public void getCheckDto() {
        CheckDto checkDto = memberDao.selectOne(111L);
        System.out.println("checkDto: "+checkDto);
    }
}
