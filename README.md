# 員工出勤管理與獎勵系統 (Attendances Project)

企業級員工出勤管理系統，提供打卡記錄、積分獎懲、商品兌換等功能，適用於人事管理需求。

---

## 技術架構

| 類別 | 技術 |
|------|------|
| 後端框架 | Spring MVC 5.3.39 |
| ORM | Hibernate 5.6.15 |
| 資料庫 | MySQL 8.4.0 |
| 連線池 | HikariCP 4.0.3 |
| 即時通訊 | Spring WebSocket |
| 排程任務 | Spring Scheduling |
| 日誌 | Log4j2 2.25.3 |
| 密碼加密 | BCrypt (strength: 12) |
| Email | JavaMail + Gmail SMTP |
| 建構工具 | Maven 3.x |
| 封裝格式 | WAR |
| Java 版本 | Java 8 |

---

## 系統功能

### 員工端
- **打卡管理**：上下班打卡，記錄狀態（準時、遲到、早退）
- **積分查詢**：查看個人積分餘額與交易明細
- **商品兌換**：瀏覽可兌換商品，使用積分購買
- **禮品卡管理**：查看擁有的禮品卡並進行兌換核銷

### 管理員端
- **即時儀表板**：WebSocket 驅動的出勤數據即時圖表
- **部門管理**：新增、編輯、刪除部門，設定工時與積分規則
- **員工管理**：帳號建立、狀態控管（在職、停職、離職）
- **商品管理**：上架、更新商品與圖片
- **兌換記錄**：查看所有員工的兌換歷史
- **系統設定**：全域參數設定（積分規則、點數值等）

### 自動化任務
- 每日 00:00（Asia/Taipei）自動檢查並過期逾期禮品卡
- AOP 資料庫操作稽核日誌（寫入獨立 `db_audit.log`）

---

## 專案結構

```
attendances-project/
├── src/main/
│   ├── java/
│   │   ├── core/                   # 核心框架模組
│   │   │   ├── annotation/         # 自訂注解 (@DbOperation)
│   │   │   ├── aop/                # AOP 稽核切面
│   │   │   ├── config/             # Spring 設定類別
│   │   │   ├── entity/             # JPA 實體（資料庫模型）
│   │   │   ├── enums/              # 列舉常數
│   │   │   ├── exception/          # 例外處理
│   │   │   ├── interceptor/        # HTTP 攔截器（登入驗證）
│   │   │   ├── listener/           # Application 監聽器
│   │   │   └── util/               # 工具類別（API 回應格式）
│   │   └── web/                    # 功能模組
│   │       ├── cart/               # 積分與禮品卡
│   │       ├── chart/              # 數據分析與 WebSocket
│   │       ├── clockIn/            # 出勤打卡
│   │       ├── employee/           # 員工與管理員帳號
│   │       └── productManage/      # 商品管理（管理員）
│   ├── resources/
│   │   ├── log4j2.properties       # 日誌設定
│   │   └── mail.properties         # Email 設定
│   └── webapp/
│       └── assets/                 # 前端靜態資源
└── pom.xml                         # Maven 依賴設定
```

---

## 環境需求

- JDK 1.8+
- Apache Tomcat 9+
- MySQL 8.x
- Maven 3.6+

---

## 安裝與部署

### 1. 資料庫設定

建立 MySQL 資料庫並設定 Tomcat JNDI：

在 Tomcat 的 `conf/context.xml` 加入以下設定：

```xml
<Resource name="jdbc/attendances"
          auth="Container"
          type="javax.sql.DataSource"
          driverClassName="com.mysql.cj.jdbc.Driver"
          url="jdbc:mysql://localhost:3306/attendances?useSSL=false&amp;serverTimezone=Asia/Taipei&amp;characterEncoding=utf8"
          username="your_db_user"
          password="your_db_password"
          maxTotal="20"
          maxIdle="10"
          maxWaitMillis="10000"/>
```

系統啟動時 Hibernate 會自動建立/更新資料表結構。

### 2. Email 設定

編輯 `src/main/resources/mail.properties`：

```properties
mail.host=smtp.gmail.com
mail.port=587
mail.username=your-email@gmail.com
mail.password=your-app-specific-password
```

