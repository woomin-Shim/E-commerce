package com.toyproject.ecommerce.controller;

import com.toyproject.ecommerce.controller.DTO.LoginForm;
import com.toyproject.ecommerce.controller.DTO.MemberForm;
import com.toyproject.ecommerce.domain.Address;
import com.toyproject.ecommerce.domain.Member;
import com.toyproject.ecommerce.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @GetMapping("/members/new")
    public String createMemberForm(@ModelAttribute("memberForm") MemberForm memberForm, Model model) {
        List<RoleCode> roleCodes = new ArrayList<>();
        roleCodes.add(new RoleCode("admin", "판매자"));
        roleCodes.add(new RoleCode("user", "구매자"));
        model.addAttribute("roleCodes", roleCodes);

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Valid @ModelAttribute MemberForm memberForm, BindingResult bindingResult, Model model,
                               @RequestParam("role") String role, RedirectAttributes redirect) {

        //이름, 이메일, 패스워드 중 하나라도 입력을 안했을 시
        if (bindingResult.hasErrors()) {
            List<RoleCode> roleCodes = new ArrayList<>();
            roleCodes.add(new RoleCode("admin", "판매자"));
            roleCodes.add(new RoleCode("user", "구매자"));
            model.addAttribute("roleCodes", roleCodes);
            return "/members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        try {
            Member member = Member.builder()
                    .name(memberForm.getName())
                    .email(memberForm.getEmail())
                    .password(memberForm.getPassword())
                    .address(address)
                    .build();

            log.info("role={}", role);

            member.changeRole(role);  //사용자에게 권한 설정(판매자 또는 구매자)
            memberService.join(member);

        } catch (IllegalStateException e) {  //예외가 발생하면(회원 이메일이 중복)
            model.addAttribute("errorMessage", e.getMessage());
            return "members/createMemberForm";
        }

//        redirect.addAttribute("status", true);
        return "redirect:/members";
    }

    /**
     * 로그인
     */
    @GetMapping("/members")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/loginForm";
    }

    @PostMapping("/members")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {

        //이메일 또는 비밀번호를 누락시
        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "members/loginForm";
        }

        Member loginMember = memberService.login(form.getEmail(), form.getPassword());
        log.info("login? {}", loginMember);

        if (loginMember == null) {
            bindingResult.reject("loginfail", "이메일 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }

        /**
         * HttpSession 생성
         */
        HttpSession session = request.getSession();  //만약 세션이 있으면 기존 세션을 반환하고, 없으면 신규 세션 생성
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);  //세션에 회원 정보 보관

        //판매자, 구매자 역할 뷰 나누기 TODO
        return "redirect:/userHome";
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @Data
    @AllArgsConstructor
    static class RoleCode {
        private String code;
        private String displayName;
    }

}
