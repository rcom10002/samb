<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/inc/component/jsp-common-header.jsp" %>
<c:set var="moduleKey" value="report"/> 
<so:load-module-phrase module="report" />
<so:lookup category="report.category;report.status;" />
<div role="tabpanel" class="tab-pane active" id="listView" ng-if="page.listView.visible">
	<h1><spring:message code="${moduleKey}.listViewH1" /></h1>
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading container-fluid">
			<div class="btn-group">
			    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">Action <span class="caret"></span></button>
			    <ul class="dropdown-menu" role="menu">
			        <li><a href="#">Action</a></li>
			        <li><a href="#">Another action</a></li>
			        <li><a href="#">Something else here</a></li>
			        <li class="divider"></li>
			        <li><a href="#">Separated link</a></li>
			    </ul>
			</div>
			<button type="button" class="btn btn-success" ng-click="page.addView.startAdd()">
				<span class="glyphicon glyphicon-filter" aria-hidden="true"></span>
			</button>
			<button type="button" class="btn btn-default pull-right" ng-click="page.helpView.startHelp()">
				<span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>
			</button>
		</div>
		<div class="panel-body">
			<div class="gridStyle" ng-grid="page.listView.gridOptions"></div>
		</div>
		<div class="panel-footer">
			<div class="container-fluid">
		    	<%@ include file="/WEB-INF/pages/inc/component/pagination.jsp" %>
			</div>
		</div>
	</div>
<!-- 	<canvas class="chart chart-line"       data="page.listView.chart.data" labels="page.listView.chart.labels" legend="true" series="page.listView.chart.series" click="onClick"></canvas> -->
<!-- 	<canvas class="chart chart-bar"        data="page.listView.chart.data" labels="page.listView.chart.labels"></canvas> -->
<!-- 	<canvas class="chart chart-radar"      data="[].concat(page.listView.chart.data)" labels="page.listView.chart.labels"></canvas> -->
	<canvas class="chart chart-doughnut"   data="page.listView.chart.data" labels="page.listView.chart.labels"></canvas>
	<canvas class="chart chart-pie"        data="page.listView.chart.data" labels="page.listView.chart.labels"></canvas>
	<canvas class="chart chart-polar-area" data="page.listView.chart.data" labels="page.listView.chart.labels"></canvas>
</div>
<%--
<div class="container-fluid" ng-if="page.filterView.visible">
	<h1><spring:message code="${moduleKey}.filterViewH1" /></h1>
	<div class="panel panel-default">
		<div class="panel-heading">
			<button type="button" class="btn btn-primary" ng-click="page.filterView.exitView()"><spring:message code="button.exit" /></button>
		</div>
		<div class="panel-body">
			<form role="form" id="filterView">
				<div class="container-fluid" ng-repeat="formControl in page.filterView.formControls" ng-include="'form-control-field'"></div>
			</form>
		</div>
	</div>
</div>
--%>