package com.example.diary.domain.fake;

import com.example.diary.domain.member.dto.service.MemberCreateDTO;
import com.example.diary.domain.member.dto.service.MemberUpdateDTO;
import com.example.diary.domain.member.model.Member;
import com.example.diary.domain.member.repository.MemberRepository;

import java.util.Map;
import java.util.Optional;

public class FakeMemberRepository implements MemberRepository {
    private Member member;

    public FakeMemberRepository() {}

    public FakeMemberRepository(Member member) {
        this.member = member;
    }

    @Override
    public void register(MemberCreateDTO dto) {
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.of(member);
    }

    @Override
    public Member updateById(Long id, MemberUpdateDTO dto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
