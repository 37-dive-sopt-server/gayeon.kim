package org.example.repository;

import org.example.domain.Member;

import java.util.*;

public class MemoryMemberRepository {


    private static final Map<Long, Member> store = new HashMap<>();


    public Member save(Member member) {

        store.put(member.getId(), member);
        return member;

    }


    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Member> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return store.values()
                .stream()
                .filter(m -> email.equals(m.getEmail()))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(Long memberId) {
        store.remove(memberId);
    }
}