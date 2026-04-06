package web.productManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.entity.GlobalSetting;
import core.enums.GlobalSettingKeyEnum;
import core.exception.BusinessException;
import web.productManage.dao.AdminSettingDao;
import web.productManage.dto.GlobalSettingDto;
import web.productManage.service.AdminSettingService;

@Service
@Transactional
public class AdminSettingServiceImpl implements AdminSettingService {

	@Autowired
	private AdminSettingDao settingDao;

	@Override
	public GlobalSettingDto getGlobalSettings() {
		GlobalSetting onTime = settingDao.findByKey(GlobalSettingKeyEnum.GLOBAL_ON_TIME_BONUS.getKey());
		GlobalSetting late = settingDao.findByKey(GlobalSettingKeyEnum.GLOBAL_LATE_PENALTY.getKey());

		GlobalSettingDto dto = new GlobalSettingDto();
		dto.setOnTimeBonus(onTime != null ? onTime.getSettingValue() : "0");
		dto.setLatePenalty(late != null ? late.getSettingValue() : "0");
		return dto;
	}

	@Override
	public void saveGlobalSettings(GlobalSettingDto dto) {
		updateSetting(GlobalSettingKeyEnum.GLOBAL_ON_TIME_BONUS.getKey(), dto.getOnTimeBonus());
		updateSetting(GlobalSettingKeyEnum.GLOBAL_LATE_PENALTY.getKey(), dto.getLatePenalty());
	}

	private void updateSetting(String key, String value) {
		GlobalSetting setting = settingDao.findByKey(key);
		if (setting == null) {
			throw new BusinessException("找不到設定項目：" + key);
		}
		setting.setSettingValue(value);
		settingDao.update(setting);
	}
}
