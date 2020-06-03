package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	//@RequestMapping("/addProductView.do") 기존 맵핑하는방법에서 바꿈 Rest방식으로.
	@RequestMapping(value = "addProduct", method = RequestMethod.GET)
//						URL 의 맵핑주소		    ,		어떤방식으로 보낼것인지. 
	public String addProduct() throws Exception {

		System.out.println("addProduct : GET");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	//@RequestMapping("/addProduct.do")
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
//					URL 의 맵핑주소		    ,		어떤방식으로 보낼것인지. 	
	public String addProduct( @ModelAttribute("product") Product product) throws Exception {
// 	@ModelAttribute는 도메인 Product의객체를 1대1로 바인딩하여 다시 View로 넘겨서 출력하기 위해 사용되는 오브젝트이다.	
//				@ModelAttribute("product") view에서 product로 접근  / 어떤VO,도메인에서 ? ProductVO 에서 
//						product의 객체들을 get,set 바인딩하여 get,set이 없으면 에러가뜸. 		

		System.out.println("addProduct : POST");
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	//@RequestMapping("/listProduct.do")
	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search,Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("listProductProduct : GET / POST ");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		
		System.out.println("현재 페이지 : "+search.getCurrentPage());
		
		search.setPageSize(pageSize);
		
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	
	//@RequestMapping("/updateProductView.do")
	@RequestMapping(value = "updateProduct",method = RequestMethod.GET)
//							URL 의 맵핑주소		   ,		어떤방식으로 보낼것인지. 						
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
//							(RequestParam("가져올데이터타입 의 이름 ,[데이터타입], [가져온데이터를 담을변수]
//					GetParameter랑 같음 URI 의 prodNo를 가져와서 ,int prodNo 데이터타입 지정, Model 객체에 넣음 						
		System.out.println("updateProduct : GET");
		Product product=productService.getProduct(prodNo);
		model.addAttribute("product",product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	//@RequestMapping("/updateProduct.do")
	@RequestMapping(value = "updateProduct", method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product")Product product, Model model , HttpSession session) throws Exception{
		System.out.println("updateProduct : POST");
		
		
		productService.updateProduct(product);
		
		int prodNo=product.getProdNo();
		
		return "redirect:/product/getProduct?prodNo="+prodNo+"&menu=manage";
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping(value = "getProduct",method = RequestMethod.GET)
	public String getProduct(@RequestParam("prodNo") String prodNo, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length>0) {
		  for(int i=0;i<cookies.length;i++) {	
			  Cookie cookie = cookies[i];
			if(cookie.getName().equals("history")) {
				cookie.setValue(cookie.getValue()+","+prodNo);
				cookie.setMaxAge(60*60);
				response.addCookie(cookie);
			}else{
			cookie = new Cookie("history",prodNo);
			cookie.setMaxAge(60*60);
			cookie.setPath("/");
			response.addCookie(cookie);
			
			}
		  }
		}
	
		Product product=productService.getProduct(Integer.parseInt(prodNo));
		
		model.addAttribute("product",product);
		
		return "forward:/product/getProduct.jsp";
	}

}
