package egovframework.client.shop.service;

import java.util.List;

public interface ShopManageService {

	//상품 리스트
	public List<ShopManageVo> selectShopList(ShopManageVo vo) throws Exception;

	//상품 리스트 카운트
	public int selectShopListCnt(ShopManageVo vo) throws Exception;
	
	//상품 상세
	public ShopManageVo selectShopView(ShopManageVo vo) throws Exception;
	
	//상품 상세_광고_등록
	public int insertShopADImage(ShopManageVo vo) throws Exception;
	
	//상품 상세_광고_수정
	public int updateShopADImage(ShopManageVo vo) throws Exception;
	
	//상품 상세(옵션)
	public List<ShopManageVo> selectShopOptionsView(ShopManageVo vo) throws Exception;
	
	//상품 img리스트
	public List<ShopManageVo> selectShopViewImg(ShopManageVo vo) throws Exception;
	
	//메인페이지 - 볼링볼 및 용품(new -> ebig = 1015 기준)
	public List<ShopManageVo> selectShopListNew() throws Exception;
	
	//메인페이지 - 볼링볼 및 용품(new -> ebig = 1015 기준) 옵션
	public List<ShopManageVo> selectShopListNewOptions() throws Exception;
	
	//메인페이지 - 볼링볼 및 용품(hot -> ebig = 1016 기준)
	public List<ShopManageVo> selectShopListHot() throws Exception;
	
	//메인페이지 - 볼링볼 및 용품(hot -> ebig = 1016 기준) 옵션
	public List<ShopManageVo> selectShopListHotOptions() throws Exception;
}
