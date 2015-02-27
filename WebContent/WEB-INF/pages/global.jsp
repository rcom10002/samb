<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/taglib/so.tld" prefix="so" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${pageContext.request.contextPath}/static/image/favicon.ico">

    <title><spring:message code="global.title"/></title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/intro/introjs.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/angular-loading-bar/loading-bar.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/angular-grid/ng-grid.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/angular-bootstrap-colorpicker/css/colorpicker.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/angular-chart.js/angular-chart.css">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/static/css/so/so.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/Font-Awesome/css/font-awesome.min.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="${pageContext.request.contextPath}/static/css/bootstrap/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body ng-app="${param.inc}" ng-controller="global-controller">

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="javascript:window.location=window.location; void(0)">Smart Office</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/book.entity"><spring:message code="global.book"/></a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Dropdown 1 <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">人事管理</li>
	            <li><a href="${pageContext.request.contextPath}/requirement.entity"><spring:message code="global.requirement"/></a></li>
	            <li><a href="${pageContext.request.contextPath}/tracking.entity"><spring:message code="global.tracking"/></a></li>
	            <li><a href="${pageContext.request.contextPath}/cost.entity"><spring:message code="global.cost"/></a></li>
                <li class="divider"></li>
                <li class="dropdown-header">Nav header</li>
	            <li><a href="${pageContext.request.contextPath}/book.entity"><spring:message code="global.book"/></a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Dropdown 2 <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">人事管理</li>
	            <li><a href="${pageContext.request.contextPath}/user.entity"><spring:message code="global.user"/></a></li>
	            <li><a href="${pageContext.request.contextPath}/unit.entity"><spring:message code="global.unit"/></a></li>
                <li class="divider"></li>
                <li class="dropdown-header">Nav header</li>
	            <li><a href="${pageContext.request.contextPath}/lookup.entity"><spring:message code="global.lookup"/></a></li>
	            <li><a href="${pageContext.request.contextPath}/news.entity"><spring:message code="global.news"/></a></li>
	            <li><a href="${pageContext.request.contextPath}/event.entity"><spring:message code="global.event"/></a></li>
	            <li><a href="${pageContext.request.contextPath}/privilege.entity"><spring:message code="global.privilege"/></a></li>
	            <li><a href="${pageContext.request.contextPath}/role.entity"><spring:message code="global.role"/></a></li>
              </ul>
            </li>
            <li><a href="${pageContext.request.contextPath}/report.entity">报表Demo</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    <so:load-module-phrase module="common" />
	<jsp:include page="/WEB-INF/pages/inc/component/solib.jsp" />
	<div class="container starter-template" ng-controller="${param.inc}-controller" ng-cloak>
		<jsp:include page="/static/html/form-control.html"></jsp:include>
		<jsp:include page="/WEB-INF/pages/inc/${param.inc}.jsp" />
	</div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/static/js/underscore-min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/angular.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/Chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/angular-chart.js/angular-chart.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/angular-sanitize.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/angular-loading-bar/loading-bar.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/angular-wysiwyg/angular-wysiwyg.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/angular-bootstrap-colorpicker/js/bootstrap-colorpicker-module.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/angular-grid/ng-grid-2.0.14.debug.js"></script>
    <script src="${pageContext.request.contextPath}/static/css/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/ui-bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/ui-bootstrap-tpls.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="${pageContext.request.contextPath}/static/css/bootstrap/js/ie10-viewport-bug-workaround.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/intro/intro.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/so/${param.inc}.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/so/angular-so-ext.js"></script>
  </body>
</html>


