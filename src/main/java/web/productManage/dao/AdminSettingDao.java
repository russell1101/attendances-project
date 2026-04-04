package web.productManage.dao;

import core.entity.GlobalSetting;

public interface AdminSettingDao {

	GlobalSetting findByKey(String key);

	void update(GlobalSetting setting);
}
