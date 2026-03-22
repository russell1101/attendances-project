package web.cart.service;

import web.cart.dto.UserAssetDto;

public interface CartAssetService {

	UserAssetDto getUserAssets(Long employeeId);

}
