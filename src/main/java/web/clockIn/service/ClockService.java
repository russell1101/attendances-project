package web.clockIn.service;

import org.springframework.stereotype.Service;

import web.clockIn.dto.ClockInResultDto;

@Service
public interface ClockService {

	ClockInResultDto clockIn(Long employeeId);

}
