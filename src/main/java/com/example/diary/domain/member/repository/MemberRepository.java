package com.example.diary.domain.member.repository;

import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.service.dto.MemberCreateDTO;
import com.example.diary.domain.member.service.dto.MemberUpdateDTO;

import java.util.Optional;

public interface MemberRepository {
    void register(MemberCreateDTO dto);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
    Member updateById(Long id, MemberUpdateDTO dto);
    void deleteById(Long id);
}
