package com.class302.omzteam.mybatis;

import com.class302.omzteam.member.model.CheckDto;
import com.class302.omzteam.member.model.MemberDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface MemberDao {
    @Select("select count(*) from member")
    Integer memCnt();

    @Select("select m.mem_name, d.dept from member m, dept d where mem_no = #{username} and m.dept_no = d.dept_no")
    MemberDto selectOneMem(String username);

    @Select("select dw_id, member_no, check_in, check_out, date from daily_workstatus " +
            "where member_no = #{no}")
    CheckDto selectOne(Long no);

    @Select("select dw_id, member_no, check_in, check_out, date from daily_workstatus " +
            "where member_no = #{no} and date = #{date}")
    List<CheckDto> myCheckList(Long no, LocalDate date);


    // 출, 퇴근 관련
    @Insert("INSERT INTO daily_workstatus " +
            "(member_no,check_in,Date,is_late) " +
            "VALUES " +
            "(#{no}, DATE_FORMAT(NOW(), '%H:%i:%s'), DATE_FORMAT(NOW(), '%Y-%m-%d'), #{isLate})")
    int insertCheck(Long no, Long isLate);

    @Select("select count(check_in) from daily_workstatus where member_no = #{no} and date = #{date}")
    int hasCheckedIn(Long no, LocalDate date);

    @Update("UPDATE daily_workstatus " +
            "SET check_out = DATE_FORMAT(NOW(), '%H:%i:%s') " +
            "WHERE member_no = #{no} and date = #{date}")
    int updateCheckOut(Long no, LocalDate date);

    @Select("select count(check_out) from daily_workstatus where member_no = #{no} and date = #{date}")
    int hasCheckedOut(Long no, LocalDate date);

    @Select("select check_in from daily_workstatus " +
            "where member_no = #{no} and date = DATE_FORMAT(NOW(), '%Y-%m-%d')")
    Time selectByInTime(Long no);

    @Select("select check_out from daily_workstatus " +
            "where member_no = #{no} and date = DATE_FORMAT(NOW(), '%Y-%m-%d')")
    Time selectByOutTime(Long no);



//    // 출, 퇴근 관련
//    @Select("SELECT date_id FROM dates WHERE event_date = CURDATE();")
//    Long getDateId();
//
//    @Update("UPDATE events " +
//            "SET check_in = DATE_FORMAT(NOW(), '%H:%i:%s') " +
//            "WHERE event_id = ( " +
//            "    SELECT MIN(sub.event_id) " +
//            "    FROM (" +
//            "        SELECT event_id " +
//            "        FROM events " +
//            "        WHERE date_id = #{dateId} AND member_no = #{no}) AS sub)")
//    int upCheckInOnEvents(Long no, Long dateId);
//
//    @Update("UPDATE events " +
//            "SET check_in = DATE_FORMAT(NOW(), '%H:%i:%s') " +
//            "WHERE event_id = ( " +
//            "    SELECT MIN(sub.event_id) " +
//            "    FROM (" +
//            "        SELECT event_id " +
//            "        FROM events " +
//            "        WHERE date_id = #{dateId} AND member_no = #{no}) AS sub)")
//    int upCheckOutOnEvents(Long no, Long dateId);
}
