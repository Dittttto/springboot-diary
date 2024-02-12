//package com.example.diary.domain.member.service;
//
//import com.example.diary.domain.fake.FakeMemberRepository;
//import com.example.diary.domain.fixture.TestMember;
//import com.example.diary.domain.member.dto.request.MemberCreateRequestDTO;
//import com.example.diary.domain.member.repository.MemberRepository;
//import com.example.diary.global.jwt.JwtProvider;
//import com.example.diary.global.exception.CustomException;
//import com.example.diary.global.exception.ErrorCode;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//class MemberServiceImplTest {
//    private MemberRepository memberRepository;
//    private MemberServiceImpl memberService;
//
//    @BeforeEach
//    void init() {
////        TestMember testMember = new TestMember("ditto@gmail.com");
////        memberRepository = new FakeMemberRepository(testMember);
////        memberService = new MemberServiceImpl(
////                memberRepository,
////                new BCryptPasswordEncoder(),
////                new JwtProvider()
////        );
//    }
//
//    @Test
//    void register_메서드는_회원을_등록할_수_있다() {
//        //given
//        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
//                "modafi@gmail.com",
//                "modafi",
//                "Qwer12345678"
//        );
//
//        //when
//        memberService.register(dto);
//        //then
//    }
//
//    @Test
//    void register_메서드는_이미_등록된_회원이_있는_경우_예외를_발생시킨다() {
//        //given
//        MemberCreateRequestDTO dto = new MemberCreateRequestDTO(
//                "ditto@gmail.com",
//                "ditto",
//                "Qwer12345678"
//        );
//
//        //when
//        Assertions.assertThatThrownBy(() ->
//                memberService.register(dto))
//                .isInstanceOf(CustomException.class)
//                .hasMessage("[ERROR] " + ErrorCode.DUPLICATE_USER_EXCEPTION);
//        //then
//    }
//}
