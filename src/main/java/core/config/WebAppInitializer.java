package core.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SpringConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		return new Filter[] { characterEncodingFilter };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		MultipartConfigElement multipartConfig = new MultipartConfigElement(null, // 上傳的檔案之暫存目錄: 使用預設目錄
				536870912, // 單檔大小上限: 512MB
				4294967296L, // 單次大小上限: 4GB
				0 // 寫出至暫存目錄的大小上限: 都寫出至暫存目錄
		);
		registration.setMultipartConfig(multipartConfig);
	}

}
