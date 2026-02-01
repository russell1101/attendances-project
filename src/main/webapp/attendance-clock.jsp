<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>打卡系統</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 600px; margin: 0 auto; text-align: center; padding: 20px; border: 1px solid #ccc; border-radius: 10px; }
        .btn { padding: 10px 20px; font-size: 18px; cursor: pointer; border: none; border-radius: 5px; color: white; }
        .btn-in { background-color: #4CAF50; }
        .btn-out { background-color: #f44336; }
        .btn:disabled { background-color: #cccccc; cursor: not-allowed; }
        .message { color: blue; margin: 10px 0; }
        .nav { margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="nav">
            <a href="${pageContext.request.contextPath}/attendance/list">查看出勤紀錄</a> | 
            <a href="${pageContext.request.contextPath}/index.html">回首頁</a>
        </div>

        <h1>員工打卡系統</h1>
        
        <c:if test="${not empty message}">
            <div class="message">${message}</div>
        </c:if>

        <div class="clock-area">
            <h3>目前狀態：
                <c:choose>
                    <c:when test="${status == 0}">尚未打卡</c:when>
                    <c:when test="${status == 1}">已上班 (工作中)</c:when>
                    <c:when test="${status == 2}">已下班</c:when>
                </c:choose>
            </h3>

            <form action="${pageContext.request.contextPath}/attendance/clock" method="post">
                <c:choose>
                    <c:when test="${status == 0}">
                        <button type="submit" name="action" value="clockIn" class="btn btn-in">上班打卡</button>
                    </c:when>
                    <c:when test="${status == 1}">
                        <button type="submit" name="action" value="clockOut" class="btn btn-out">下班打卡</button>
                    </c:when>
                    <c:when test="${status == 2}">
                        <button type="button" class="btn" disabled>今日打卡已完成</button>
                    </c:when>
                </c:choose>
            </form>
        </div>
    </div>
</body>
</html>
