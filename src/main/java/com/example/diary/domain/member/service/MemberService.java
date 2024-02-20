package com.example.diary.domain.member.service;

import com.example.diary.domain.member.dto.request.MemberCreateRequestDTO;
import com.example.diary.domain.member.dto.request.MemberDeleteRequestDTO;
import com.example.diary.domain.member.dto.request.MemberLoginRequestDTO;
import com.example.diary.domain.member.dto.request.MemberUpdateRequestDTO;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.dto.service.MemberInfoDTO;

public interface MemberService {
    MemberInfoDTO register(MemberCreateRequestDTO dto);

    MemberInfoDTO getById(Long id, Member member);

    MemberInfoDTO update(Long id, MemberUpdateRequestDTO dto, Member member);

    void delete(Long id, MemberDeleteRequestDTO dto, Member member);

    String login(MemberLoginRequestDTO dto);
}
