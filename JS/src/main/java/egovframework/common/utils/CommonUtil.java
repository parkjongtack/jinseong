package egovframework.common.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service("CommonUtil")
public class CommonUtil {

	public static String baseUrl = "";
	public static final int __PAGECNT = 10;
	public static final int __NAVCNT = 10;
	/**
	 * 페이징 Nav 반환
	 *
	 * @param vo
	 * @return
	 */
	public String getPageNav(HttpServletRequest request, PageVo vo) {

		StringBuilder sb = new StringBuilder();

		String reqCurrRow = request.getParameter("currRow");
		int currPage = 1;
		if (reqCurrRow != null && !"".equals(reqCurrRow)) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		vo.setCurrPage(currPage);
		vo = setPaging(request, vo);
		// TO-DO
		if (vo.getTotalRowCnt() > 0) {
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('1');\"><img src=\"" + baseUrl + "/images/sub/btn_first.gif\" alt=\"처음\"></a></span>\n");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + (vo.getCurrPage() - vo.getCntPerNav() <= 1 ? 1 : (vo.getCurrPage() - vo.getCntPerNav())) + "');\" class=\"prev\"><img src=\"" + baseUrl + "/images/sub/btn_prev.gif\" alt=\"이전\"></a></span>\n");
			sb.append("<ul class=\"paging\">");
			for (int i = vo.getStartPageGrp(); i <= vo.getEndPageGrp(); i++)
				sb.append("<li><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + i + "');\" " + (vo.getCurrPage() == i ? "style=\"font-weight:bold;\"" : "") + ">" + i + "</a></li>\n");
			sb.append("</ul>");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + (vo.getCurrPage() + vo.getCntPerNav() >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + vo.getCntPerNav())) + "');\" class=\"next\"><img src=\"" + baseUrl + "/images/sub/btn_next.gif\" alt=\"다음\"></a></span>\n");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + vo.getPageCnt() + "');\" class=\"nextEnd\"><img src=\"" + baseUrl + "/images/sub/btn_last.gif\" alt=\"끝\"></a></span>");
		}

		return sb.toString();
	}
	
	
	
	/**
	 * 페이징 Nav 반환
	 *
	 * @param vo
	 * @return
	 */
	public String getFtaPageNav(HttpServletRequest request, PageVo vo) {

		StringBuilder sb = new StringBuilder();

		String reqCurrRow = request.getParameter("currRow");
		int currPage = 1;
		if (reqCurrRow != null && !"".equals(reqCurrRow)) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		vo.setCurrPage(currPage);
		vo = setPaging(request, vo);
		// TO-DO
		if (vo.getTotalRowCnt() > 0) {
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('1');\" class=\"page_prev_btn\">&lt;&lt;</a></span>\n");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + (vo.getCurrPage() - vo.getCntPerNav() <= 1 ? 1 : (vo.getCurrPage() - vo.getCntPerNav())) + "');\" class=\"page_prev_btn\">&lt;</a></span>\n");
			for (int i = vo.getStartPageGrp(); i <= vo.getEndPageGrp(); i++)
				sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + i + "');\" " + (vo.getCurrPage() == i ? "class=\"on\"" : "") + ">" + i + "</a></span>");			
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + (vo.getCurrPage() + vo.getCntPerNav() >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + vo.getCntPerNav())) + "');\" class=\"page_next_btn\">&gt;</a></span>\n");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + vo.getPageCnt() + "');\" class=\"page_next_btn\">&gt;&gt;</a></span>");
		}

		return sb.toString();
	}
	
	/**
	 * admin 페이징 Nav 반환
	 *
	 * @param vo
	 * @return
	 */
	public String getadminPageNav(HttpServletRequest request, PageVo vo) {

		StringBuilder sb = new StringBuilder();

		String reqCurrRow = request.getParameter("currRow");
		int currPage = 1;
		if (reqCurrRow != null && !"".equals(reqCurrRow)  && Integer.parseInt(reqCurrRow) > 0) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		vo.setCurrPage(currPage);
		vo = setPaging(request, vo);
		// TO-DO
		if (vo.getTotalRowCnt() > 0) {
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('1');\">First</a></span>\n");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + (vo.getCurrPage() - vo.getCntPerNav() <= 1 ? 1 : (vo.getCurrPage() - vo.getCntPerNav())) + "');\" class=\"prev\">&#60;</a></span>\n");
			sb.append("<ul class=\"paging\">");
			for (int i = vo.getStartPageGrp(); i <= vo.getEndPageGrp(); i++)
				sb.append("<li><a href=\"javascript:void(0);\" id=\"aaa\"  onclick=\"javascript:pagingUtil.pageSubmit('" + i + "');\" " + (vo.getCurrPage() == i ?  "class=\"on\"" : "") + ">" + i + "</a></li>\n");
			sb.append("</ul>");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + (vo.getCurrPage() + vo.getCntPerNav() >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + vo.getCntPerNav())) + "');\" class=\"next\">&#62;</a></span>\n");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + vo.getPageCnt() + "');\" class=\"nextEnd\">Last</a></span>");
		}

		return sb.toString();
	}
	/**
	 * admin 페이징 Nav 반환
	 *
	 * @param vo
	 * @return
	 */
	public String getadminPageNav2(HttpServletRequest request, PageVo vo) {

		StringBuilder sb = new StringBuilder();

		String reqCurrRow = request.getParameter("currRow2");
		int currPage = 1;
		if (reqCurrRow != null && !"".equals(reqCurrRow)) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		vo.setCurrPage(currPage);
		vo = setPaging(request, vo);
		// TO-DO
		if (vo.getTotalRowCnt() > 0) {
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil2.pageSubmit('1');\">First</a></span>\n");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil2.pageSubmit('" + (vo.getCurrPage() - vo.getCntPerNav() <= 1 ? 1 : (vo.getCurrPage() - vo.getCntPerNav())) + "');\" class=\"prev\">&#60;</a></span>\n");
			sb.append("<ul class=\"paging\">");
			for (int i = vo.getStartPageGrp(); i <= vo.getEndPageGrp(); i++)
				sb.append("<li><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil2.pageSubmit('" + i + "');\" " + (vo.getCurrPage() == i ?  "class=\"on\"" : "") + ">" + i + "</a></li>\n");
			sb.append("</ul>");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil2.pageSubmit('" + (vo.getCurrPage() + vo.getCntPerNav() >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + vo.getCntPerNav())) + "');\" class=\"next\">&#62;</a></span>\n");
			sb.append("<span><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil2.pageSubmit('" + vo.getPageCnt() + "');\" class=\"nextEnd\">Last</a></span>");
		}

		return sb.toString();
	}
	
	/**
	 * client 페이징 Nav 반환
	 *
	 * @param vo
	 * @return
	 */
	public String getclientPageNav(HttpServletRequest request, PageVo vo) {

		StringBuilder sb = new StringBuilder(); 

		String reqCurrRow = request.getParameter("currRow");
		int currPage = 1;
		if (reqCurrRow != null && !"".equals(reqCurrRow) && Integer.parseInt(reqCurrRow) > 0) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		vo.setCurrPage(currPage);
		vo = setPaging(request, vo);

		if (vo.getTotalRowCnt() > 0) {
			sb.append("<ul>");
			sb.append("<li class=\"f_prev\"><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('1');\"><span class=\"sp_ico\">처음페이지</span></a></li>");
			sb.append("<li class=\"prev\"><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + (vo.getCurrPage() - vo.getCntPerNav() <= 1 ? 1 : (vo.getCurrPage() - vo.getCntPerNav())) + "');\"><span class=\"sp_ico\">이전</span></a></li>");
			for (int i = vo.getStartPageGrp(); i <= vo.getEndPageGrp(); i++){
				sb.append("<li><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + i + "');\" " + (vo.getCurrPage() == i ?  "class=\"on\"" : "") + ">" + i + "</a></li>\n");	
			}			
			sb.append("<li class=\"next\"><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + (vo.getCurrPage() + vo.getCntPerNav() >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + vo.getCntPerNav())) + "');\"><span class=\"sp_ico\">다음</span></a></li>");
			sb.append("<li class=\"l_next\"><a href=\"javascript:void(0);\" onclick=\"javascript:pagingUtil.pageSubmit('" + vo.getPageCnt() + "');\"><span class=\"sp_ico\">마지막페이지</span></a></li>");
			sb.append("</ul>");
		}

		return sb.toString();
	}
	/**
	 * 페이징 Nav 반환
	 *
	 * @param vo
	 * @return
	 */
	public String getPageNavJson(HttpServletRequest request, PageVo vo) {

		StringBuilder sb = new StringBuilder();

		String reqCurrRow = request.getParameter("currRow");
		int currPage = 1;
		if (reqCurrRow != null && !"".equals(reqCurrRow)) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		vo.setCurrPage(currPage);
		vo = setPaging(request, vo);
		// TO-DO
		if (vo.getTotalRowCnt() > 0) {
			sb.append("<a href=\"javascript:void(0);\" data-page=\"" + (vo.getCurrPage() - vo.getCntPerNav() <= 1 ? 1 : (vo.getCurrPage() - vo.getCntPerNav())) + "\"><img src=\"" + baseUrl + "/images/btn/btn_paging_first.gif\" alt=\"처음\"></a>");
			sb.append("<a href=\"javascript:void(0);\" data-page=\"" + (vo.getCurrPage() - 1 <= 1 ? 1 : (vo.getCurrPage() - 1)) + "\" class=\"prev\"><img src=\"" + baseUrl + "/images/btn/btn_paging_pre.gif\" alt=\"이전\"></a>");
			
			for (int i = vo.getStartPageGrp(); i <= vo.getEndPageGrp(); i++)
				sb.append("<a href=\"javascript:void(0);\" data-page=\"" + i + "\"" + (vo.getCurrPage() == i ? "style=\"font-weight:bold;\"" : "") + ">" + i + "</a>\n");

			sb.append("<a href=\"javascript:void(0);\" data-page=\"" + (vo.getCurrPage() + 1 >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + 1)) + "\" class=\"next\"><img src=\"" + baseUrl + "/images/btn/btn_paging_next.gif\" alt=\"다음\"></a>\n");
			sb.append("<a href=\"javascript:void(0);\" data-page=\"" + (vo.getCurrPage() + vo.getCntPerNav() >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + vo.getCntPerNav())) + "\" class=\"nextEnd\"><img src=\"" + baseUrl + "/images/btn/btn_paging_end.gif\" alt=\"끝\"></a>");
		}

		return sb.toString();
	}
	
	/**
	 * 페이징 Nav 반환
	 *
	 * @param vo
	 * @return
	 */
	public String getPageNav(PageVo vo, String currRow) {

		StringBuilder sb = new StringBuilder();

		String reqCurrRow = currRow;
		int currPage = 1;
		if (reqCurrRow != null && !"".equals(reqCurrRow)) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		vo.setCurrPage(currPage);
		vo = setPaging(vo);
		// TO-DO
		if (vo.getTotalRowCnt() > 0) {
			sb.append("<a href=\"javascript:paging('1');\"><img src=\"" + baseUrl + "/images/btn/btn_paging_first.gif\" alt=\"처음\"></a>");
			sb.append("<a href=\"javascript:paging('" + (vo.getCurrPage() - vo.getCntPerNav() <= 1 ? 1 : (vo.getCurrPage() - vo.getCntPerNav())) + "');\" class=\"prev\"><img src=\"" + baseUrl + "/images/btn/btn_paging_pre.gif\" alt=\"이전\"></a>");

			for (int i = vo.getStartPageGrp(); i <= vo.getEndPageGrp(); i++)
				sb.append("<a href=\"javascript:paging('" + i + "');\" " + (vo.getCurrPage() == i ? "style=\"font-weight:bold;\"" : "") + ">" + i + "</a>\n");

			sb.append("<a href=\"javascript:paging('" + (vo.getCurrPage() + vo.getCntPerNav() >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + vo.getCntPerNav())) + "');\" class=\"next\"><img src=\"" + baseUrl + "/images/btn/btn_paging_next.gif\" alt=\"다음\"></a>\n");
			sb.append("<a href=\"javascript:paging('" + vo.getPageCnt() + "');\" class=\"nextEnd\"><img src=\"" + baseUrl + "/images/btn/btn_paging_end.gif\" alt=\"끝\"></a>");
		}

		return sb.toString();
	}

	/**
	 * 페이징 Nav 반환 2
	 *
	 * @param vo
	 * @return
	 */
	public String getPageNav2(HttpServletRequest request, PageVo vo) {

		StringBuilder sb = new StringBuilder();

		String reqCurrRow = request.getParameter("currRow2");
		int currPage = 1;
		if (reqCurrRow != null && !"".equals(reqCurrRow)) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		vo.setCurrPage(currPage);
		vo = setPaging(request, vo);
		// TO-DO
		if (vo.getTotalRowCnt() > 0) {
			sb.append("<a href=\"javascript:void(0);\" data-nopaging=\"0\" class=\"pagefrm\" currpage='" + 1 + "' onclick=\"javascript:pagingUtil2.pageSubmit('1');\"><img src=\"" + baseUrl + "/images/btn/btn_paging_first.gif\" alt=\"처음\"></a>");
			sb.append("<a href=\"javascript:void(0);\" data-nopaging=\"0\" class=\"pagefrm\" currpage='" + (vo.getCurrPage() - vo.getCntPerNav() <= 1 ? 1 : (vo.getCurrPage() - vo.getCntPerNav())) + "' onclick=\"javascript:pagingUtil2.pageSubmit('" + (vo.getCurrPage() - vo.getCntPerNav() <= 1 ? 1 : (vo.getCurrPage() - vo.getCntPerNav())) + "');\" class=\"prev\"><img src=\"" + baseUrl
					+ "/images/btn/btn_paging_pre.gif\" alt=\"이전\"></a>");

			for (int i = vo.getStartPageGrp(); i <= vo.getEndPageGrp(); i++)
				sb.append("<a href=\"javascript:void(0);\" data-nopaging=\"0\" class=\"pagefrm\" currpage='" + i + "' onclick=\"javascript:pagingUtil2.pageSubmit('" + i + "');\" " + (vo.getCurrPage() == i ? "style=\"font-weight:bold;\"" : "") + ">" + i + "</a>\n");

			sb.append("<a href=\"javascript:void(0);\" data-nopaging=\"0\" class=\"pagefrm\" currpage='" + (vo.getCurrPage() + vo.getCntPerNav() >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + vo.getCntPerNav())) + "' onclick=\"javascript:pagingUtil2.pageSubmit('" + (vo.getCurrPage() + vo.getCntPerNav() >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + vo.getCntPerNav()))
					+ "');\" class=\"next\"><img src=\"" + baseUrl + "/images/btn/btn_paging_next.gif\" alt=\"다음\"></a>\n");
			sb.append("<a href=\"javascript:void(0);\" data-nopaging=\"0\" class=\"pagefrm\" currpage='" + vo.getPageCnt() + "' onclick=\"javascript:pagingUtil2.pageSubmit('" + vo.getPageCnt() + "');\" class=\"nextEnd\"><img src=\"" + baseUrl + "/images/btn/btn_paging_end.gif\" alt=\"끝\"></a>");
		}

		return sb.toString();
	}

	/**
	 * 수강자 페이징 Nav 반환
	 *
	 * @param vo
	 * @return
	 */
	public String getFrontPageNav(HttpServletRequest request, PageVo vo) {

		StringBuilder sb = new StringBuilder();

		String reqCurrRow = request.getParameter("currRow");
		int currPage = 1;
		if (reqCurrRow != null && !"".equals(reqCurrRow)) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		vo.setCurrPage(currPage);
		vo = setPaging(request, vo);
		// TO-DO
		if (vo.getTotalRowCnt() > 0) {
			sb.append("<a href=\"#\" class='img' onclick=\"javascript:pagingUtil.pageSubmit('1');\"><img src=\"" + baseUrl + "/images/btn/btn_paging_first.gif\" alt=\"처음\"></a>");
			sb.append("<a href=\"#\" class='prev img' onclick=\"javascript:pagingUtil.pageSubmit('" + (vo.getCurrPage() - vo.getCntPerNav() <= 1 ? 1 : (vo.getCurrPage() - vo.getCntPerNav())) + "');\" ><img src=\"" + baseUrl + "/images/btn/btn_paging_pre.gif\" alt=\"이전\"></a>");

			for (int i = vo.getStartPageGrp(); i <= vo.getEndPageGrp(); i++)
				sb.append("<a href=\"#\" onclick=\"javascript:pagingUtil.pageSubmit('" + i + "');\" " + (vo.getCurrPage() == i ? "class=\"current\"" : "") + ">" + i + "</a>\n");

			sb.append("<a href=\"#\" class=\"next img\" onclick=\"javascript:pagingUtil.pageSubmit('" + (vo.getCurrPage() + vo.getCntPerNav() >= vo.getPageCnt() ? vo.getPageCnt() : (vo.getCurrPage() + vo.getCntPerNav())) + "');\" ><img src=\"" + baseUrl + "/images/btn/btn_paging_next.gif\" alt=\"다음\"></a>\n");
			sb.append("<a href=\"#\" class='img' onclick=\"javascript:pagingUtil.pageSubmit('" + vo.getPageCnt() + "');\"><img src=\"" + baseUrl + "/images/btn/btn_paging_end.gif\" alt=\"끝\"></a>");
		}

		return sb.toString();
	}

	/**
	 * 페이징 처리
	 *
	 * @param vo
	 * @return
	 */
	public PageVo setPaging(HttpServletRequest request, PageVo vo) {

		int totalRowCnt = vo.getTotalRowCnt(); // 총개시물갯수
		int cntPerPage = vo.getCntPerPage(); // 한화면에 보여줄 갯수
		int cntPerNav = vo.getCntPerNav(); // 한화면에 보여줄 navigation갯수

		int currPage = vo.getCurrPage(); // 현재페이지
		int pageCnt = (int) Math.ceil((double) totalRowCnt / cntPerPage); // 총페이지갯수
		int pageGroupNum = (int) Math.ceil((double) currPage / cntPerNav); // 페이지그룹번호

		int startPageGrp = (pageGroupNum - 1) * cntPerNav + 1;
		int endPageGrp = startPageGrp + cntPerNav - 1;
		if (endPageGrp > pageCnt) {
			endPageGrp = pageCnt;
		}
		int startPageNum = (currPage * cntPerPage - (cntPerPage));
		int endPageNum = currPage * cntPerPage - 1;

		// TO-DO,
		vo.setPageCnt(pageCnt);
		vo.setPageGroupNum(pageGroupNum);
		vo.setStartPageGrp(startPageGrp);
		vo.setEndPageGrp(endPageGrp);
		vo.setStartPageNum(startPageNum);
		vo.setEndPageNum(endPageNum);

		return vo;
	}

	public PageVo setPaging(PageVo vo) {

		int totalRowCnt = vo.getTotalRowCnt(); // 총개시물갯수
		int cntPerPage = vo.getCntPerPage(); // 한화면에 보여줄 갯수
		int cntPerNav = vo.getCntPerNav(); // 한화면에 보여줄 navigation갯수

		int currPage = vo.getCurrPage(); // 현재페이지
		int pageCnt = (int) Math.ceil((double) totalRowCnt / cntPerPage); // 총페이지갯수
		int pageGroupNum = (int) Math.ceil((double) currPage / cntPerNav); // 페이지그룹번호

		int startPageGrp = (pageGroupNum - 1) * cntPerNav + 1;
		int endPageGrp = startPageGrp + cntPerNav - 1;
		if (endPageGrp > pageCnt) {
			endPageGrp = pageCnt;
		}
		int startPageNum = (currPage * cntPerPage - (cntPerPage));
		int endPageNum = currPage * cntPerPage - 1;

		// TO-DO,
		vo.setPageCnt(pageCnt);
		vo.setPageGroupNum(pageGroupNum);
		vo.setStartPageGrp(startPageGrp);
		vo.setEndPageGrp(endPageGrp);
		vo.setStartPageNum(startPageNum);
		vo.setEndPageNum(endPageNum);

		return vo;
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	public int getStartNum(HttpServletRequest request, PageVo vo) {

		String reqCurrRow = request.getParameter("currRow");
		int currPage = 1;

		if (reqCurrRow != null && !"".equals(reqCurrRow) && Integer.parseInt(reqCurrRow) > 0) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		int endpageNum = currPage * vo.getCntPerPage();
		int startPageNum = endpageNum - (vo.getCntPerPage() - 1);

		return startPageNum;
	}

	public int getStartNum(String currRow, PageVo vo) {

		String reqCurrRow = currRow;
		int currPage = 1;

		if (reqCurrRow != null && !"".equals(reqCurrRow)) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		int endpageNum = currPage * vo.getCntPerPage();
		int startPageNum = endpageNum - (vo.getCntPerPage() - 1);

		return startPageNum;
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	public int getStartNum2(HttpServletRequest request, PageVo vo) {

		String reqCurrRow = request.getParameter("currRow2");
		int currPage = 1;

		if (reqCurrRow != null && !"".equals(reqCurrRow)) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		int endpageNum = currPage * vo.getCntPerPage();
		int startPageNum = endpageNum - (vo.getCntPerPage() - 1);

		return startPageNum;
	}

	/**
	 * EndRow
	 *
	 * @param pVo
	 * @param startNum
	 * @return
	 */
	public int getEndNum(PageVo pVo, int startNum) {

		int endPageNum = 1;
		endPageNum = startNum + pVo.getCntPerPage() - 1;

		return endPageNum;
	}

	/**
	 * EndRow
	 *
	 * @param pVo
	 * @param startNum
	 * @return
	 */
	public int getEndNum2(PageVo pVo, int startNum) {

		int endPageNum = 1;
		endPageNum = startNum + pVo.getCntPerPage() - 1;

		return endPageNum;
	}
	
	 /**
     * XSS 방지 처리.
     * 
     * @param data
     * @return
     */
    public String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }
        
        String ret = data;
        
        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
        
        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
        
        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
        
        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        
        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }
    
    /**
     * 페이징 현재 페이지/총 페이지
     * 
     * @param request,vo
     * @return map
     */
    public Map<String,Integer> getPagingInfo(HttpServletRequest request, PageVo vo) {

		String reqCurrRow = request.getParameter("currRow");
		int currPage = 1;
		if (reqCurrRow != null && !"".equals(reqCurrRow)) {
			currPage = Integer.parseInt(reqCurrRow);
		}

		vo.setCurrPage(currPage);
		vo = setPaging(request, vo);
		// TO-DO
		Map<String,Integer> map = new HashMap<String, Integer>();
		if (vo.getTotalRowCnt() > 0) {
			map.put("currPageInfo", vo.getCurrPage());
			map.put("pageCntInfo", vo.getPageCnt());
		}else{
			map.put("currPageInfo", 0);
			map.put("pageCntInfo", 0);
		}
		
		return map;
	}
    
}
