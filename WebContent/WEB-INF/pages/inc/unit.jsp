<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/inc/component/jsp-common-header.jsp" %>
<c:set var="moduleKey" value="unit"/> 
<so:load-module-phrase module="unit" />
<so:lookup category="unit.category;unit.status;" />
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
			<!-- ENTRIES START -->
			<div class="container-fluid">
				<div class="panel panel-default">
					<div class="panel-heading"><h2>成员编辑</h2></div>
					<div class="panel-body">
						<form class="form-inline">
							<div class="form-group">
								<div class="input-group">
								    <span class="input-group-addon">新成员</span>
								    <input type="text" class="form-control" id="input-addView-user" ng-model="page.addView.model._entryModel['_memberId']" readonly />
							        <span class="input-group-btn"><button class="btn btn-default" type="button" ng-click="page.memberLinkedHandler('addView')"><span class="glyphicon glyphicon-link"></span></button></span>
								    <input type="hidden" ng-model="page.addView.model._entryModel['memberId']" />
								</div>
								<button type="button" class="btn btn-primary" ng-click="page.addView.model.entryModelList.push(page.addView.model._entryModel); page.addView.model._entryModel={};">添加</button>
							</div>
						</form>
						<div class="alert alert-warning" role="alert" ng-if="page.addView.model.entryModelList.length == 0">暂无数据</div>
						<table class="table" ng-if="page.addView.model.entryModelList.length > 0">
							<thead>
								<tr>
									<th></th>
									<th>编号</th>
									<th>成员</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="entryModel in page.addView.model.entryModelList">
									<td><button ng-click="page.addView.model.entryModelList.splice($index, 1)" type="button" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
									<td>{{$index + 1}}</td>
									<td>{{entryModel._memberId}}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- ENTRIES END -->
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
			<!-- ENTRIES START -->
			<div class="container-fluid">
				<div class="panel panel-default">
					<div class="panel-heading"><h2>成员列表</h2></div>
					<div class="panel-body">
						<div class="alert alert-warning" role="alert" ng-if="page.viewView.model.entryModelList.length == 0">暂无数据</div>
						<table class="table" ng-if="page.viewView.model.entryModelList.length > 0">
							<thead>
								<tr>
									<th>编号</th>
									<th>成员</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="entryModel in page.viewView.model.entryModelList">
									<td>{{$index + 1}}</td>
									<td>{{entryModel._memberId}}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- ENTRIES END -->
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
			<!-- ENTRIES START -->
			<div class="container-fluid">
				<div class="panel panel-default">
					<div class="panel-heading"><h2>成员编辑</h2></div>
					<div class="panel-body">
						<form class="form-inline">
							<div class="form-group">
								<div class="input-group">
								    <span class="input-group-addon">新成员</span>
								    <input type="text" class="form-control" id="input-editView-user" ng-model="page.editView.model._entryModel['_memberId']" readonly />
							        <span class="input-group-btn"><button class="btn btn-default" type="button" ng-click="page.memberLinkedHandler('editView')"><span class="glyphicon glyphicon-link"></span></button></span>
								    <input type="hidden" ng-model="page.editView.model._entryModel['memberId']" />
								</div>
								<button type="button" class="btn btn-primary" ng-click="page.editView.model.entryModelList.push(page.editView.model._entryModel); page.editView.model._entryModel={};">添加</button>
							</div>
						</form>
						<div class="alert alert-warning" role="alert" ng-if="page.editView.model.entryModelList.length == 0">暂无数据</div>
						<table class="table" ng-if="page.editView.model.entryModelList.length > 0">
							<thead>
								<tr>
									<th></th>
									<th>编号</th>
									<th>成员</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="entryModel in page.editView.model.entryModelList">
									<td><button ng-click="page.editView.model.entryModelList.splice($index, 1)" type="button" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
									<td>{{$index + 1}}</td>
									<td>{{entryModel._memberId}}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- ENTRIES END -->
		</div>
	</div>
</div>
