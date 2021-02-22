package com.example.zagabi.web;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class MemberForm {
	@NotEmpty(message = "회원 이름은 필수 입니다") private String name;
	private String city;
	private String street;
	private String zipcode;
}
