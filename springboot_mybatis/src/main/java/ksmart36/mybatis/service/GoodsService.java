package ksmart36.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart36.mybatis.domain.Member;
import ksmart36.mybatis.mapper.GoodsMapper;

@Service
@Transactional
public class GoodsService {

	@Autowired private GoodsMapper goodsMapper;
	
	public List<Member> getGoodsList(){
		return goodsMapper.getGoodsList();
	}
}
