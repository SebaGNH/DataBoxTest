package ar.edu.ues21.pricingbff.security.config;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * Custom method expression root
 */
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    /**
     * Creates a new {@link CustomMethodSecurityExpressionRoot}
     *
     * @param authentication the {@link Authentication}
     */
    public CustomMethodSecurityExpressionRoot(final Authentication authentication) {
        super(authentication);
    }

    /**
     * Validate if the given parameter is the user name of the principal name
     *
     * @param username the parameter value
     * @return true if the principal has the given username, false otherwise
     */
    public boolean isUserLogged(final String username) {
        final String user = ((String) this.getPrincipal());
        return user.equalsIgnoreCase(username);
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setFilterObject(final Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public void setReturnObject(final Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}