package com.example.diary.domain.member.repository;

import com.example.diary.domain.member.dto.service.MemberCreateDTO;
import com.example.diary.domain.member.dto.service.MemberUpdateDTO;
import com.example.diary.domain.member.infrastructure.MemberJpaRepository;
import com.example.diary.domain.member.infrastructure.entity.MemberEntity;
import com.example.diary.domain.member.model.Member;
import com.example.diary.global.exception.CustomException;
import com.example.diary.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;


    @Override
    public Member register(MemberCreateDTO dto) {
        MemberEntity memberEntity = new MemberEntity(
                dto.getEmail(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getRole()
        );

        MemberEntity saved = memberJpaRepository.save(memberEntity);
        return Member.from(saved);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id).map(Member::from);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email).map(Member::from);
    }

    @Override
    public Member updateById(Long id, MemberUpdateDTO dto) {
        // TODO: 예외 메시지의 중앙화가 필요하다
        MemberEntity entity = memberJpaRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION,
                        String.format("%s는 존재하지 않습니다.", dto)));

        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getUsername());
        entity.setRole(dto.getRole());

        memberJpaRepository.saveAndFlush(entity);
        return Member.from(entity);
    }

    @Override
    public void deleteById(Long id) {
        memberJpaRepository.deleteById(id);
    }
}
