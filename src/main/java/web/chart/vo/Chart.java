package web.chart.vo;

import java.util.List;

import lombok.Data;

@Data
public class Chart {
	// 圓餅圖-出勤狀態
	private int onTime; // 準時人數
	private int late; // 遲到人數
	private int absent; // 曠職人數

	// 橫條圖-遲到排行
	private List<String> deptName; // 部門名稱 Y軸
	private List<Integer> lateCounts; // 遲到次數 X軸

	// 長條圖-工時統計
	private List<String> workingDates; // 日期 Y軸
	private List<Integer> workingHours; // 工時 X軸

	// 散佈圖-打卡紀錄
	private List<String> checkInTimes; // 上班打卡時間
	private List<String> checkOutTimes; // 下班打卡時間

	private int totalLateCounts; // 總遲到人次
	private double attendRate; // 出勤率
	private int noChecked; // 未打卡人數

}