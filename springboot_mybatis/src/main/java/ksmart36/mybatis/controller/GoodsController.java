package ksmart36.mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ksmart36.mybatis.domain.Goods;
import ksmart36.mybatis.domain.Member;
import ksmart36.mybatis.service.GoodsService;

@Controller
public class GoodsController {

	@Autowired private GoodsService goodsService;
	
	@GetMapping("/goodsList")
	public String getGoodsList(Model model) {
		model.addAttribute("title", "상품목록조회");
		List<Member> goodsListByMember = goodsService.getGoodsList();
		System.out.println(goodsListByMember);
		model.addAttribute("mGoodsList", goodsListByMember);
		return "goods/goodsList";
	}
	@GetMapping("/addGoods")
	public String addGoods(Model model) {
		model.addAttribute("title", "상품등록");
		return "goods/addGoods";
	}
	@PostMapping("/addGoods")
	public String addGoods(Model model, Goods goods) {
		model.addAttribute("title", "상품등록");
		return "goods/addGoods";
	}
}
