package web.productManage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import core.entity.AdminUser;
import core.util.ApiResponse;
import web.productManage.dto.GlobalSettingDto;
import web.productManage.service.AdminSettingService;

@RestController
@RequestMapping("/admin/setting")
public class AdminSettingController {

	@Autowired
	private AdminSettingService service;

	// 取得全域考勤點數設定
	@GetMapping("/global")
	public ApiResponse<GlobalSettingDto> getGlobalSettings(
			@SessionAttribute("adminUser") AdminUser adminUser) {

		return ApiResponse.success(service.getGlobalSettings());
	}

	// 儲存全域考勤點數設定
	@PostMapping("/global")
	public ApiResponse<String> saveGlobalSettings(
			@SessionAttribute("adminUser") AdminUser adminUser,
			@RequestBody GlobalSettingDto dto) {

		service.saveGlobalSettings(dto);
		return ApiResponse.success("success");
	}
}
