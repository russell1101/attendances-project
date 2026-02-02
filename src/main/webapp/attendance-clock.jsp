<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>出勤打卡</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
        }
        .container {
            border: 1px solid #ddd;
            padding: 30px;
            border-radius: 8px;
            background-color: #f9f9f9;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .button-group {
            text-align: center;
            margin: 30px 0;
        }
        button {
            padding: 15px 40px;
            margin: 10px;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .clock-in {
            background-color: #4CAF50;
            color: white;
        }
        .clock-out {
            background-color: #f44336;
            color: white;
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
        .status {
            text-align: center;
            font-size: 18px;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>出勤打卡系統</h2>
        
        <% String message = (String) request.getAttribute("message"); %>
        <% if (message != null) { %>
            <div class="message success">
                <%= message %>
            </div>
        <% } %>
        
        <% Integer status = (Integer) request.getAttribute("status"); %>
        <% if (status != null) { %>
            <div class="status">
                <% if (status == 0) { %>
                    狀態: 尚未打卡
                <% } else if (status == 1) { %>
                    狀態: 工作中
                <% } else if (status == 2) { %>
                    狀態: 已下班
                <% } %>
            </div>
        <% } %>
        
        <div class="button-group">
            <form method="post" action="<%= request.getContextPath() %>/attendance/clock" style="display: inline;">
                <input type="hidden" name="action" value="clockIn">
                <button type="submit" class="clock-in" <%= (status != null && status != 0) ? "disabled" : "" %>>
                    上班打卡
                </button>
            </form>
            
            <form method="post" action="<%= request.getContextPath() %>/attendance/clock" style="display: inline;">
                <input type="hidden" name="action" value="clockOut">
                <button type="submit" class="clock-out" <%= (status == null || status != 1) ? "disabled" : "" %>>
                    下班打卡
                </button>
            </form>
        </div>
        
        <div style="text-align: center; margin-top: 30px;">
            <a href="<%= request.getContextPath() %>/attendance/list">查看出勤紀錄</a>
        </div>
    </div>
</body>
</html>
