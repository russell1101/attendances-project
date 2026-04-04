package web.productManage.service;

import org.springframework.stereotype.Service;

import web.productManage.dto.GlobalSettingDto;

@Service
public interface AdminSettingService {

	GlobalSettingDto getGlobalSettings();

	void saveGlobalSettings(GlobalSettingDto dto);
}
