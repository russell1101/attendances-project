<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>出勤紀錄列表</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 20px;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 20px;
                }

                th,
                td {
                    border: 1px solid #ddd;
                    padding: 12px;
                    text-align: left;
                }

                th {
                    background-color: #f2f2f2;
                }

                tr:nth-child(even) {
                    background-color: #f9f9f9;
                }

                .nav {
                    margin-bottom: 20px;
                }
            </style>
        </head>

        <body>
            <div class="nav">
                <a href="${pageContext.request.contextPath}/attendance/clock">回打卡頁面</a> |
                <a href="${pageContext.request.contextPath}/index.html">回首頁</a>
            </div>

            <h1>個人出勤紀錄</h1>

            <table>
                <thead>
                    <tr>
                        <th>日期</th>
                        <th>上班時間</th>
                        <th>上班狀態</th>
                        <th>下班時間</th>
                        <th>下班狀態</th>
                        <th>獎勵點數</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="record" items="${records}">
                        <tr>
                            <td>${record.workDate}</td>
                            <td>${record.clockInTime}</td>
                            <td>${record.clockInStatus}</td>
                            <td>${record.clockOutTime != null ? record.clockOutTime : '--:--:--'}</td>
                            <td>${record.clockOutStatus != null ? record.clockOutStatus : '--'}</td>
                            <td>${record.pointsAwarded}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty records}">
                        <tr>
                            <td colspan="6" style="text-align: center;">目前沒有出勤紀錄</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </body>

        </html>