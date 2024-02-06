package com.example.diary.domain.schedule.model;

import com.example.diary.domain.fixture.TestMember;
import com.example.diary.domain.fixture.TestSchedule;
import com.example.diary.domain.schedule.infrastructure.entity.ScheduleEntity;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ScheduleTest {
    @Test
    void form_메서드는_엔티티를_매개변수로_받아_Schedule_객체를_반환한다() {
        //given
        ScheduleEntity entity = new TestSchedule().toEntity();
        //when
        Schedule result = Schedule.from(entity);
        //then
        assertThat(result)
                .isNotNull()
                .isInstanceOf(Schedule.class);
    }

    @Test
    void toEntity_메서드는_객체를_엔티티_객체로_변환_후_반환한다() {
        //given
        TestSchedule schedule = new TestSchedule();
        //when
        ScheduleEntity result = schedule.toEntity();
        //then
        assertThat(result)
                .isNotNull()
                .isInstanceOf(ScheduleEntity.class);
        assertThat(result.getContent()).isEqualTo(schedule.getContent());
    }

    @Test
    void update_메서드는_주어진_매개변수를_적용한_새로운_Schedule_객체를_반환한다() {
        //given
        TestSchedule schedule = new TestSchedule();
        //when
        Schedule result = schedule.update(
                "변경된 제목",
                "변경된 내용",
                schedule.getPassword(),
                true,
                true,
                schedule.getOwner(),
                schedule.getAssignedMember()
        );
        //then
        assertThat(result.getTitle()).isEqualTo("변경된 제목");
        assertThat(result.getContent()).isEqualTo("변경된 내용");
        assertThat(result.getIsDone()).isTrue();
        assertThat(result.getIsPrivate()).isTrue();
    }

    @Test
    void hasAuthorization_메서드는_일정_작성자이고_권한이_있다면_true_를_반환한다() {
        //given
        TestMember owner = new TestMember("ditto@gmail.com");
        String password = "qwer1234";

        TestSchedule schedule = new TestSchedule(owner, password);
        //when
        boolean result = schedule.hasAuthorization(password, owner);
        //then
        assertThat(result).isTrue();
    }

    @Test
    void hasAuthorization_메서드는_일정_작성자가_아니라면_에외를_발생시킨다() {
        //given
        TestMember owner = new TestMember("ditto@gmail.com");
        TestMember newMember = new TestMember("modafi@gmail.com");
        String password = "qwer1234";

        TestSchedule schedule = new TestSchedule(owner, password);
        //when
        assertThatThrownBy(() ->
                schedule.hasAuthorization(password, newMember))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.UN_AUTHORIZATION_EXCEPTION.getMessage());
    }

    @Test
    void hasAuthorization_메서드는_일정_비밀번호가_다르면_에외를_발생시킨다() {
        //given
        TestMember owner = new TestMember("ditto@gmail.com");
        String password = "qwer1234";

        TestSchedule schedule = new TestSchedule(owner, password);
        //when
        assertThatThrownBy(() ->
                schedule.hasAuthorization("1234", owner))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.PASSWORD_INVALID_EXCEPTION.getMessage());
    }
}
