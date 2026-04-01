package web.cart.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.annotation.DbOperation;
import core.entity.Employee;
import core.entity.GiftCard;
import core.entity.PointTransaction;
import core.entity.Product;
import core.entity.ProductRedemption;
import core.enums.EmployeeStatusEnum;
import core.enums.GiftCardStatusEnum;
import core.enums.PointTransactionType;
import core.exception.BusinessException;
import web.cart.dao.CartEmployeeDao;
import web.cart.dao.CartGiftCardDao;
import web.cart.dao.CartPointTransactionDao;
import web.cart.dao.CartProductDao;
import web.cart.dao.CartProductRedemptionDao;
import web.cart.dto.BuyResultDto;
import web.cart.dto.ProductDto;
import web.cart.service.CartProductService;

@Service
@Transactional
public class CartProductServiceImpl implements CartProductService {

	@Autowired
	private CartProductDao productDao;
	@Autowired
	private CartEmployeeDao employeeDao;
	@Autowired
	private CartPointTransactionDao transactionDao;
	@Autowired
	private CartProductRedemptionDao redemptionDao;
	@Autowired
	private CartGiftCardDao giftCardDao;

	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> productList = productDao.findAll();
		List<ProductDto> dtoList = new ArrayList<>();

		for (Product product : productList) {
			ProductDto dto = new ProductDto();
			BeanUtils.copyProperties(product, dto, "imageData");

			// base64 圖片
			if (product.getImageData() != null && product.getImageData().length > 0) {
				String base64 = Base64.getEncoder().encodeToString(product.getImageData());
				dto.setImageData("data:image/jpeg;base64," + base64);
			}

			dtoList.add(dto);
		}

		return dtoList;
	}

	@Override
	@DbOperation(action = "員工購買禮券")
	public BuyResultDto buyProduct(Long employeeId, Long productId, Integer qty) {
		if (qty <= 0) {
			throw new BusinessException("購買數量必須大於 0");
		}

		Product product = productDao.findById(productId);
		if (product == null) {
			throw new BusinessException("找不到該商品");
		}

		if (product.getStock() < qty) {
			throw new BusinessException("庫存不足，晚了一步被搶光了");
		}

		Employee employee = employeeDao.findById(employeeId);
		if (employee == null) {
			throw new BusinessException("查無個人基本資訊");
		}

		// 員工狀態檢查
		if (!Boolean.TRUE.equals(employee.getIsActive())) {
			throw new BusinessException("帳號已停用");
		}
		if (!EmployeeStatusEnum.ACTIVE.getId().equals(employee.getEmployeeStatusId())) {
			throw new BusinessException("非在職狀態，無法兌換");
		}

		BigDecimal cost = product.getRequiredPoints().multiply(new BigDecimal(qty));
		if (employee.getCurrentPoints().compareTo(cost) < 0) {
			throw new BusinessException("點數不足");
		}

		// 扣點數與庫存
		employee.setCurrentPoints(employee.getCurrentPoints().subtract(cost));
		product.setStock(product.getStock() - qty);

		// 寫入點數異動紀錄
		PointTransaction txn = new PointTransaction();
		txn.setEmployeeId(employeeId);
		txn.setPointsUpdate(cost.negate()); // 轉負數
		txn.setReason("兌換商品: " + product.getProductName() + " x" + qty);
		txn.setRelatedType(PointTransactionType.PRODUCT_REDEMPTION.name());
		txn.setRelatedId(product.getProductId());
		transactionDao.save(txn);

		// 寫入商品兌換明細紀錄
		ProductRedemption redemption = new ProductRedemption();
		redemption.setEmployeeId(employeeId);
		redemption.setProductId(productId);
		redemption.setQuantity(qty);
		redemption.setPointsSpent(cost);
		redemptionDao.save(redemption);

		// 生成兌換券
		for (int i = 0; i < qty; i++) {
			GiftCard giftCard = new GiftCard();
			giftCard.setGiftName(product.getProductName());
			giftCard.setEmployeeId(employeeId);
			giftCard.setRedemptionId(redemption.getRedemptionId()); // 取得剛剛insert的id

			giftCard.setGiftCardStatusId(GiftCardStatusEnum.AVAILABLE.getId());

			// 產生隨機8碼兌換碼
			String randomCode = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
			giftCard.setGiftCode(randomCode);

			// 處理過期時間
			if (product.getValidDays() != null) {
				java.time.LocalDateTime expiryDate = java.time.LocalDateTime.now().plusDays(product.getValidDays());
				giftCard.setExpiresAt(java.sql.Timestamp.valueOf(expiryDate));
			}

			giftCardDao.save(giftCard);
		}

		return new BuyResultDto(employee.getCurrentPoints(), product.getStock(), "兌換成功！禮券已發送至您的票夾");
	}
}