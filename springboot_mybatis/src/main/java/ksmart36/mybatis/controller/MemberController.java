package ksmart36.mybatis.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart36.mybatis.domain.Member;
import ksmart36.mybatis.mapper.MemberMapper;
import ksmart36.mybatis.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberMapper memberMapper;
	
	@GetMapping("/getLoginHistory")
	public String getLoginHistory(Model model
								,@RequestParam(value="currentPage", required = false, defaultValue = "1") int currentPage) {
		Map<String, Object> resultMap
			=memberService.getLoginHistory(currentPage);
		System.out.println("login 이력 "+resultMap.get("loginHistory").toString());
		
		model.addAttribute("title","로그인 이력조회");
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("loginHistory", resultMap.get("loginHistory"));
		model.addAttribute("startPageNum", resultMap.get("startPageNum"));
		model.addAttribute("lastPageNum", resultMap.get("lastPageNum"));
		
		return "login/loginHistory";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String login(Model model
			,@RequestParam(value="memberId", required = false) String memberId
			,@RequestParam(value="memberPw", required = false) String memberPw
			,HttpSession session) {
		
		Member member = memberMapper.getMemberById(memberId);
		if(member != null) {
			if(member.getMemberPw().equals(memberPw)) {
				session.setAttribute("SID", 	member.getMemberId());
				session.setAttribute("SNAME", 	member.getMemberName());
				session.setAttribute("SLEVEL", 	member.getMemberLevel());
				return "redirect:/";
			}
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("title", "로그인화면");
		return "login/login";
	}
	
	@PostMapping("/getMemberList")
	public String getMemberList(Model model
								,@RequestParam(value="sk", required = false) String sk
								,@RequestParam(value="sv", required = false) String sv) {
		
		List<Member> memberList = memberService.getSearchMemberList(sk, sv);
		System.out.println("memberList-> " + memberList);
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("title", "회원 목록 조회");
		
		return "member/memberList";
	}
	
	@GetMapping("/removeMember")
	public String removeMember(Member member, Model model) {
		if(member.getMemberId() != null && !"".equals(member.getMemberId())){
			model.addAttribute("title", "회원삭제화면");
			model.addAttribute("memberId", member.getMemberId());
		}
		return "member/removeMember";
	}
	
	@PostMapping("/removeMember")
	public String removeMember(Member member) {
		if(	  member.getMemberId() != null && !"".equals(member.getMemberId())
		   && member.getMemberPw() != null && !"".equals(member.getMemberPw())){
			memberService.removeMember(member.getMemberId(), member.getMemberPw());
		}
		return "redirect:/getMemberList";
	}
	
	@PostMapping("/modifyMember")
	public String modifyMember(Member member) {
		System.out.println("회원수정정보->" + member);
		memberService.modifyMember(member);
		return "redirect:/getMemberList";
	}
	
	@GetMapping("/modifyMember")
	public String modifyMember(@RequestParam(value="memberId", required = false) String memberId
							  ,@RequestParam(value="memberPw", required = false) String memberPw
							  ,Model model) {
		System.out.println("memberId->"+memberId);
		System.out.println("memberPw->"+memberPw);
		Member member = memberService.getMemberById(memberId);
		model.addAttribute("member", member);
		model.addAttribute("title", "회원정보조회");
		return "member/modifyMember";
	}
	
	
	@PostMapping("/addMember")
	public String addMember(Member member
						   ,@RequestParam(value="memberId", required = false) String memberId
						   ,@RequestParam(value="memberPw", required = false) String memberPw
						   ,@RequestParam(value="memberName", required = false) String memberName
						   ,@RequestParam(value="memberEmail", required = false) String memberEmail
						   ,@RequestParam(value="memberAddr", required = false) String memberAddr) {
		System.out.println("회원등록정보 -> " + member);
		System.out.println("회원아이디->" + memberId);
		System.out.println("회원비밀번호->" + memberPw);
		System.out.println("회원이름->" + memberName);
		System.out.println("회원이메일->" + memberEmail);
		System.out.println("회원주소->" + memberAddr);
		//memberService.addMember(member);
		return "redirect:/getMemberList";
	}
	
	@GetMapping("/addMember")
	public String addMember(Model model) {
		model.addAttribute("title", "회원등록");
		return "member/addMember";
	}
	
	
	//회원목록조회
	//@GetMapping("/getMemberList") 
	@RequestMapping(value="/getMemberList", method = RequestMethod.GET)
	public String getMemeberList(Model model) {
		
		List<Member> memberList = memberService.getMemberList();
		System.out.println("memberList-> " + memberList);
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("title", "회원 목록 조회");
		//model.addAttribute("memberList", memberService.getMemberList());
		
		return "member/memberList";
	}
}
