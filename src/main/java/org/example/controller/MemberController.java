package org.example.controller;

import org.example.domain.Gender;
import org.example.domain.Member;
import org.example.service.MemberService;
import org.example.service.MemberServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberController {

    private MemberService memberService = new MemberServiceImpl();

    /**

     컨트롤러의 회원 생성 메서드가 생년월일/이메일/성별을 전달
     */
    public Long createMember(String name, LocalDate birthDate, String email, Gender gender) {

        return memberService.join(name, birthDate, email, gender);
    }


    public Optional<Member> findMemberById(Long id) {
        return memberService.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    public void delete(Long memberId) {
        memberService.delete(memberId);
    }
}
