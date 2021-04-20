package egovframework.common;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapClient;


public class ShopAbstractDAO extends SqlMapClientDaoSupport{

	/**
     * EgovAbstractDAO 는 base class 로만 사용되며 해당 인스턴스를 직접
     * 생성할 수 없도록 protected constructor 로 선언하였음.
     */
    protected ShopAbstractDAO() {
    }

    /**
     * Annotation 형식으로 sqlMapClient 를 받아와 이를
     * super(SqlMapClientDaoSupport) 의 setSqlMapClient
     * 메서드를 호출하여 설정해 준다.
     * @param sqlMapClient
     *        - ibatis 의 SQL Map 과의 상호작용을 위한 기본 클래스로
     *        mapped statements(select, insert, update,
     *        delete 등) 의 실행을 지원함.
     */
    @Resource(name = "sqlMapClientShop")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }

    /**
     * 입력 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 입력 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 입력 처리 SQL mapping 입력 데이터를 세팅한 파라메터
     *        객체(보통 VO 또는 Map)
     * @return 입력 시 selectKey 를 사용하여 key 를 딴 경우 해당 key
     */
    public Object insert(String queryId, Object parameterObject) {
        return getSqlMapClientTemplate().insert(queryId, parameterObject);
    }

    /**
     * 수정 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 수정 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 수정 처리 SQL mapping 입력 데이터(key 조건 및 변경
     *        데이터)를 세팅한 파라메터 객체(보통 VO 또는 Map)
     * @return DBMS가 지원하는 경우 update 적용 결과 count
     */
    public int update(String queryId, Object parameterObject) {
        return getSqlMapClientTemplate().update(queryId, parameterObject);
    }

    /**
     * 삭제 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 삭제 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 삭제 처리 SQL mapping 입력 데이터(일반적으로 key 조건)를
     *        세팅한 파라메터 객체(보통 VO 또는 Map)
     * @return DBMS가 지원하는 경우 delete 적용 결과 count
     */
    public int delete(String queryId, Object parameterObject) {
        return getSqlMapClientTemplate().delete(queryId, parameterObject);
    }

    /**
     * pk 를 조건으로 한 단건조회 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 단건 조회 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 단건 조회 처리 SQL mapping 입력 데이터(key)를 세팅한
     *        파라메터 객체(보통 VO 또는 Map)
     * @return 결과 객체 - SQL mapping 파일에서 지정한
     *         resultClass/resultMap 에 의한 단일 결과 객체(보통
     *         VO 또는 Map)
     */
    public Object selectByPk(String queryId, Object parameterObject) {
        return getSqlMapClientTemplate().queryForObject(queryId,
            parameterObject);
    }

    /**
     * 리스트 조회 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 리스트 조회 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 리스트 조회 처리 SQL mapping 입력 데이터(조회 조건)를
     *        세팅한 파라메터 객체(보통 VO 또는 Map)
     * @return 결과 List 객체 - SQL mapping 파일에서 지정한
     *         resultClass/resultMap 에 의한 결과 객체(보통 VO
     *         또는 Map)의 List
     */
    @SuppressWarnings("rawtypes")
	public List list(String queryId, Object parameterObject) {
        return getSqlMapClientTemplate().queryForList(queryId, parameterObject);
    }

    /**
     * 리스트 조회 처리 SQL mapping 을 실행한다.
     * @param queryId
     *        - 리스트 조회 처리 SQL mapping 쿼리 ID
     * @return 결과 List 객체 - SQL mapping 파일에서 지정한
     *         resultClass/resultMap 에 의한 결과 객체(보통 VO
     *         또는 Map)의 List
     */
    @SuppressWarnings("rawtypes")
	public List list(String queryId) {
        return getSqlMapClientTemplate().queryForList(queryId);
    }

    /**
     * 부분 범위 리스트 조회 처리 SQL mapping 을 실행한다. (부분 범위 -
     * pageIndex 와 pageSize 기반으로 현재 부분 범위 조회를 위한
     * skipResults, maxResults 를 계산하여 ibatis 호출)
     * @param queryId
     *        - 리스트 조회 처리 SQL mapping 쿼리 ID
     * @param parameterObject
     *        - 리스트 조회 처리 SQL mapping 입력 데이터(조회 조건)를
     *        세팅한 파라메터 객체(보통 VO 또는 Map)
     * @param pageIndex
     *        - 현재 페이지 번호
     * @param pageSize
     *        - 한 페이지 조회 수(pageSize)
     * @return 부분 범위 결과 List 객체 - SQL mapping 파일에서 지정한
     *         resultClass/resultMap 에 의한 부분 범위 결과
     *         객체(보통 VO 또는 Map) List
     */
    @SuppressWarnings("rawtypes")
	public List listWithPaging(String queryId, Object parameterObject,
            int pageIndex, int pageSize) {
        int skipResults = pageIndex * pageSize;
        int maxResults = (pageIndex * pageSize) + pageSize;
        return getSqlMapClientTemplate().queryForList(queryId, parameterObject,
            skipResults, maxResults);
    }

}
