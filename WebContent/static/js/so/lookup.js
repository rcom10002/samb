var moduleName = "lookup";
var app = angular.module(moduleName, solib.angularModules);

app.controller(moduleName + "-controller", function ($scope, $http) {
	var page = new solib.Page($scope);

	/*
	 * View Model
	 */
	page.listView = new solib.PageViewModel("list");
	page.addView = new solib.PageViewModel("add");
	page.viewView = new solib.PageViewModel("view");
	page.editView = new solib.PageViewModel("edit");
	page.deleteView = new solib.PageViewModel("delete");
	page.helpView = new solib.PageViewModel("help");

	/*
	 * Meta data for development-aid
	 */
	page._validation =
	{
		"category": [solib.validation.required(), solib.validation.stringMaxLength(50)],
		"name"    : [solib.validation.required(), solib.validation.stringMaxLength(50)],
		"val"     : [solib.validation.required(), solib.validation.stringMaxLength(100)],
		"weight"  : [solib.validation.required(), solib.validation.numberIntegerOnly(), solib.validation.numberMax(999999999999)]
	};
	page._formControlRepository = 
	{
		"category": { "name": "category", "check": page._validation["category"], "label": "lookup.category",                           },
		"name"    : { "name": "name"    , "check": page._validation["name"    ], "label": "lookup.name"    ,                           },
		"val"     : { "name": "val"     , "check": page._validation["val"     ], "label": "lookup.val"     ,                           },
		"weight"  : { "name": "weight"  , "check": page._validation["weight"  ], "label": "lookup.weight"  ,                           },
		"createAt": { "name": "createAt", "check": page._validation["createAt"], "label": "common.createAt", "type": "datetime" },
		"updateAt": { "name": "updateAt", "check": page._validation["updateAt"], "label": "common.updateAt", "type": "datetime" }
	};

	/*
	 * Form controls
	 */
	page.addView.formControls =
		[
			page._formControlRepository["category"],
			page._formControlRepository["name"    ],
			page._formControlRepository["val"     ],
			page._formControlRepository["weight"  ]
		];
	page.viewView.formControls =
		[
			page.renew(angular.copy(page._formControlRepository["category"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["name"    ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["val"     ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["weight"  ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["createAt"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"]), {"readonly": true})
		];
	page.editView.formControls =
		[
			page._formControlRepository["category"],
			page._formControlRepository["name"    ],
			page._formControlRepository["val"     ],
			page._formControlRepository["weight"  ],
			page.renew(angular.copy(page._formControlRepository["createAt"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"]), {"readonly": true})
		];

	var columnDefs =
		[
            {"field": "category", "displayName": "lookup.category"},
            {"field": "name",     "displayName": "lookup.name"},
            {"field": "val",      "displayName": "lookup.val"},
            {"field": "weight",   "displayName": "lookup.weight"}
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
		$http["get"](solib.uri(uri), page.addView.model)
		.success(function(data, status, headers, config) {
			page.listView.pagination = data.pagination;
			page.listView.model = data.list;
		})
		.error(solib.httpErrorHandler);
	}
	page.addView.startAdd = function () {
		page.formControlView = page.addView;
		page.addView.model = { };
		page.hideAllViews();
		page.addView.visible = true;
	};
	page.addView.startCopy = function () {
		page.formControlView = page.addView;
		$.each(page.listView.model, function (i, e) {
			if (e.id == page.listView.gridOptions.selectedItems[0].id) {
				page.addView.model = angular.copy(e);
			}
		});
		page.hideAllViews();
		page.addView.visible = true;
	};
	page.addView.confirmAdd = function () {
		if (!page.addView.isModelPerfect) { $scope.control.openAlert("common.message.type.warn", "rest.request.message.incorrectData"); return };
		$http["put"](solib.uri("/rest/" + moduleName + "/add"), page.addView.model)
		.success(page._internalInit)
		.error(solib.httpErrorHandler);
	};
	page.addView.cancelAdd = function () {
		page.listView.startList();
	};
	page.viewView.startView = function () {
		$.each(page.listView.model, function (i, e) {
			if (e.id == page.listView.gridOptions.selectedItems[0].id) {
				page.viewView.model = angular.copy(e);
			}
		});
		page.formControlView = page.viewView;
		page.hideAllViews();
		page.viewView.visible = true;
	};
	page.viewView.exitView = function () {
		page.listView.startList();
	};
	page.editView.startEdit = function () {
		$.each(page.listView.model, function (i, e) {
			if (e.id == page.listView.gridOptions.selectedItems[0].id) {
				page.editView.model = angular.copy(e);
			}
		});
		page.formControlView = page.editView;
		page.hideAllViews();
		page.editView.visible = true;
	};
	page.editView.confirmEdit = function () {
		if (!page.editView.isModelPerfect) { $scope.control.openAlert("common.message.type.warn", "rest.request.message.incorrectData"); return };
		$http["post"](solib.uri("/rest/" + moduleName + "/edit"), page.editView.model)
		.success(page._internalInit)
		.error(solib.httpErrorHandler);
	};
	page.editView.cancelEdit = function () {
		page.listView.startList();
	};
	page.deleteView.startDelete = function () {
		$scope.control.openConfirm("common.message.type.warn", "common.message.action.delete", page.deleteView.confirmDelete);
	};
	page.deleteView.confirmDelete = function () {
		$http["delete"](solib.uri("/rest/" + moduleName + "/delete/" + page.listView.gridOptions.selectedItems[0].id))
		.success(page._internalInit)
		.error(solib.httpErrorHandler);
	};
	page.helpView.startHelp = function () {
		page.startIntro();
	};

	/*
	 * initialization
	 */
	page._internalInit();
});
