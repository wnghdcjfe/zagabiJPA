package com.example.zagabi.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.zagabi.domain.Member;
import com.example.zagabi.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
	private final MemberService memberService;
	@PostMapping("/api/v1/members")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}
	@Data
	static class CreateMemberRequest{
		private String name;
	}
	@Data
	class CreateMemberResponse{
		private Long id;
		public CreateMemberResponse(Long id){
			this.id = id;
		}
	}
	@GetMapping("/api/v2/members")
	public Result memberV2(){
		List<Member> findMembers = memberService.findMembers();
		List<MemberDto> collect = findMembers.stream()
			.map(m -> new MemberDto(m.getName()))
			.collect(Collectors.toList());
		return new Result(collect);
	}
	@GetMapping("/api/v2/memberstest")
	public Result memberV3(){
		List<Member> findMembers = memberService.findMembers();
		return new Result(findMembers);
	}
	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
		Member member = new Member();
		member.setName(request.getName());
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}
	@PutMapping("/api/v2/members/{id}")
	public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request){
		memberService.update(id, request.getName());
		Member findMember = memberService.findOne(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName());
	}
	@Data
	static class UpdateMemberRequest {
		private String name;
	}
	@Data
	@AllArgsConstructor
	class UpdateMemberResponse{
		private Long id;
		private String name;
	}

	@Data
	@AllArgsConstructor
	class Result<T>{
		private T data;
	}
	@Data
	@AllArgsConstructor
	class MemberDto{
		private String name;
	}


}
