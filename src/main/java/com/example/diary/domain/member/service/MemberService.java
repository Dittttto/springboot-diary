package com.example.diary.domain.member.service;

import com.example.diary.domain.member.controller.request.MemberCreateRequestDTO;
import com.example.diary.domain.member.controller.request.MemberDeleteRequestDTO;
import com.example.diary.domain.member.controller.request.MemberLoginRequestDTO;
import com.example.diary.domain.member.controller.request.MemberUpdateRequestDTO;
import com.example.diary.domain.member.service.dto.MemberInfoDTO;

public interface MemberService {
    void register(MemberCreateRequestDTO dto);

    MemberInfoDTO getById(Long id);

    MemberInfoDTO update(MemberUpdateRequestDTO dto);

    void delete(MemberDeleteRequestDTO dto);

    String login(MemberLoginRequestDTO dto);
}
