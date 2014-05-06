package next.wildgoose.web;

import javax.servlet.http.HttpServletRequest;

import next.wildgoose.dao.SignDAO;
import next.wildgoose.model.Account;
import next.wildgoose.utility.Validation;

public class SignAccount extends DaoManager {
	
	private Account account;
	private SignDAO signDAO = (SignDAO) context.getAttribute("signDAO");

	protected SignAccount(HttpServletRequest request) {
		super(request);
		this.account = new Account(request.getParameter("email"), request.getParameter("password"));
		
	}

	public boolean up () {
		// validation 성공시
		if (Validation.isEmail(account.getEmail()) && Validation.isPassword(account.getPassword())) {
			// signDao 에서 회원등록하는 부분 실행 
			return true;
		}	
		
		// validation 실패시
		return false;
	}
	
	public boolean hasEmail(String email) {

		if (Validation.isEmail(email)) {
			// signDao email을 검색하는 부분 실행
			// result = singDao.findByEmail(email);
			return "hello@world.com".equals(email);
		}
		
		return false;
	}
	
}