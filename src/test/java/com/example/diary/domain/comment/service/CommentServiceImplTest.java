package com.example.diary.domain.comment.service;

import com.example.diary.domain.comment.dto.request.CommentCreateRequestDTO;
import com.example.diary.domain.comment.dto.request.CommentUpdateRequestDTO;
import com.example.diary.domain.comment.dto.service.CommentCreateDto;
import com.example.diary.domain.comment.dto.service.CommentInfoDTO;
import com.example.diary.domain.comment.repository.CommentRepository;
import com.example.diary.domain.fixture.TestMember;
import com.example.diary.domain.fixture.TestSchedule;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.mock.FakeCommentRepository;
import com.example.diary.domain.mock.FakeScheduleRepository;
import com.example.diary.domain.schedule.dto.service.ScheduleCreateDTO;
import com.example.diary.domain.schedule.repository.ScheduleRepository;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentServiceImplTest {
    private CommentService commentService;

    @BeforeEach
    void init() {
        ScheduleRepository scheduleRepository = new FakeScheduleRepository();
        ScheduleCreateDTO dto = new ScheduleCreateDTO(
                "test title",
                "test content",
                "test1234",
                false,
                null,
                new TestMember(),
                new TestMember()
        );

        CommentRepository commentRepository = new FakeCommentRepository();
        CommentCreateDto commentCreateDto = new CommentCreateDto(
                new TestMember(),
                new TestSchedule(),
                "test comment"
        );

        scheduleRepository.register(dto);
        commentRepository.register(commentCreateDto);
        commentService = new CommentServiceImpl(commentRepository, scheduleRepository);
    }

    @Test
    void register_메서드로_댓글을_생성할_수_있다() {
        //given
        CommentCreateRequestDTO dto = new CommentCreateRequestDTO(
                1L,
                "content",
                null
        );

        Member member = new TestMember();

        //when
        CommentInfoDTO infoDto = commentService.register(dto, member);

        //then
        assertThat(infoDto).isNotNull();
        assertThat(infoDto.getContent()).isEqualTo("content");
        assertThat(infoDto.getMemberInfoDTO().getId()).isEqualTo(member.getId());
    }

    @Test
    void register_메서드로_유효하지_않은_일정에_댓글을_생성시_예외를_발생시킨다() {
        //given
        CommentCreateRequestDTO dto = new CommentCreateRequestDTO(
                100L,
                "content",
                null
        );

        Member member = new TestMember();

        //when
        assertThatThrownBy(() -> commentService.register(dto, member))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    void register_메서드로_대댓글을_생성할_수_있다() {
        //given
        CommentCreateRequestDTO childCommentDto = new CommentCreateRequestDTO(
                1L,
                "content",
                1L
        );

        Member member = new TestMember();

        //when
        CommentInfoDTO register = commentService.register(childCommentDto, member);

        //given
        assertThat(register).isNotNull();
        assertThat(register.getChildComments()).hasSize(1);
    }

    @Test
    void findById_메서드로_단_건_조회를_할_수_있다() {
        //when
        CommentInfoDTO infoDto = commentService.findById(1L);

        //then
        assertThat(infoDto).isNotNull();
        assertThat(infoDto.getContent()).isEqualTo("test comment");
    }

    @Test
    void findById_메서드로_유효하지_않은_댓글을_조회하면_예외를_발생시킨다() {
        //when
        assertThatThrownBy(() -> commentService.findById(10L))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    void findAllByMemberId_메서드로_전체_조회를_할_수_있다() {
        //when
        List<CommentInfoDTO> allByMemberId = commentService.findAllByMemberId(1L);

        //then
        assertThat(allByMemberId).isNotNull();
        assertThat(allByMemberId.size()).isEqualTo(1);
    }

    @Test
    void update_메서드로_댓글을_수정할_수_있다() {
        //given
        CommentUpdateRequestDTO dto = new CommentUpdateRequestDTO(
                "hello",
                1L
        );

        Member member = new TestMember();

        //when
        CommentInfoDTO updated = commentService.update(
                1L,
                dto,
                member
        );

        //then
        assertThat(updated).isNotNull();
        assertThat(updated.getContent()).isEqualTo("hello");
    }

    @Test
    void update_메서드로_수정요청시_작성자가_아닌_경우_예외를_발생시킨다() {
        //given
        CommentUpdateRequestDTO dto = new CommentUpdateRequestDTO(
                "hello",
                1L
        );

        Member other = new TestMember("other@test.test");

        //when
        assertThatThrownBy(() -> commentService.update(1L, dto, other))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.UN_AUTHORIZATION_EXCEPTION.getMessage());
    }

    @Test
    void update_메서드로_수정요청시_댓글이_없는_경우_예외를_발생시킨다() {
        //given
        CommentUpdateRequestDTO dto = new CommentUpdateRequestDTO(
                "hello",
                1L
        );

        Member other = new TestMember();

        //when
        assertThatThrownBy(() -> commentService.update(100L, dto, other))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    void delete_메서드로_댓글을_삭제할_수_있다() {
        //given
        Member member = new TestMember();

        //when
        commentService.delete(1L, member);
    }

    @Test
    void delete_메서드로_삭제요청시_댓글이_없는_경우_예외를_발생시킨다() {
        //given
        Member other = new TestMember();

        //when
        assertThatThrownBy(() -> commentService.delete(100L, other))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    void delete_메서드로_삭제요청시_작성자가_아닌_경우_예외를_발생시킨다() {
        //given
        Member other = new TestMember("other@test.test");

        //when
        assertThatThrownBy(() -> commentService.delete(1L, other))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.UN_AUTHORIZATION_EXCEPTION.getMessage());
    }
}
