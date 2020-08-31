package ksmart36.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart36.mybatis.domain.Member;
import ksmart36.mybatis.mapper.MemberMapper;

@Service
@Transactional
public class MemberService {

	@Autowired 
	private MemberMapper memberMapper;
	
	//회원 로그인이력 조회
	public Map<String, Object>getLoginHistory(int currentPage){
		
		//보여줄 행의 갯수
		final int ROW_PER_PAGE = 10;	//상수를 표기할 때 대문자를 사용해 
										//스네이크표기법(언더바)으로 변수를 선언한다.
		//보여줄 행의 시작점
		int startRow = 0;
		
		//시작페이지번호, 끝페이지번호
		int startPageNum = 1;
		int lastPageNum = ROW_PER_PAGE;	
		
		//6번째부터 가운데 위치
		if(currentPage > (ROW_PER_PAGE/2)) {
			startPageNum = currentPage - (lastPageNum/2);
			lastPageNum += startPageNum;
		}
		
		
		//페이징 알고리즘
		startRow = (currentPage - 1) * ROW_PER_PAGE;
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("startRow", startRow);
		parameterMap.put("ROW_PER_PAGE", ROW_PER_PAGE);
		
		double totalRowCount = memberMapper.getLoginCount();
		
		int lastPage = (int) Math.ceil((totalRowCount / ROW_PER_PAGE));
		
		List<Map<String,Object>> loginHistory 
						= memberMapper.getLoginHistory(parameterMap);
		
		if(currentPage <= (lastPageNum-4)) {
			lastPageNum = lastPage;
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("loginHistory", loginHistory);
		resultMap.put("lastPage", lastPage);
		resultMap.put("startPageNum", startPageNum);
		resultMap.put("lastPageNum", lastPageNum);
		
		return resultMap;
	}
			
			
	//회원목록조건검색
	public List<Member> getSearchMemberList(String sk, String sv){
		//회원들의 정보가 담긴 List객체
		List<Member> memberList = memberMapper.getSearchMemberList(sk, sv);
		
		//List객체가 null이 아닌 경우 = 조회 성공
		if(memberList != null) {			
			for(int i=0; i<memberList.size(); i++) {
				//변수(회원레벨) 초기화
				int memberLevel = 0;
				memberLevel = memberList.get(i).getMemberLevel();
				//회원레벨이 담겨져 있는 경우 체크
				if(memberLevel > 0) {
					if(memberLevel == 1) {
						memberList.get(i).setMemberLevelName("관리자");
					}else if(memberLevel == 2) {
						memberList.get(i).setMemberLevelName("판매자");				
					}else if(memberLevel == 3) {
						memberList.get(i).setMemberLevelName("구매자");								
					}else {
						memberList.get(i).setMemberLevelName("일반회원");														
					}
				}
			}
		}
		
		return memberList;
	};
	
	//회원정보삭제
	public int removeMember(String memberId, String memberPw) {
		//delete성공 결과 변수 초기화
		int result = 0;
		
		Member member = memberMapper.getMemberById(memberId);
		
		if(member != null && memberPw != null && !"".equals(memberPw)) {
			if(memberPw.equals(member.getMemberPw())) {
				//1. 로그인 테이블(login_id) 삭제
				result += memberMapper.removeLogin(memberId);
				//2-1. 상품테이블(g_seller_id)통해 g_code조회 후
				List<Map<String, Object>> goodsCodeList = 
						memberMapper.getGoodsCodeById(memberId);
				//2-2. 주문테이블삭제
				result += memberMapper.removeOrder(goodsCodeList);
				//3. 상품테이블(g_seller_id)삭제
				result += memberMapper.removeGoods(memberId);
				//4. 멤버테이블삭제
				result += memberMapper.removeMember(memberId);
			}
		}
		
		return result;
	}
	
	//회원정보수정
	public int modifyMember(Member member) {
		int result = memberMapper.modifyMember(member);
		System.out.println("업데이트 결과값->" + result);
		return result;
	}
	
	
	//id로 회원정보 조회
	public Member getMemberById(String memberId) {
		Member member = memberMapper.getMemberById(memberId);
		
		//레벨에 따라 memberLevelName 값을 setter
		if(member != null) {
			
			int memberLevel = 0;
			memberLevel = member.getMemberLevel();
			
			if(memberLevel > 0) {
				if(memberLevel == 1) {
					member.setMemberLevelName("관리자");
				}else if(memberLevel == 2) {
					member.setMemberLevelName("판매자");
				}else if(memberLevel == 3) {
					member.setMemberLevelName("구매자");					
				}else {
					member.setMemberLevelName("일반회원");										
				}
			}
		}
		return member;
	}
	
	//회원정보 등록
	public int addMember(Member member) {
		int result = memberMapper.addMember(member);
		
		return result;
	}
	
	
	//회원정보 조회
	public List<Member> getMemberList(){
		//회원들의 정보가 담긴 List객체
		List<Member> memberList = memberMapper.getMemberList();
		
		//List객체가 null이 아닌 경우 = 조회 성공
		if(memberList != null) {			
			for(int i=0; i<memberList.size(); i++) {
				//변수(회원레벨) 초기화
				int memberLevel = 0;
				memberLevel = memberList.get(i).getMemberLevel();
				//회원레벨이 담겨져 있는 경우 체크
				if(memberLevel > 0) {
					if(memberLevel == 1) {
						memberList.get(i).setMemberLevelName("관리자");
					}else if(memberLevel == 2) {
						memberList.get(i).setMemberLevelName("판매자");				
					}else if(memberLevel == 3) {
						memberList.get(i).setMemberLevelName("구매자");								
					}else {
						memberList.get(i).setMemberLevelName("일반회원");														
					}
				}
			}
		}
		
		return memberList;
	}
}
