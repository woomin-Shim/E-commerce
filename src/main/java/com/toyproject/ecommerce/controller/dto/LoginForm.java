package com.toyproject.ecommerce.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

    @NotEmpty(message = "이메일 주소는 필수입니다.")
    @Email  //이메일 형식 validation
    private String email;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;
}
