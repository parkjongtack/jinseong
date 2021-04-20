package egovframework.client.shop.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.shop.service.ShopManageVo;
import egovframework.common.ShopAbstractDAO;

@Repository("ShopManageDAO")
public class ShopManageDAO extends ShopAbstractDAO {
	
	private Logger logger	= Logger.getLogger(this.getClass());

	//상품 리스트
	public List<ShopManageVo> selectShopList(ShopManageVo vo) {
		List<ShopManageVo> list	= list("selectShopList", vo);
		return list;
	}

	//상품 리스트 카운트
	public int selectShopListCnt(ShopManageVo vo) {
		int list	= (Integer)getSqlMapClientTemplate().queryForObject("selectShopListCnt", vo);		
		return list;
	}
	
	//상품 상세
	public ShopManageVo selectShopView(ShopManageVo vo) {
		// TODO Auto-generated method stub
		return (ShopManageVo)selectByPk("selectShopView", vo);
	}
	
	//상품 상세_광고_등록
	public int insertShopADImage(ShopManageVo vo) {
		return (Integer)getSqlMapClientTemplate().insert("insertShopADImage", vo);
	}
	
	//상품 상세_광고_수정
	public int updateShopADImage(ShopManageVo vo) {
		return (Integer)getSqlMapClientTemplate().update("updateShopADImage", vo);
	}
	
	//상품 상세(옵션)
	public List<ShopManageVo> selectShopOptionsView(ShopManageVo vo) {
		List<ShopManageVo> list	= list("selectShopOptionsView", vo);
		return list;
	}
	
	//상품 이미지 리스트
	public List<ShopManageVo> selectShopViewImg(ShopManageVo vo) {
		List<ShopManageVo> list	= list("selectShopViewImg", vo);
		return list;
	}
	
	//메인페이지 - 볼링볼 및 용품(new -> ebig = 1015 기준)
	public List<ShopManageVo> selectShopListNew() {
		List<ShopManageVo> list	= list("selectShopListNew");
		return list;
	}
	
	//메인페이지 - 볼링볼 및 용품(new -> ebig = 1015 기준) 옵션
	public List<ShopManageVo> selectShopListNewOptions() {
		List<ShopManageVo> list	= list("selectShopListNewOptions");
		return list;
	}
	
	//메인페이지 - 볼링볼 및 용품(hot -> ebig = 1016 기준)
	public List<ShopManageVo> selectShopListHot() {
		List<ShopManageVo> list	= list("selectShopListHot");
		return list;
	}
	
	//메인페이지 - 볼링볼 및 용품(hot -> ebig = 1016 기준) 옵션
	public List<ShopManageVo> selectShopListHotOptions() {
		List<ShopManageVo> list	= list("selectShopListHotOptions");
		return list;
	}
}
