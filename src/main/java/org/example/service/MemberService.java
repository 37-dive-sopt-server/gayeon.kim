package org.example.service;

import org.example.domain.Gender;
import org.example.domain.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Long join(String name, LocalDate birthDate, String email, Gender gender);
    Optional<Member> findOne(Long memberId);
    List<Member> findAllMembers();
    void delete(Long memberId);
}
