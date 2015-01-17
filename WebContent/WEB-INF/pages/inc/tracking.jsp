<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/inc/component/jsp-common-header.jsp" %>
<c:set var="moduleKey" value="tracking"/> 
<so:load-module-phrase module="${moduleKey}" />
<so:lookup category="tracking.language,requirement.location" />
<div role="tabpanel" class="tab-pane active" id="listView" ng-if="page.listView.visible">
	<h1><spring:message code="${moduleKey}.listViewH1" /></h1>
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading container-fluid">
			<button type="button" class="btn btn-success" ng-click="page.addView.startAdd()">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			</button>
			<button type="button" class="btn btn-success" ng-click="page.addView.startCopy()" ng-disabled="page.listView.gridOptions.selectedItems.length == 0">
				<span class="glyphicon glyphicon-print" aria-hidden="true"></span>
			</button>
			<button type="button" class="btn btn-info" ng-click="page.viewView.startView()" ng-disabled="page.listView.gridOptions.selectedItems.length == 0">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
			</button>
			<button type="button" class="btn btn-primary" ng-click="page.editView.startEdit()" ng-disabled="page.listView.gridOptions.selectedItems.length == 0">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
			</button>
			<button type="button" class="btn btn-danger" ng-click="page.deleteView.startDelete()" ng-disabled="page.listView.gridOptions.selectedItems.length == 0">
				<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
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
</div>

<div class="container-fluid" ng-if="page.addView.visible">
	<h1><spring:message code="${moduleKey}.addViewH1" /></h1>
	<div class="panel panel-default">
		<div class="panel-heading">
			<button type="button" class="btn btn-primary" ng-click="page.addView.confirmAdd()"><spring:message code="button.submit" /></button>
			<button type="button" class="btn btn-warning" ng-click="page.addView.cancelAdd()"><spring:message code="button.cancel" /></button>
		</div>
		<div class="panel-body">
			<form role="form" id="addView">
				<div class="container-fluid" ng-repeat="formControl in page.addView.formControls" ng-include="'form-control-field'"></div>
			</form>
		</div>
	</div>
</div>
<div class="container-fluid" ng-if="page.viewView.visible">
	<h1><spring:message code="${moduleKey}.viewViewH1" /></h1>
	<div class="panel panel-default">
		<div class="panel-heading">
			<button type="button" class="btn btn-primary" ng-click="page.viewView.exitView()"><spring:message code="button.exit" /></button>
		</div>
		<div class="panel-body">
			<form role="form" id="viewView">
				<div class="container-fluid" ng-repeat="formControl in page.viewView.formControls" ng-include="'form-control-field'"></div>
			</form>
		</div>
	</div>
</div>
<div class="container-fluid" ng-if="page.editView.visible">
	<h1><spring:message code="${moduleKey}.editViewH1" /></h1>
	<div class="panel panel-default">
		<div class="panel-heading">
			<button type="button" class="btn btn-primary" ng-click="page.editView.confirmEdit()"><spring:message code="button.submit" /></button>
			<button type="button" class="btn btn-warning" ng-click="page.editView.cancelEdit()"><spring:message code="button.cancel" /></button>
		</div>
		<div class="panel-body">
			<form role="form" id="editView">
				<div class="container-fluid" ng-repeat="formControl in page.editView.formControls" ng-include="'form-control-field'"></div>
			</form>
		</div>
	</div>
</div>
