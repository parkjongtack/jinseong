package egovframework.common.core;

import java.util.ArrayList;
import java.util.List;

public class ResultDataManager{
	private List<ResultDataBean> list = new ArrayList<ResultDataBean>();
	
	public ResultDataManager(){}
	
	public void setData(Class<?> clazz, List<?> dataList){
		ResultDataBean bean = new ResultDataBean();
		bean.setClazz(clazz);
		bean.setDataList(dataList);
		
		list.add(bean);
	}
	
	public int getDataCount(){
		return list.size();
	}
	
	public ResultDataBean getData(int idx){
		return list.get(idx);
	}
	
	public String getDataID(int idx){
		ResultDataBean bean = list.get(idx);
		return bean.getDataID();
	}

}