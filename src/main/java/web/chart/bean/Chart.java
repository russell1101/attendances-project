package web.chart.bean;

import java.util.List;

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
	private List<String> checkInTimes; //  上班打卡時間 
	private List<String> checkOutTimes; // 下班打卡時間
	
	private int totalLateCounts; // 總遲到人次
	private double attendRate; // 出勤率
	private int noChecked; // 未打卡人數
	
	
	public int getOnTime() {
		return onTime;
	}
	public void setOnTime(int onTime) {
		this.onTime = onTime;
	}
	public int getLate() {
		return late;
	}
	public void setLate(int late) {
		this.late = late;
	}
	public int getAbsent() {
		return absent;
	}
	public void setAbsent(int absent) {
		this.absent = absent;
	}
	public List<String> getDeptName() {
		return deptName;
	}
	public void setDeptName(List<String> deptName) {
		this.deptName = deptName;
	}
	public List<Integer> getLateCounts() {
		return lateCounts;
	}
	public void setLateCounts(List<Integer> lateCounts) {
		this.lateCounts = lateCounts;
	}
	public List<String> getWorkingDates() {
		return workingDates;
	}
	public void setWorkingDates(List<String> workingDates) {
		this.workingDates = workingDates;
	}
	public List<Integer> getWorkingHours() {
		return workingHours;
	}
	public void setWorkingHours(List<Integer> workingHours) {
		this.workingHours = workingHours;
	}
	public List<String> getCheckInTimes() {
		return checkInTimes;
	}
	public void setCheckInTimes(List<String> checkInTimes) {
		this.checkInTimes = checkInTimes;
	}
	public List<String> getCheckOutTimes() {
		return checkOutTimes;
	}
	public void setCheckOutTimes(List<String> checkOutTimes) {
		this.checkOutTimes = checkOutTimes;
	}
	public int getTotalLateCounts() {
		return totalLateCounts;
	}
	public void setTotalLateCounts(int totalLateCounts) {
		this.totalLateCounts = totalLateCounts;
	}
	public double getAttendRate() {
		return attendRate;
	}
	public void setAttendRate(double attendRate) {
		this.attendRate = attendRate;
	}
	public int getNoChecked() {
		return noChecked;
	}
	public void setNoChecked(int noChecked) {
		this.noChecked = noChecked;
	}
	
	
}