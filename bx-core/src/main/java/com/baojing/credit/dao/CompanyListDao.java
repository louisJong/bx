package com.baojing.credit.dao ;
import java.util.List;

import org.springframework.stereotype.Component;

import com.baojing.credit.model.CompanyList;
@Component
public class CompanyListDao extends GenericDAOImpl<CompanyList> {
	@Override
	public String getNameSpace() {
		return "company_list";
	}
	
	public List<CompanyList> findByNameLimit10(String comName){
		return this.getSqlSession().selectList(this.getNameSpace()+".findByNameLimit10" , comName);
	}
	
}
