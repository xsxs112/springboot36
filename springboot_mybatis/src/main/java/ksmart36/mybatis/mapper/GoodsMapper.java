package ksmart36.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksmart36.mybatis.domain.Member;

@Mapper
public interface GoodsMapper {
	public List<Member> getGoodsList();
	
}
