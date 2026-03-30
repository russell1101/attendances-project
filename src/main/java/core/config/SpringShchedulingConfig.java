package core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import web.cart.service.CartAssetService;

@Configuration
@EnableScheduling
@EnableAsync
public class SpringShchedulingConfig {
    
    private static final Logger logger = LogManager.getLogger(SpringShchedulingConfig.class);

    @Autowired
    private CartAssetService giftCardService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Taipei")
    @Async
    public void checkAndExpireGiftCards() {
        logger.info("【系統排程啟動】開始檢查過期禮券");
        try {
            giftCardService.processExpiredGiftCards();
            logger.info("【系統排程結束】過期禮券檢查完畢");
        } catch (Exception e) {
            logger.error("【系統排程失敗】更新過期禮券發生異常", e);
        }
    }
}