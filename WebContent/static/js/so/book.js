var moduleName = "book";
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

	function fileHandler(currentView) {
		return function (files) {
			page[currentView].model._mailEvidence = files;
			page[currentView].model.mailEvidence  = (files && files.length > 0) ? files.join("/") : "";
		};
	}

	/*
	 * Meta data for development-aid
	 */
	page._validation =
	{
	    "name"          : [solib.validation.required(),solib.validation.stringMaxLength(20)],
	    "category"      : [solib.validation.required()],
	    "dateOfIssue"   : [solib.validation.required()],
	    "summary"       : [solib.validation.required()],
	    "dateOfPurchase": [solib.validation.required()]
	};
	page._formControlRepository = 
	{
		"name"          : { "name": "name"          , "check": page._validation["name"          ], "label": "book.name"          , "type": "" },
		"category"      : { "name": "category"      , "check": page._validation["category"      ], "label": "book.category"      , "type": "select", "select-source": "book.category" },
		"dateOfIssue"   : { "name": "dateOfIssue"   , "check": page._validation["dateOfIssue"   ], "label": "book.dateOfIssue"   , "type": "date" },
		"summary"       : { "name": "summary"       , "check": page._validation["summary"       ], "label": "book.summary"       , "type": "richtext" },
		"dateOfPurchase": { "name": "dateOfPurchase", "check": page._validation["dateOfPurchase"], "label": "book.dateOfPurchase", "type": "date" },
		"createAt"    : { "name": "createAt"    , "check": page._validation["createAt"    ], "label": "common.createAt"  , "type": "datetime" },
		"updateAt"    : { "name": "updateAt"    , "check": page._validation["updateAt"    ], "label": "common.updateAt"  , "type": "datetime" }
	};

	/*
	 * Form controls
	 */
	page.addView.formControls =
		[
            page._formControlRepository["name"          ],
            page._formControlRepository["category"      ],
            page._formControlRepository["dateOfIssue"   ],
            page._formControlRepository["summary"       ],
            page._formControlRepository["dateOfPurchase"]
		];
	page.viewView.formControls =
		[
			page.renew(angular.copy(page._formControlRepository["name"          ]), {"readonly": true}),
            page.renew(angular.copy(page._formControlRepository["category"      ]), {"readonly": true}),
            page.renew(angular.copy(page._formControlRepository["dateOfIssue"   ]), {"readonly": true}),
            page.renew(angular.copy(page._formControlRepository["summary"       ]), {"readonly": true}),
            page.renew(angular.copy(page._formControlRepository["dateOfPurchase"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["createAt"    ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"    ]), {"readonly": true})
		];
	page.editView.formControls =
		[
		 	page._formControlRepository["name"          ],
            page._formControlRepository["category"      ],
            page._formControlRepository["dateOfIssue"   ],
            page._formControlRepository["summary"       ],
            page._formControlRepository["dateOfPurchase"],
			page.renew(angular.copy(page._formControlRepository["createAt"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"]), {"readonly": true})
		];

	var columnDefs = 
		[
            { "field": "name"          , "displayName": "book.name"           },
            { "field": "category"      , "displayName": "book.category"       },
            { "field": "dateOfIssue"   , "displayName": "book.dateOfIssue"    },
            //{ "field": "summary"       , "displayName": "book.summary"        },
            { "field": "dateOfPurchase", "displayName": "book.dateOfPurchase" },
            {"field": "createAt"    , "displayName": "common.createAt"  }, 
            {"field": "updateAt"    , "displayName": "common.updateAt"  } 
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