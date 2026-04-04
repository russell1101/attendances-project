package web.productManage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import web.productManage.dto.AdminProductDto;

@Service
public interface AdminProductService {

	List<AdminProductDto> getAllProducts();

	void saveProduct(AdminProductDto dto);
}
