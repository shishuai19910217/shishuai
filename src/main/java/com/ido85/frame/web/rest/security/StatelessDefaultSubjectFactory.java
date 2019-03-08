package com.ido85.frame.web.rest.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {

	/**(non-Javadoc)
	 * @see org.apache.shiro.web.mgt.DefaultWebSubjectFactory#createSubject(org.apache.shiro.subject.SubjectContext)
	 * 不创建session
	 */
	@Override
	public Subject createSubject(SubjectContext context) {
		context.setSessionCreationEnabled(false);
		return super.createSubject(context);
	}
}
