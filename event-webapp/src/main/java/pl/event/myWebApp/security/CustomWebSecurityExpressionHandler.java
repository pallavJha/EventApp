package pl.event.myWebApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.expression.AbstractSecurityExpressionHandler;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import pl.event.myApp.persistense.UserDao;

@Component("expressionHandler")
public class CustomWebSecurityExpressionHandler extends
		AbstractSecurityExpressionHandler<FilterInvocation> {

	@Autowired
	@Qualifier("userDao")
	UserDao userDao;

	@Override
	protected SecurityExpressionRoot createSecurityExpressionRoot(
			Authentication authentication, FilterInvocation invocation) {
		NewExpressionsRoot root = new NewExpressionsRoot(authentication,
				invocation, userDao);
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setTrustResolver(new AuthenticationTrustResolverImpl());
		return root;
	}

}
