package org.example.service;

import org.example.domain.Gender;
import org.example.domain.Member;
import org.example.repository.MemoryMemberRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    private MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    private static long sequence = 1L;

    /**

    회원 생성 시 생년월일/이메일/성별을 함께 저장
*/
    public Long join(String name, LocalDate birthDate, String email, Gender gender) {

        /**

        이메일 중복 방지: 공백 제거 및 소문자 정규화 후 중복 여부 확인
                */
                String normalizedEmail = email == null ? null : email.trim().toLowerCase();

        if (memberRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new IllegalStateException("이미 사용 중인 이메일입니다.");
        }

        Member member = new Member(sequence++, name, birthDate, normalizedEmail, gender);
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void delete(Long memberId) {
        memberRepository.delete(memberId);
    }
}