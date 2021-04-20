package egovframework.client.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.client.shop.service.impl.ShopManageDAO;
import egovframework.client.shop.service.ShopManageService;
import egovframework.client.shop.service.ShopManageVo;

@Repository("ShopManageService")
public class ShopManageServiceImpl implements ShopManageService {
	
private Logger logger	= Logger.getLogger(this.getClass());
	
	@Resource(name="ShopManageDAO")
	ShopManageDAO ShopManageDAO;

	//상품 리스트
	@Override
	public List<ShopManageVo> selectShopList(ShopManageVo vo) throws Exception {
		List<ShopManageVo> list = ShopManageDAO.selectShopList(vo);
		return list;
	}

	//상품 리스트 카운트
	@Override
	public int selectShopListCnt(ShopManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ShopManageDAO.selectShopListCnt(vo);
	}
	
	//상품 상세
	@Override
	public ShopManageVo selectShopView(ShopManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ShopManageDAO.selectShopView(vo);
	}
	
	//상품 상세_광고_등록
	@Override
	public int insertShopADImage(ShopManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ShopManageDAO.insertShopADImage(vo);
	}
	
	//상품 상세_광고_수정
	@Override
	public int updateShopADImage(ShopManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ShopManageDAO.updateShopADImage(vo);
	}
	
	//상품 상세(옵션)
	@Override
	public List<ShopManageVo> selectShopOptionsView(ShopManageVo vo) throws Exception {
		// TODO Auto-generated method stub
		return ShopManageDAO.selectShopOptionsView(vo);
	}
	
	//상품 img리스트
	@Override
	public List<ShopManageVo> selectShopViewImg(ShopManageVo vo) throws Exception {
		List<ShopManageVo> list = ShopManageDAO.selectShopViewImg(vo);
		return list;
	}
	
	//메인페이지 - 볼링볼 및 용품(new -> ebig = 1015 기준)
	@Override
	public List<ShopManageVo> selectShopListNew() throws Exception {
		List<ShopManageVo> list = ShopManageDAO.selectShopListNew();
		return list;
	}
	
	//메인페이지 - 볼링볼 및 용품(new -> ebig = 1015 기준) 옵션
	@Override
	public List<ShopManageVo> selectShopListNewOptions() throws Exception {
		List<ShopManageVo> list = ShopManageDAO.selectShopListNewOptions();
		return list;
	}
	
	//메인페이지 - 볼링볼 및 용품(hot -> ebig = 1016 기준)
	@Override
	public List<ShopManageVo> selectShopListHot() throws Exception {
		List<ShopManageVo> list = ShopManageDAO.selectShopListHot();
		return list;
	}
	
	//메인페이지 - 볼링볼 및 용품(hot -> ebig = 1016 기준) 옵션
	@Override
	public List<ShopManageVo> selectShopListHotOptions() throws Exception {
		List<ShopManageVo> list = ShopManageDAO.selectShopListHotOptions();
		return list;
	}
}
