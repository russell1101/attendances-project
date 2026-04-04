package web.productManage.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.entity.Product;
import core.exception.BusinessException;
import web.productManage.dao.AdminProductDao;
import web.productManage.dto.AdminProductDto;
import web.productManage.service.AdminProductService;

@Service
@Transactional
public class AdminProductServiceImpl implements AdminProductService {

	@Autowired
	private AdminProductDao productDao;

	// 取得所有商品清單
	@Override
	public List<AdminProductDto> getAllProducts() {
		List<Product> products = productDao.findAll();
		List<AdminProductDto> dtoList = new ArrayList<>();

		for (Product product : products) {
			AdminProductDto dto = new AdminProductDto();
			dto.setProductId(product.getProductId());
			dto.setProductName(product.getProductName());
			dto.setDescription(product.getDescription());
			dto.setStock(product.getStock());
			dto.setRequiredPoints(product.getRequiredPoints());
			dto.setVersion(product.getVersion());
			dto.setReleasedAt(product.getReleasedAt());
			dto.setRemovedAt(product.getRemovedAt());
			dto.setValidDays(product.getValidDays());
			dto.setCreatedAt(product.getCreatedAt());
			dto.setUpdatedAt(product.getUpdatedAt());

			// byte[] 轉 Base64 字串
			if (product.getImageData() != null) {
				dto.setImageData(Base64.getEncoder().encodeToString(product.getImageData()));
			}

			dtoList.add(dto);
		}

		return dtoList;
	}

	// 新增或修改商品（productId 為 null → 新增，否則 → 修改）
	@Override
	public void saveProduct(AdminProductDto dto) {
		if (dto.getProductId() == null) {
			// 新增
			Product product = new Product();
			fillProductFields(product, dto);
			productDao.save(product);
		} else {
			// 修改
			Product product = productDao.findById(dto.getProductId());
			if (product == null) {
				throw new BusinessException("找不到該商品，無法進行修改");
			}
			fillProductFields(product, dto);
			productDao.update(product);
		}
	}

	// 將 dto 欄位寫入 entity
	private void fillProductFields(Product product, AdminProductDto dto) {
		product.setProductName(dto.getProductName());
		product.setDescription(dto.getDescription());
		product.setStock(dto.getStock());
		product.setRequiredPoints(dto.getRequiredPoints());
		product.setRemovedAt(dto.getRemovedAt());
		product.setValidDays(dto.getValidDays());

		// Base64 字串轉 byte[]
		if (dto.getImageData() != null && !dto.getImageData().isEmpty()) {
			product.setImageData(Base64.getDecoder().decode(dto.getImageData()));
		}
	}
}
