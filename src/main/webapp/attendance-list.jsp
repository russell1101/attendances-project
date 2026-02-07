<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="web.attendance.bean.AttendanceRecordVO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>出勤紀錄</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 900px;
            margin: 50px auto;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .back-link {
            text-align: center;
            margin: 20px 0;
        }
        .delete-btn {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 5px 15px;
            cursor: pointer;
            border-radius: 3px;
        }
        .message {
            text-align: center;
            padding: 15px;
            margin: 20px 0;
            border-radius: 5px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>
    <h2>出勤紀錄列表</h2>
    
    <% String message = (String) session.getAttribute("message"); %>
    <% if (message != null) { %>
        <div class="message success"><%= message %></div>
        <% session.removeAttribute("message"); %>
    <% } %>
    
    <% String error = (String) session.getAttribute("error"); %>
    <% if (error != null) { %>
        <div class="message error"><%= error %></div>
        <% session.removeAttribute("error"); %>
    <% } %>
    
    <table>
        <thead>
            <tr>
                <th>日期</th>
                <th>上班時間</th>
                <th>下班時間</th>
                <th>上班狀態</th>
                <th>下班狀態</th>
                <th>積分</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <% 
            List<AttendanceRecordVO> records = (List<AttendanceRecordVO>) request.getAttribute("records");
            if (records != null && !records.isEmpty()) {
                for (AttendanceRecordVO record : records) {
            %>
                <tr>
                    <td><%= record.getWorkDate() %></td>
                    <td><%= record.getClockInTime() != null ? record.getClockInTime() : "-" %></td>
                    <td><%= record.getClockOutTime() != null ? record.getClockOutTime() : "-" %></td>
                    <td><%= record.getClockInStatus() != null ? record.getClockInStatus() : "-" %></td>
                    <td><%= record.getClockOutStatus() != null ? record.getClockOutStatus() : "-" %></td>
                    <td><%= record.getPointsAwarded() != null ? record.getPointsAwarded() : "0" %></td>
                    <td>
                        <form method="post" action="<%= request.getContextPath() %>/attendance/delete" style="display: inline;">
                            <input type="hidden" name="attendanceId" value="<%= record.getAttendanceId() %>">
                            <button type="submit" class="delete-btn" onclick="return confirm('確定要刪除這筆紀錄嗎?')">刪除</button>
                        </form>
                    </td>
                </tr>
            <% 
                }
            } else {
            %>
                <tr>
                    <td colspan="7">目前沒有出勤紀錄</td>
                </tr>
            <% } %>
        </tbody>
    </table>
    
    <div class="back-link">
        <a href="<%= request.getContextPath() %>/attendance/clock">返回打卡頁面</a>
    </div>
</body>
</html>
