package com.example.diary.domain.member.service;

import com.example.diary.domain.member.controller.request.MemberCreateRequestDTO;
import com.example.diary.domain.member.controller.request.MemberDeleteRequestDTO;
import com.example.diary.domain.member.controller.request.MemberLoginRequestDTO;
import com.example.diary.domain.member.controller.request.MemberUpdateRequestDTO;
import com.example.diary.domain.member.infrastructure.entity.MemberRole;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.member.service.dto.MemberCreateDTO;
import com.example.diary.domain.member.service.dto.MemberInfoDTO;
import com.example.diary.domain.member.service.dto.MemberUpdateDTO;
import com.example.diary.domain.member.util.JwtUtil;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void register(MemberCreateRequestDTO dto) {
        Optional<Member> findMember = memberRepository.findByEmail(dto.email());
        if (findMember.isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_USER_EXCEPTION);
        }

        MemberCreateDTO memberCreateDTO = new MemberCreateDTO(
                dto.username(),
                dto.email(),
                passwordEncoder.encode(dto.password())
        );

        memberRepository.register(memberCreateDTO);
    }

    @Override
    public MemberInfoDTO getById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        return MemberInfoDTO.from(member);
    }

    @Override
    @Transactional
    public MemberInfoDTO update(MemberUpdateRequestDTO dto) {
        checkPassword(dto.id(), dto.password());

        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO(dto.username(), dto.password(), dto.isAdmin());
        Member updatedMember = memberRepository.updateById(dto.id(), memberUpdateDTO);

        return MemberInfoDTO.from(updatedMember);
    }

    @Override
    public void delete(MemberDeleteRequestDTO dto) {
        checkPassword(dto.id(), dto.password());
        memberRepository.deleteById(dto.id());
    }

    // TODO 중복 코드
    private void checkPassword(Long dto, String dto1) {
        Member member = memberRepository.findById(dto)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        if (member.isPasswordMatch(dto1, passwordEncoder)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }
    }

    @Override
    public String login(MemberLoginRequestDTO dto) {
        // TODO: 가입된 회원이 없는 경우는?
        Member member = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        if (!member.isPasswordMatch(dto.password(), passwordEncoder)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        return jwtUtil.generateToken(dto.email(), MemberRole.DEFAULT);
    }
}
