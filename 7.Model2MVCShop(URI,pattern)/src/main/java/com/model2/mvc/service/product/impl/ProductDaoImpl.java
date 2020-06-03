package com.model2.mvc.service.product.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDAO;



@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDAO {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	//ibatis객체 생성. 쿼리날려주는 .
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
//순수하게 맵핑만연결 시켜 주는 부분 . DAOIMPL 오는 데이터를 맵핑 시켜주는부분 .	
	public ProductDaoImpl() {
		System.out.println(this.getClass());
	}

	public void insertProduct(Product product) throws Exception {
		product.setManuDate(product.getManuDate().replace("-", ""));
		sqlSession.insert("ProductMapper.addProduct", product);	
	}
	
	public Product findProduct(int prodNo) throws Exception {
		return sqlSession.selectOne("ProductMapper.getProduct", prodNo);
	}

	public List<Product> getProductList(Search search) throws Exception {
		return sqlSession.selectList("ProductMapper.getProductList", search);
	}

	public void updateProduct(Product product) throws Exception {
		product.setManuDate(product.getManuDate().replace("-", ""));
		sqlSession.update("ProductMapper.updateProduct", product);
	}

	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}

}
