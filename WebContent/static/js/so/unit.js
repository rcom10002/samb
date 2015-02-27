var moduleName = "unit";
var app = angular.module(moduleName, solib.angularModules);

app.controller(moduleName + "-controller", function ($scope, $http) {
	var page = new solib.Page($scope);

	/*
	 * View Model
	 */
	page.listView = new solib.PageView("list");
	page.addView = new solib.PageView("add");
	page.viewView = new solib.PageView("view");
	page.editView = new solib.PageView("edit");
	page.deleteView = new solib.PageView("delete");
	page.helpView = new solib.PageView("help");

	function unitLinkedHandler(currentView) {
		$scope.control.openLinked('/rest/linked/unit', function (selectedItem) {
			page[currentView].model._parentUnit  = selectedItem.val;
			page[currentView].model.parentUnitId = selectedItem.name;
			page[currentView].model.parentUnit   = selectedItem.name;
		});
	}

	function memberLinkedHandler(currentView) {
		$scope.control.openLinked('/rest/linked/member', function (selectedItem) {
			var entryModel = page[currentView].model._entryModel || {};
			page[currentView].model._entryModel = entryModel || {};
			entryModel._memberId = selectedItem.val;
			entryModel.memberId = selectedItem.name;
		});
	}
	page.memberLinkedHandler = memberLinkedHandler;
	page.addMember = function () {
	};
	page.removeMember = function () {
	};

	/*
	 * Meta data for development-aid
	 */
	page._validation =
	{
		"parentUnit": [solib.validation.required()],
		"name"      : [solib.validation.required(), solib.validation.stringMinLength(3), solib.validation.stringMaxLength(30), solib.validation.mustMatch(/^[\u4E00-\u9FFFa-z-]+$/i, "solib.validation.message.custom.chinese.alpha.num.strike.only")],
		"status"    : [solib.validation.required()],
		"category"  : [solib.validation.required()],
		"remark"    : [solib.validation.stringMaxLength(20)]
	};
	page._formControlRepository = 
	{
		"parentUnit": { "name": "parentUnit", "check": page._validation["parentUnit"], "label": "unit.parentUnit", "type": "linked", "linked-handler": unitLinkedHandler },
	    "name"      : { "name": "name"      , "check": page._validation["name"      ], "label": "unit.name"      , "type": "" },
	    "status"    : { "name": "status"    , "check": page._validation["status"    ], "label": "unit.status"    , "type": "select", "select-source": "unit.status" },
	    "category"  : { "name": "category"  , "check": page._validation["category"  ], "label": "unit.category"  , "type": "select", "select-source": "unit.category" },
	    "remark"    : { "name": "remark"    , "check": page._validation["remark"    ], "label": "unit.remark"    , "type": "" },
	    "createAt"  : { "name": "createAt"  , "check": page._validation["createAt"  ], "label": "common.createAt", "type": "datetime" },
		"updateAt"  : { "name": "updateAt"  , "check": page._validation["updateAt"  ], "label": "common.updateAt", "type": "datetime" }
	};

	/*
	 * Form controls
	 */
	page.addView.formControls =
		[
		 	page._formControlRepository["parentUnit"],
            page._formControlRepository["name"      ],
            page._formControlRepository["remark"    ],
            page._formControlRepository["status"    ],
            page._formControlRepository["category"  ]
		];
	page.viewView.formControls =
		[
		 	page.renew(angular.copy(page._formControlRepository["parentUnit"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["name"      ]), {"readonly": true}),
            page.renew(angular.copy(page._formControlRepository["remark"    ]), {"readonly": true}),
            page.renew(angular.copy(page._formControlRepository["status"    ]), {"readonly": true}),
            page.renew(angular.copy(page._formControlRepository["category"  ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["createAt"  ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"  ]), {"readonly": true})
		];
	page.editView.formControls =
		[
		 	page._formControlRepository["parentUnit"],
		 	page._formControlRepository["name"      ],
            page._formControlRepository["remark"    ],
            page._formControlRepository["status"    ],
            page._formControlRepository["category"  ],
			page.renew(angular.copy(page._formControlRepository["createAt"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"]), {"readonly": true})
		];

	var columnDefs = 
		[
		 	{ "field": "parentUnit", "displayName": "unit.parentUnit"},
		    { "field": "name"      , "displayName": "unit.name"      },
		    { "field": "remark"    , "displayName": "unit.remark"    },
		    { "field": "status"    , "displayName": "unit.status"    },
		    { "field": "category"  , "displayName": "unit.category"  },
            {"field": "createAt"   , "displayName": "common.createAt"}, 
            {"field": "updateAt"   , "displayName": "common.updateAt"} 
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
		})
		.error(solib.httpErrorHandler);
	}
	page.addView.startAdd = function () {
		page.formControlView = page.addView;
		page.addView.model = new solib.EntityModel();
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
