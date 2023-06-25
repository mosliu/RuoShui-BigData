package com.ruoshui.bigdata.core.conf;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @Date: 2022/1/20 22:52
 * @Description:
 **/
@Component
public class ExcecutorConfig  implements InitializingBean, DisposableBean {

	private static ExcecutorConfig excecutorConfig = null;

	public static ExcecutorConfig getExcecutorConfig() {
		return excecutorConfig;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		excecutorConfig = this;
	}

	@Override
	public void destroy() throws Exception {
	}



	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassname;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;


	public static void setExcecutorConfig(ExcecutorConfig excecutorConfig) {
		ExcecutorConfig.excecutorConfig = excecutorConfig;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClassname() {
		return driverClassname;
	}

	public void setDriverClassname(String driverClassname) {
		this.driverClassname = driverClassname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
