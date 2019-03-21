<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">
<head>
<jsp:include page="../common/head.jsp" />
    <meta charset="UTF-8">
    <title>企业展示</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/enterprise-show.css" rel="stylesheet">
    <link href="/css/icon/iconfont.css" rel="stylesheet">
</head>
<body>
<!--banner-->
<jsp:include page="/header" />
<div class="banner">
    <div class="mask"></div>
    <div class="banner-img">

        <p>${enterprise.enterpriseName}</p>
    </div>
</div>

<!--企业介绍-->
<div class="enterprise-introduce">
    <div class="logo">
        <img src="${enterprise.logo}" alt="This's logo">
    </div>
    <div class="content">
        <p>
           ${enterprise.introduce}</p>
    </div>
</div>
<!--业务范围-->
<div class="enterprise-introduce">
    <div class="content">
        <p>${enterprise.businessScope}</p>
    </div>
    <div class="logo">
        <img src="${enterprise.businessLicense}" alt="This's logo.">
    </div>
</div>

<!--企业的简介-->
<div class="introduction">
    <p>联系人：<span>${enterprise.personName}</span></p>
    <p>电话：<span>${enterprise.telephone}</span></p>
    <p>邮箱：<span>${enterprise.email}</span></p>
    <p>地址：<span>${enterprise.addressAll}</span></p>
</div>
<jsp:include page="../common/footer.jsp" />
</body>
</html>