> Gmail 需使用「應用程式密碼」，而非帳號密碼。

### 3. 建構專案

```bash
mvn clean package
```

產出檔案位於 `target/attendances-project.war`。

### 4. 部署

將 `attendances-project.war` 複製到 Tomcat 的 `webapps/` 目錄，啟動 Tomcat 即可。

```bash
# 啟動 Tomcat
$CATALINA_HOME/bin/startup.sh

# 停止 Tomcat
$CATALINA_HOME/bin/shutdown.sh
```

---

## API 端點

### 員工端 `/frontUser`

| 方法 | 路徑 | 說明 |
|------|------|------|
| POST | `/frontUser/employee/login` | 員工登入 |
| GET | `/frontUser/employee/logout` | 員工登出 |
| POST | `/frontUser/employee/checkLogin` | 檢查登入狀態 |
| POST | `/frontUser/clock/clockin` | 打卡上班 |
| POST | `/frontUser/clock/clockout` | 打卡下班 |
| GET | `/frontUser/clock/status` | 查詢今日打卡狀態 |
| GET | `/frontUser/clock/history` | 查詢月份出勤紀錄 |
| GET | `/frontUser/clock/profile` | 查詢個人資料 |
| GET | `/frontUser/product/getAll` | 取得可兌換商品列表 |
| POST | `/frontUser/product/buy` | 積分購買商品 |
| GET | `/frontUser/cart/myAssets` | 查詢積分與禮品卡 |
| POST | `/frontUser/cart/exchange` | 核銷禮品卡 |

### 管理員端 `/admin`

| 方法 | 路徑 | 說明 |
|------|------|------|
| POST | `/admin/login` | 管理員登入 |
| GET | `/admin/logout` | 管理員登出 |
| GET | `/admin/chart/chartData` | 取得出勤分析數據 |
| GET | `/admin/dep/manage` | 部門列表 |
| POST | `/admin/dep/save` | 新增部門 |
| POST | `/admin/dep/update` | 更新部門 |
| POST | `/admin/dep/remove` | 刪除部門 |
| GET | `/admin/emp/manage` | 員工列表 |
| POST | `/admin/emp/save` | 新增員工 |
| POST | `/admin/emp/update` | 更新員工 |
| POST | `/admin/emp/remove` | 刪除員工 |
| GET | `/admin/product/list` | 商品列表 |
| POST | `/admin/product/save` | 新增/更新商品 |
| GET | `/admin/setting/list` | 系統設定列表 |
| POST | `/admin/setting/save` | 更新系統設定 |
| GET | `/admin/redemption/list` | 兌換記錄列表 |

### WebSocket

| 路徑 | 說明 |
|------|------|
| `ws://.../ws/admin/chart` | 即時出勤數據推送 |

---

## 資料模型

| 實體 | 說明 |
|------|------|
| `Employee` | 員工資料與積分餘額 |
| `Department` | 部門與工時、積分規則 |
| `AttendanceRecord` | 每日打卡紀錄 |
| `PointTransaction` | 積分異動明細 |
| `Product` | 可兌換商品 |
| `GiftCard` | 禮品卡（含有效期） |
| `ProductRedemption` | 商品兌換交易紀錄 |
| `AdminUser` | 管理員帳號 |
| `GlobalSetting` | 系統全域設定（Key-Value） |

---

## 安全機制

- **BCrypt 加密**：密碼雜湊強度 12
- **Session 保護**：登入後變更 Session ID（防 Session Fixation）
- **路由攔截**：
  - `AdminInterceptor` 保護 `/admin/**`
  - `EmployeeInterceptor` 保護 `/frontUser/**`
- **CORS**：支援跨域請求，允許攜帶憑證

---

## 日誌

| 日誌類型 | 位置 | 說明 |
|----------|------|------|
| 應用程式日誌 | `$CATALINA_HOME/log/` | 滾動式，每 10MB 輪替 |
| 資料庫稽核日誌 | `$CATALINA_HOME/log/db_audit.log` | 記錄所有資料異動，保留 90 天 |

---

## 開發者

| 開發者 | 分支 |
|--------|------|
| Wesley | `wesley_dev` |
| Marco | `marco_dev` |
| Ann | `ann_dev` |
