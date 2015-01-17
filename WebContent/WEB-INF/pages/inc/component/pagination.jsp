<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="container-fluid">
    <pagination ng-change="page.listView.startList(true)"
    	total-items="page.listView.pagination.rowCount"
    	items-per-page="page.listView.pagination.pageSize"
    	ng-model="page.listView.pagination.currentPageNumber"
    	class="pagination-sm pull-right" boundary-links="true" rotate="true"
    	first-text="<spring:message code="common.pagination.first"/>"
    	previous-text="<spring:message code="common.pagination.up"/>"
    	next-text="<spring:message code="common.pagination.down"/>"
    	last-text="<spring:message code="common.pagination.last"/>"
    	></pagination>
</div>
