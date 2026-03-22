package web.clockIn.service;

import org.springframework.stereotype.Service;

import web.clockIn.dto.AttendanceHistoryDto;
import web.clockIn.dto.ClockInResultDto;
import web.clockIn.dto.ClockStatusDto;

@Service
public interface ClockService {

	ClockInResultDto clockIn(Long employeeId);

	ClockStatusDto getTodayStatus(Long employeeId);

	ClockInResultDto clockOut(Long employeeId);

	java.util.List<AttendanceHistoryDto> getMonthlyHistory(Long employeeId, int year, int month);
}
