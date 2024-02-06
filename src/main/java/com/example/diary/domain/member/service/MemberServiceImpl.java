package com.example.diary.domain.member.service;

import com.example.diary.domain.member.dto.request.MemberCreateRequestDTO;
import com.example.diary.domain.member.dto.request.MemberDeleteRequestDTO;
import com.example.diary.domain.member.dto.request.MemberLoginRequestDTO;
import com.example.diary.domain.member.dto.request.MemberUpdateRequestDTO;
import com.example.diary.domain.member.dto.service.MemberCreateDTO;
import com.example.diary.domain.member.dto.service.MemberInfoDTO;
import com.example.diary.domain.member.dto.service.MemberUpdateDTO;
import com.example.diary.domain.member.infrastructure.entity.MemberRole;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.member.util.JwtUtil;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void register(MemberCreateRequestDTO dto) {
        checkMemberAlreadyExist(dto);
        MemberCreateDTO memberCreateDTO = new MemberCreateDTO(
                dto.username(),
                dto.email(),
                passwordEncoder.encode(dto.password())
        );

        memberRepository.register(memberCreateDTO);
    }

    private void checkMemberAlreadyExist(MemberCreateRequestDTO dto) {
        Optional<Member> findMember = memberRepository.findByEmail(dto.email());
        if (findMember.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_EXCEPTION);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfoDTO getById(Long id, Member requestMember) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        if (!requestMember.equals(member)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        return MemberInfoDTO.from(member);
    }

    @Override
    public MemberInfoDTO update(Long id, MemberUpdateRequestDTO dto, Member requestMember) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));
        if (!requestMember.equals(member)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        Member update = member.update(
                dto.username(),
                dto.password(),
                dto.isAdmin(),
                passwordEncoder
        );

        Member updatedMember = memberRepository.updateById(id, MemberUpdateDTO.from(update));
        return MemberInfoDTO.from(updatedMember);
    }

    @Override
    public void delete(Long id, MemberDeleteRequestDTO dto, Member requestMember) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        if (!requestMember.equals(member)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        if (member.isPasswordNotMatch(dto.password(), passwordEncoder)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        memberRepository.deleteById(id);
    }

    @Override
    public String login(MemberLoginRequestDTO dto) {
        // TODO: 가입된 회원이 없는 경우는?
        Member member = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        if (member.isPasswordNotMatch(dto.password(), passwordEncoder)) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID_EXCEPTION);
        }

        return jwtUtil.generateToken(dto.email(), MemberRole.DEFAULT);
    }
}
