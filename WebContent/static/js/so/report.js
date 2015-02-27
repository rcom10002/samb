var moduleName = "report";
var app = angular.module(moduleName, solib.angularModules);

app.controller(moduleName + "-controller", function ($scope, $http) {
	var page = new solib.Page($scope);

	/*
	 * View Model
	 */
	page.listView = new solib.PageView("list");
	page.helpView = new solib.PageView("help");

	/*
	 * Meta data for development-aid
	 */
	page._validation =
	{
		"category"    : [solib.validation.required()],
		"qty"         : [solib.validation.required()],
		"avgWeight"   : [solib.validation.required(), solib.validation.stringMaxLength(50)]
	};
	page._formControlRepository = 
	{
		"category"    : { "name": "category"    , "check": page._validation["category"    ], "label": "report.category"                    },
		"qty"         : { "name": "name"        , "check": page._validation["name"        ], "label": "report.name"     , "type": "number" },
		"avgWeight"   : { "name": "applicant"   , "check": page._validation["applicant"   ], "label": "report.applicant", "type": "number" }
	};

	var columnDefs = 
		[
            {"field": "category" , "displayName": "report.category" }, 
            {"field": "qty"      , "displayName": "report.qty"      }, 
            {"field": "avgWeight", "displayName": "report.avgWeight"}
	    ];

	page.listView.gridOptions = new solib.GridOptions("page.listView.model", columnDefs);

	/*
	 * Private methods for internal usage
	 */
	page._internalInit = function () {
		page.listView.startList(true);
	}

	/*
	 * Model actions
	 */
	page.listView.startList = function (reload) {
		page.hideAllViews();
		page.listView.visible = true;
		if (!reload) {
			return;
		}
		page.listView.gridOptions.selectedItems.length = 0;
		var uri = "/rest/" + moduleName + "/load";
		if (page.listView.pagination && page.listView.pagination.currentPageNumber) {
			uri = "/rest/" + moduleName + "/load/" + page.listView.pagination.currentPageNumber;
		}
		$http["get"](solib.uri(uri))
		.success(function(data, status, headers, config) {
			page.listView.pagination = data.pagination;
			page.listView.model = data.list;

			page.listView.chart = {};

			page.listView.chart.labels = _.map(page.listView.model, function(e) {
				return e.category;
			});
			page.listView.chart.data = _.map(page.listView.model, function(e) {
				return e.avgWeight;
			});
			page.listView.chart.series = [ "abc" ];

			page.listView.chart.labels.length = page.listView.chart.labels.length > 7 ? 7 : page.listView.chart.labels.length;
			page.listView.chart.data.length   = page.listView.chart.data.length   > 7 ? 7 : page.listView.chart.data.length  ;
		})
		.error(solib.httpErrorHandler);
	}
	page.helpView.startHelp = function () {
		page.startIntro();
	};

	/*
	 * initialization
	 */
	page._internalInit();
});
