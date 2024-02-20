package com.example.diary.domain.member.service;

import com.example.diary.domain.fixture.TestMember;
import com.example.diary.domain.member.dto.request.MemberCreateRequestDTO;
import com.example.diary.domain.member.dto.request.MemberDeleteRequestDTO;
import com.example.diary.domain.member.dto.request.MemberLoginRequestDTO;
import com.example.diary.domain.member.dto.request.MemberUpdateRequestDTO;
import com.example.diary.domain.member.dto.service.MemberInfoDTO;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.member.util.JwtUtil;
import com.example.diary.domain.mock.FakeMemberRepository;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceImplTest {
    private MemberService memberService;

    @BeforeEach
    void init() {
        MemberRepository memberRepository = new FakeMemberRepository();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        JwtUtil jwtUtil = new JwtUtil();
        memberService = new MemberServiceImpl(
                memberRepository,
                bCryptPasswordEncoder,
                jwtUtil
        );
    }

    @Test
    void register_메서드로_맴버를_생성할_수_있다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        //when
        MemberInfoDTO registered = memberService.register(dto);

        //then
        assertThat(registered).isNotNull();
        assertThat(registered.getEmail()).isEqualTo("test@test.test");
        assertThat(registered.getUsername()).isEqualTo("test");
    }

    @Test
    void register_메서드로_생성요청시_중복된_맴버가_있다면_예외를_발생시킨다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        memberService.register(dto);

        //when
        assertThatThrownBy(() -> memberService.register(dto))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.DUPLICATE_USER_EXCEPTION.getMessage());
    }

    @Test
    void getById_메서드로_유효하지_사용자를_조회시_예외를_발생시킨다() {
        //given
        Member member = new TestMember();

        //when
        assertThatThrownBy(() -> memberService.getById(100L, member))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    void getById_메서드로_요청시_타맴버의_정보를_조회시_예외를_발생시킨다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        memberService.register(dto);
        Member member = new TestMember("wron@test.test");

        //when
        assertThatThrownBy(() -> memberService.getById(1L, member))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.UN_AUTHORIZATION_EXCEPTION.getMessage());
    }

    @Test
    void getById_메서드로_요청시_정보를_조회할_수_있다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        memberService.register(dto);
        Member owner = new TestMember("test@test.test");

        //when
        MemberInfoDTO searched = memberService.getById(1L, owner);

        //given
        assertThat(searched.getEmail()).isEqualTo("test@test.test");
        assertThat(searched.getUsername()).isEqualTo("test");
    }

    @Test
    void update_메서드로_맴버정보를_수정할_수_있다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        MemberUpdateRequestDTO updateDto = new MemberUpdateRequestDTO(
                1L,
                "test",
                "test1234",
                true
        );

        memberService.register(dto);
        Member owner = new TestMember("test@test.test");

        //when
        MemberInfoDTO updated = memberService.update(1L, updateDto, owner);

        // then
        assertThat(updated).isNotNull();
        assertThat(updated.getEmail()).isEqualTo("test@test.test");
        assertThat(updated.getUsername()).isEqualTo("test");
        assertThat(updated.isAdmin()).isTrue();
    }

    @Test
    void update_메서드로_수정요청시_타맴버의_정보를_조회시_예외를_발생시킨다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        MemberUpdateRequestDTO updateDto = new MemberUpdateRequestDTO(
                1L,
                "test",
                "test1234",
                true
        );

        memberService.register(dto);
        Member member = new TestMember("wron@test.test");

        //when
        assertThatThrownBy(() -> memberService.update(1L, updateDto, member))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.UN_AUTHORIZATION_EXCEPTION.getMessage());
    }

    @Test
    void update_메서드로_유효하지_않은_맴버의_수정요청시_예외를_발생시킨다() {
        //given
        MemberUpdateRequestDTO updateDto = new MemberUpdateRequestDTO(
                1L,
                "test",
                "test1234",
                true
        );

        Member member = new TestMember("wron@test.test");

        //when
        assertThatThrownBy(() -> memberService.update(100L, updateDto, member))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    void update_메서드로_수정요청시_비밀번호가_틀리면_예외를_발생시킨다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        MemberUpdateRequestDTO updateDto = new MemberUpdateRequestDTO(
                1L,
                "test",
                "qwer1234",
                true
        );
        memberService.register(dto);
        Member owner = new TestMember("test@test.test");

        //when
        assertThatThrownBy(() -> memberService.update(1L, updateDto, owner))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.PASSWORD_INVALID_EXCEPTION.getMessage());
    }

    @Test
    void delete_메서드로_삭제요청시_맴버를_삭제할_수_있다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        MemberDeleteRequestDTO deleteDto = new MemberDeleteRequestDTO(
                1L,
                "test1234"
        );

        memberService.register(dto);
        Member owner = new TestMember("test@test.test");

        //when
        memberService.delete(1L, deleteDto, owner);
    }

    @Test
    void delete_메서드로_삭제요청시_비밀번호가_틀리면_예외를_발생시킨다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        MemberDeleteRequestDTO deleteDto = new MemberDeleteRequestDTO(
                1L,
                "test"
        );

        memberService.register(dto);
        Member owner = new TestMember("test@test.test");

        //when
        assertThatThrownBy(() -> memberService.delete(1L, deleteDto, owner))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.PASSWORD_INVALID_EXCEPTION.getMessage());
    }

    @Test
    void delete_메서드로_타맴버의_정보_삭제요청시_예외를_발생시킨다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        MemberDeleteRequestDTO deleteDto = new MemberDeleteRequestDTO(
                1L,
                "test"
        );

        memberService.register(dto);
        Member other = new TestMember("qwer@test.test");

        //when
        assertThatThrownBy(() -> memberService.delete(1L, deleteDto, other))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.UN_AUTHORIZATION_EXCEPTION.getMessage());
    }

    @Test
    void delete_메서드로_유효하지_않은_맴버의_삭제요청시_예외를_발생시킨다() {
        //given
        MemberDeleteRequestDTO deleteDto = new MemberDeleteRequestDTO(
                1L,
                "test"
        );

        Member other = new TestMember("qwer@test.test");

        //when
        assertThatThrownBy(() -> memberService.delete(100L, deleteDto, other))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    void login_메서드로_유요하지_않은_맴버를_요청시_예외를_발생시킨다() {
        //when
        MemberLoginRequestDTO loginDto = new MemberLoginRequestDTO(
                "test@test.test",
                "test1234"
        );

        assertThatThrownBy(() -> memberService.login(loginDto))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    void login_메서드로_요청시_비밀번호가_틀리면_예외를_발생시킨다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "qwer1234"
        );

        MemberLoginRequestDTO loginDto = new MemberLoginRequestDTO(
                "test@test.test",
                "test1234"
        );

        memberService.register(dto);

        assertThatThrownBy(() -> memberService.login(loginDto))
                .isInstanceOf(CustomException.class)
                .hasMessage("[ERROR] " + ErrorCode.PASSWORD_INVALID_EXCEPTION.getMessage());
    }

    @Test
    void login_메서드로_로그인_할_수_있다() {
        //given
        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
                "test@test.test",
                "test",
                "test1234"
        );

        MemberLoginRequestDTO loginDto = new MemberLoginRequestDTO(
                "test@test.test",
                "test1234"
        );

        memberService.register(dto);

        // TODO: JWT util 클래스를 개선해야 문제를 해결할 수 있다.
        // 1. Interface로 분리하기 -> 이미 util의 성격이 아니지 않은가?
//        String token = memberService.login(loginDto);
//        assertThat(token).isNotNull();
    }
}
