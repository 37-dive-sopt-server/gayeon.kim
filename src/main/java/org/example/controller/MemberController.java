package org.example.controller;

import org.example.domain.Member;
import org.example.service.MemberService;
import org.example.service.MemberServiceImpl;

import java.util.List;
import java.util.Optional;

public class MemberController {

    private MemberService memberService = new MemberServiceImpl();

    public Long createMember(String name) {

        return memberService.join(name);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberService.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }
}
