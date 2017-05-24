package com.baojing.credit.dao ;
import java.util.List;

import org.springframework.stereotype.Component;

import com.baojing.credit.model.Trend;
@Component
public class TrendDao extends GenericDAOImpl<Trend> {
	@Override
	public String getNameSpace() {
		return "trend";
	}
	
	public List<Trend> findLimit10(){
		return this.getSqlSession().selectList(this.getNameSpace()+".findLimit10");
	}
}
