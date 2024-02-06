package com.example.diary.domain.member.model;

import com.example.diary.domain.fixture.TestMember;
import com.example.diary.domain.member.infrastructure.entity.MemberEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    @Test
    void form_메서드는_엔티티를_매개변수로_받아_Member_객체를_반환한다() {
        //given
        TestMember testMember = new TestMember();

        //when
        Member result = Member.from(testMember.toEntity());

        //then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("ditto@gmail.com");
    }

    @Test
    void toEntity_메서드는_객체를_엔티티_객체로_변환_후_반환한다() {
        //given
        TestMember testMember = new TestMember();
        //when
        MemberEntity result = testMember.toEntity();
        //then
        assertThat(result).isNotNull().isInstanceOf(MemberEntity.class);
        assertThat(result.getEmail()).isEqualTo("ditto@gmail.com");
    }

    @Test
    void Member_객체를_email_을_기준으로_equals_를_비교한다() {
        //given
        TestMember testMember1 = new TestMember("ditto@gmail.com");
        TestMember testMember2 = new TestMember("ditto@gmail.com");
        //then
        assertThat(testMember1).isEqualTo(testMember2);
    }

    @Test
    void isSameEmail_메서드는_email_이_같은지_비교하고_같으면_true_를_반환한다() {
        //given
        TestMember testMember = new TestMember("ditto@gmail.com");
        //then
        assertThat(testMember.isSameEmail("ditto@gmail.com")).isTrue();
    }

    @Test
    void isSameEmail_메서드는_email_이_같은지_비교하고_다르면_false_를_반환한다() {
        //given
        TestMember testMember = new TestMember("ditto@gmail.com");
        //then
        assertThat(testMember.isSameEmail("modafi@gmail.com")).isFalse();
    }
}
