package next.wildgoose.backcontroller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import next.wildgoose.dao.SignDAO;
import next.wildgoose.dto.result.SimpleResult;
import next.wildgoose.framework.BackController;
import next.wildgoose.framework.Result;
import next.wildgoose.utility.Constants;

public abstract class AuthController implements BackController {
	
	@Autowired
	SignDAO signDao;
	
	@Override
	public abstract Result execute(HttpServletRequest request);
	
	public SimpleResult authenticate(HttpServletRequest request, String userId) {
		SimpleResult sResult = null;
		if (!isValidUserId(request, userId)) {
			sResult = new SimpleResult();
			sResult.setStatus(404);
			sResult.setPageName("join");
			sResult.setMessage(Constants.MSG_WRONG_ID);
			return sResult;
		}
		
		HttpSession session = request.getSession();
		String visitor = (String) session.getAttribute("userId");
		
		if (visitor == null) {
			sResult = new SimpleResult();
			sResult.setStatus(401);
			sResult.setPageName("login");
			sResult.setMessage(Constants.MSG_AUTH_NEED);
			// 로그인 하도록 유도하기
		}
		return sResult;
	}
	private boolean isValidUserId(HttpServletRequest request, String userId) {
		if (signDao.findEmail(userId)) {
			return true;
		}
		return false;
	}

}
