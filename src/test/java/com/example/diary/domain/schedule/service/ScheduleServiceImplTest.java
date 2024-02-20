package com.example.diary.domain.schedule.service;

import com.example.diary.domain.mock.FakeMemberRepository;
import com.example.diary.domain.mock.FakeScheduleRepository;
import com.example.diary.domain.fixture.TestMember;
import com.example.diary.domain.member.dto.service.MemberCreateDTO;
import com.example.diary.domain.member.dto.service.MemberInfoDTO;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.schedule.dto.request.ScheduleCreateRequestDTO;
import com.example.diary.domain.schedule.dto.request.ScheduleDeleteRequestDTO;
import com.example.diary.domain.schedule.dto.request.ScheduleUpdateRequestDTO;
import com.example.diary.domain.schedule.dto.service.ScheduleInfoDTO;
import com.example.diary.domain.schedule.repository.ScheduleRepository;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ScheduleServiceImplTest {
    private ScheduleService scheduleService;

    @BeforeEach
    void init() {
        MemberRepository memberRepository = new FakeMemberRepository();
        ScheduleRepository scheduleRepository = new FakeScheduleRepository();

        MemberCreateDTO memberDto = new MemberCreateDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        memberRepository.register(memberDto);

        scheduleService = new ScheduleServiceImpl(
                scheduleRepository,
                memberRepository
        );
    }

    @Test
    void register_메서드로_새로운_일정을_생성할_수_있다() {
        //given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                null
        );

        Member member = new TestMember();

        //when
        assertDoesNotThrow(() -> scheduleService.register(dto, member));
    }

    @Test
    void register_메서드로_새로운_일정을_생성시_수행할_맴버를_지정할_수_있다() {
        //given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                1L
        );

        Member owner = new TestMember();

        // when
        ScheduleInfoDTO registered = scheduleService.register(dto, owner);

        // then
        assertThat(registered).isNotNull();
        assertThat(registered.getAssignedMember())
                .isNotNull()
                .isInstanceOf(MemberInfoDTO.class);
    }

    @Test
    void register_메서드로_새로운_일정을_생성시_수행할_맴버가_유효하지_않다면_예외를_발생시킨다() {
        //given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                100L
        );

        Member owner = new TestMember();

        // when
        Assertions.assertThatThrownBy(() -> scheduleService.register(dto, owner))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    void getSchedules_메서드로_모든_일정을_조회할_수_있다() {
        //given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                1L
        );

        Member owner = new TestMember();
        scheduleService.register(dto, owner);

        // when
        List<ScheduleInfoDTO> schedules = scheduleService.getSchedules();

        // then
        assertThat(schedules).hasSize(1);
        assertThat(schedules.get(0).getAssignedMember())
                .isNotNull()
                .isInstanceOf(MemberInfoDTO.class);
    }

    @Test
    void searchByTitle_메서드로_제목으로_일정을_검색할_수_있다() {
        //given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                1L
        );

        Member owner = new TestMember();
        scheduleService.register(dto, owner);

        // when
        List<ScheduleInfoDTO> schedules = scheduleService.searchByTitle("test");

        // then
        assertThat(schedules).hasSize(1);
        assertThat(schedules.get(0).getTitle()).isEqualTo(dto.title());
    }

    @Test
    void findAllByAssignedMember_메서드로_할당_인원별로_할_일을_조회할_수_있다() {
        //given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                1L
        );

        ScheduleCreateRequestDTO dto2 = new ScheduleCreateRequestDTO(
                "test title 2",
                "test content 2",
                "test1234",
                false,
                null
        );

        ScheduleCreateRequestDTO dto3 = new ScheduleCreateRequestDTO(
                "test title 3",
                "test content 3",
                "test1234",
                false,
                1L
        );

        Member owner = new TestMember();
        scheduleService.register(dto, owner);
        scheduleService.register(dto2, owner);
        scheduleService.register(dto3, owner);

        // when
        Map<Long, List<ScheduleInfoDTO>> allByAssignedMember =
                scheduleService.findAllByAssignedMember();

        // then
        assertThat(allByAssignedMember.keySet()).hasSize(2);
        assertThat(allByAssignedMember.get(1L)).hasSize(2);
        assertThat(allByAssignedMember.get(-1L)).hasSize(1);
    }

    @Test
    void getScheduleById_메서드로_단_건_조회를_할_수_있다() {
        //given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                1L
        );

        Member owner = new TestMember();
        scheduleService.register(dto, owner);

        // when
        ScheduleInfoDTO infoDTO = scheduleService.getScheduleById(1L);

        // then
        assertThat(infoDTO.getTitle()).isEqualTo(dto.title());
        assertThat(infoDTO.getContent()).isEqualTo(dto.content());
    }

    @Test
    void deleteById_메서드로_일정을_삭제할_수_있다() {
        // given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                1L
        );

        ScheduleDeleteRequestDTO deleteDto = new ScheduleDeleteRequestDTO(
                1L,
                "test1234"
        );

        Member owner = new TestMember();
        Member other = new TestMember("other@email.com");

        scheduleService.register(dto, owner);

        // when
        scheduleService.deleteById(deleteDto, owner);
    }

    @Test
    void deleteById_메서드로_삭제요청시_작성자가_아니면_예외를_발생시킨다() {
        // given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                1L
        );

        ScheduleDeleteRequestDTO deleteDto = new ScheduleDeleteRequestDTO(
                1L,
                "test1234"
        );

        Member owner = new TestMember();
        Member other = new TestMember("other@email.com");

        scheduleService.register(dto, owner);

        // when
        assertThatThrownBy(() -> scheduleService.deleteById(deleteDto, other))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.UN_AUTHORIZATION_EXCEPTION.getMessage());
    }

    @Test
    void deleteById_메서드로_삭제요청시_비밀번호가_다르면_예외를_발생시킨다() {
        // given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                1L
        );

        ScheduleDeleteRequestDTO deleteDto = new ScheduleDeleteRequestDTO(
                1L,
                "wrong password"
        );

        Member owner = new TestMember();

        scheduleService.register(dto, owner);

        // when
        assertThatThrownBy(() -> scheduleService.deleteById(deleteDto, owner))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.PASSWORD_INVALID_EXCEPTION.getMessage());
    }

    @Test
    void modifySchedule_메서드로_일정의_제목과_내용을_수정할_수_있다() {
        // given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                null
        );

        ScheduleUpdateRequestDTO updateDto = new ScheduleUpdateRequestDTO(
                1L,
                "Modified title",
                "modified content",
                "test1234",
                true,
                false,
                null
        );

        Member owner = new TestMember();

        scheduleService.register(dto, owner);

        // when
        ScheduleInfoDTO updatedDto = scheduleService.modifySchedule(updateDto, owner);

        assertThat(updatedDto.getTitle()).isEqualTo(updateDto.title());
        assertThat(updatedDto.getContent()).isEqualTo(updateDto.content());
    }

    @Test
    void modifySchedule_메서드로_일정의_수행_맴버를_수정할_수_있다() {
        // given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                null
        );

        ScheduleUpdateRequestDTO updateDto = new ScheduleUpdateRequestDTO(
                1L,
                "Modified title",
                "modified content",
                "test1234",
                true,
                false,
                1L
        );

        Member owner = new TestMember();

        scheduleService.register(dto, owner);

        // when
        ScheduleInfoDTO updatedDto = scheduleService.modifySchedule(updateDto, owner);

        assertThat(updatedDto.getTitle()).isEqualTo(updateDto.title());
        assertThat(updatedDto.getContent()).isEqualTo(updateDto.content());
        assertThat(updatedDto.getAssignedMember()).isNotNull();
    }

    @Test
    void modifySchedule_메서드로_일정의_수행_맴버가_유효하지_않으면_예외를_발생시킨다() {
        // given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                null
        );

        ScheduleUpdateRequestDTO updateDto = new ScheduleUpdateRequestDTO(
                1L,
                "Modified title",
                "modified content",
                "test1234",
                true,
                false,
                100L
        );

        Member owner = new TestMember();

        scheduleService.register(dto, owner);

        // when
        assertThatThrownBy(() -> scheduleService.modifySchedule(updateDto, owner))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    void modifySchedule_메서드로_수정요청시_비밀번호가_다르면_예외를_발생시킨다() {
        // given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                1L
        );


        ScheduleUpdateRequestDTO updateDto = new ScheduleUpdateRequestDTO(
                1L,
                "Modified title",
                "modified content",
                "qwer1234",
                true,
                false,
                1L
        );

        Member owner = new TestMember();

        scheduleService.register(dto, owner);

        // when
        assertThatThrownBy(() -> scheduleService.modifySchedule(updateDto, owner))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.PASSWORD_INVALID_EXCEPTION.getMessage());
    }

    @Test
    void modifySchedule_메서드로_수정요청시_작성자가_아니면_예외를_발생시킨다() {
        // given
        ScheduleCreateRequestDTO dto = new ScheduleCreateRequestDTO(
                "test title",
                "test content",
                "test1234",
                false,
                1L
        );


        ScheduleUpdateRequestDTO updateDto = new ScheduleUpdateRequestDTO(
                1L,
                "Modified title",
                "modified content",
                "qwer1234",
                true,
                false,
                1L
        );

        Member owner = new TestMember();
        Member other = new TestMember("other@email.com");

        scheduleService.register(dto, owner);

        // when
        assertThatThrownBy(() -> scheduleService.modifySchedule(updateDto, other))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.UN_AUTHORIZATION_EXCEPTION.getMessage());
    }
}
