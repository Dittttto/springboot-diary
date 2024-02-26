package com.example.diary.domain.comment.model;

import com.example.diary.domain.comment.infrastructure.entity.CommentEntity;
import com.example.diary.domain.fixture.TestComment;
import com.example.diary.domain.fixture.TestMember;
import com.example.diary.domain.fixture.TestSchedule;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.schedule.model.Schedule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentTest {
    @Test
    void from_메서드는_엔티티를_매개변수로_받아_Comment_객체를_반환한다() {
        //given
        Member member = new TestMember();
        Schedule schedule = new TestSchedule();

        String content = "new comment";
        CommentEntity commentEntity = CommentEntity.of(
                schedule.toEntity(),
                member.toEntity(),
                content
        );

        //when
        Comment comment = Comment.from(commentEntity);

        //then
        assertThat(comment).isNull();
        assertThat(comment).isInstanceOf(Comment.class);
        assertThat(comment.getScheduleId()).isEqualTo(schedule.getId());
        assertThat(comment.getMemberInfo().getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void isNotOwner_메서드는_댓글의_작성자가_아니라면_true_를_반환한다() {
        //given
        Member member = new TestMember("modafi@gmail.com");
        TestComment testComment = new TestComment();
        System.out.println(member.getEmail());
        System.out.println(testComment.getMemberInfo().getEmail());
        //when
        boolean result = testComment.isNotOwner(member);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void isNotOwner_메서드는_댓글의_작성자라면_false_를_반환한다() {
        //given
        Member member = new TestMember();
        TestComment testComment = new TestComment();
        System.out.println(member.getEmail());
        System.out.println(testComment.getMemberInfo().getEmail());
        //when
        boolean result = testComment.isNotOwner(member);

        //then
        assertThat(result).isFalse();
    }
}
