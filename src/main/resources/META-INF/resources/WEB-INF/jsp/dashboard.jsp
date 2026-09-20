<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Monitoring Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background: #f5f5f5; }
        h1 { color: #222; }
        h2 { color: #444; margin-top: 40px; }
        table { border-collapse: collapse; width: 100%; background: #fff; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background: #333; color: #fff; }
        .breached { background: #ffe0e0; color: #a30000; font-weight: bold; }
        .ok { color: #237804; }
        .summary { display: flex; gap: 20px; margin-bottom: 20px; }
        .card { background: #fff; border: 1px solid #ddd; padding: 15px 25px; border-radius: 6px; }
        .card span { display: block; font-size: 22px; font-weight: bold; }
    </style>
</head>
<body>

<h1>Payment Monitoring Dashboard</h1>

<div class="summary">
    <div class="card">Total Transactions<span>${totalTransactions}</span></div>
    <div class="card">Successful<span>${successCount}</span></div>
    <div class="card">Failed<span>${failedCount}</span></div>
    <div class="card">Active Violations<span>${violationCount}</span></div>
</div>

<h2>Gateway-wise Stats</h2>
<table>
    <tr>
        <th>Gateway</th>
        <th>Total</th>
        <th>Success</th>
        <th>Failed</th>
        <th>Failure Rate</th>
        <th>Threshold</th>
        <th>Status</th>
    </tr>
    <c:forEach var="stat" items="${gatewayStats}">
        <tr class="${stat.breached ? 'breached' : ''}">
            <td>${stat.gateway}</td>
            <td>${stat.totalCount}</td>
            <td>${stat.successCount}</td>
            <td>${stat.failedCount}</td>
            <td><fmt:formatNumber value="${stat.failureRate}" maxFractionDigits="2" />%</td>
            <td><fmt:formatNumber value="${stat.threshold}" maxFractionDigits="2" />%</td>
            <td>
                <c:choose>
                    <c:when test="${stat.breached}">BREACHED</c:when>
                    <c:otherwise><span class="ok">NORMAL</span></c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>

<h2>Recent Alerts</h2>
<table>
    <tr>
        <th>Gateway</th>
        <th>Total</th>
        <th>Failed</th>
        <th>Failure Rate</th>
        <th>Threshold</th>
        <th>Raised At</th>
    </tr>
    <c:forEach var="alert" items="${alerts}">
        <tr>
            <td>${alert.gateway}</td>
            <td>${alert.totalCount}</td>
            <td>${alert.failedCount}</td>
            <td><fmt:formatNumber value="${alert.failureRate}" maxFractionDigits="2" />%</td>
            <td><fmt:formatNumber value="${alert.threshold}" maxFractionDigits="2" />%</td>
            <td>${alert.createdAt}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
