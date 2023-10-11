package com.class302.omzteam.mybatis;

import com.class302.omzteam.event.model.DateModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EventDao {

    @Insert("INSERT INTO dates (event_date) VALUES (#{eventDate, jdbcType=DATE})")
    void insertDate(@Param("eventDate") String eventDate);

    @Select("SELECT MAX(date_id) AS last_date_id FROM dates")
    long lastInsertDateId();
    @Select("Select * from dates where event_date = #{eventDate} ")
   DateModel SearchDate(@Param("eventDate") String eventDate);

    @Insert("INSERT INTO events (date_id, member_no, event_description) VALUES (#{dateId},100001 ,#{description})")
    void insertDescription(@Param("dateId")long dateId,
                           @Param("description")String description);


}
