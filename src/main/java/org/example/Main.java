package org.example;

import org.example.controller.MemberController;
import org.example.domain.Gender;
import org.example.domain.Member;
import org.example.repository.MemoryMemberRepository;
import org.example.service.MemberServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        MemberController memberController = new MemberController();

        Scanner scanner = new Scanner(System.in);

        /**
         * 생년월일 입력을 위한 날짜 포맷(yyyy-MM-dd) 추가
         */
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true) {
            System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
            System.out.println("---------------------------------");
            System.out.println("1️⃣. 회원 등록 ➕");
            System.out.println("2️⃣. ID로 회원 조회 🔍");
            System.out.println("3️⃣. 전체 회원 조회 📋");
            System.out.println("4️⃣. 회원삭제 📋");
            System.out.println("5️⃣. 종료 🚪");
            System.out.println("---------------------------------");
            System.out.print("메뉴를 선택하세요: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("등록할 회원 이름을 입력하세요: ");
                    String name = scanner.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("⚠️ 이름을 입력해주세요.");
                        continue;
                    }
                    /**
                     * 신규 입력: 생년월일(yyyy-MM-dd)
                     */
                    LocalDate birthDate;
                    while (true) {
                        System.out.print("생년월일을 입력하세요 (yyyy-MM-dd): ");
                        String birthInput = scanner.nextLine();
                        try {
                            birthDate = LocalDate.parse(birthInput, dateFormatter);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("❌ 형식이 올바르지 않습니다. 예) 1990-04-25");
                        }
                    }
                    /**
                     * 신규 입력: 이메일
                     *
                     * aa@aa.aa
                     */
                    System.out.print("이메일을 입력하세요: ");
                    String email = scanner.nextLine();

                    if (email.trim().isEmpty()) {
                        System.out.println("⚠️ 이메일을 입력해주세요.");
                    }


                    /**
                     * 신규 입력: 성별(M/F -> Gender enum 매핑)
                     */
                    Gender gender;
                    while (true) {
                        System.out.print("성별을 입력하세요 (M/F): ");
                        String g = scanner.nextLine().trim().toUpperCase();
                        if (g.equals("M") || g.equals("MALE")) {
                            gender = Gender.MALE;
                            break;
                        } else if (g.equals("F") || g.equals("FEMALE")) {
                            gender = Gender.FEMALE;
                            break;
                        } else {
                            System.out.println("❌ 올바른 성별을 입력해주세요. (M/F)");
                        }
                    }

                    try {
                        Long createdId = memberController.createMember(name, birthDate, email, gender);
                        if (createdId != null) {
                            System.out.println("✅ 회원 등록 완료 (ID: " + createdId + ")");
                        } else {
                            /**
                             * 일반 실패 케이스(방어적)
                             */
                            System.out.println("❌ 회원 등록 실패");
                        }
                    } catch (IllegalStateException e) {
                        /**
                         * 이메일 중복 등 서비스 레벨에서의 검증 실패 메시지 출력
                         */
                        System.out.println("❌ " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.print("조회할 회원 ID를 입력하세요: ");
                    try {
                        Long id = Long.parseLong(scanner.nextLine());
                        Optional<Member> foundMember = memberController.findMemberById(id);
                        if (foundMember.isPresent()) {
                            Member m = foundMember.get();
                            /**
                             * 조회 출력에 생년월일/이메일/성별을 추가 표시
                             */
                            System.out.println(
                                    "✅ 조회된 회원: ID=" + m.getId() +
                                            ", 이름=" + m.getName() +
                                            ", 생년월일=" + m.getBirthDate() +
                                            ", 이메일=" + m.getEmail() +
                                            ", 성별=" + m.getGender()
                            );
                        } else {
                            System.out.println("⚠️ 해당 ID의 회원을 찾을 수 없습니다.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ 유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
                    }
                    break;

                case "3":
                    List<Member> allMembers = memberController.getAllMembers();
                    if (allMembers.isEmpty()) {
                        System.out.println("ℹ️ 등록된 회원이 없습니다.");
                    }
                    else {
                        System.out.println("--- 📋 전체 회원 목록 📋 ---");
                        for (Member member : allMembers) {
                            /**
                             * 목록 출력에도 신규 필드(생년월일/이메일/성별) 표시
                             */
                            System.out.println(
                                    "👤 ID=" + member.getId() +
                                            ", 이름=" + member.getName() +
                                            ", 생년월일=" + member.getBirthDate() +
                                            ", 이메일=" + member.getEmail() +
                                            ", 성별=" + member.getGender()
                            );
                        }
                        System.out.println("--------------------------");
                    }
                    break;

                case "4":
                    System.out.println("👋 회원삭제 할 ID를 넣어라: ");
                    Scanner sc= new Scanner(System.in);

                    Long memberId = Long.valueOf(sc.nextLine());
                    memberController.delete(memberId);

                    System.out.println("👋 회원삭제 됐다 이자식아 ㅋㅋ");
                    break;

                case "5":
                    System.out.println("👋 서비스를 종료합니다. 안녕히 계세요!");
                    scanner.close();
                    return;

                default:
                    System.out.println("🚫 잘못된 메뉴 선택입니다. 다시 시도해주세요.");
            }
        }
    }

}
