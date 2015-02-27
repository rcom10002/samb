var moduleName = "requirement";
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

	/*
	 * Meta data for development-aid
	 */
	page._validation =
	{
		"corp"    : [solib.validation.required(), solib.validation.stringMaxLength(50)],
		"location": [solib.validation.required()],
		"target"  : [solib.validation.required()],
		"manager" : [solib.validation.required()],
		"position": [solib.validation.required()],
		"quantity": [solib.validation.required(), solib.validation.numberValid(), solib.validation.numberIntegerOnly(), solib.validation.numberMin(1), solib.validation.numberMax(1000)],
	    "status"  : [solib.validation.required()],
	    "hr"      : [solib.validation.required()],
	    "comment" : []
	};
	page._formControlRepository = 
		{
			"corp"    : { "name": "corp"    , "check": page._validation["corp"    ], "label": "requirement.corp"    ,                                },
			"location": { "name": "location", "check": page._validation["location"], "label": "requirement.location", "type": "select", "select-source": "requirement.location"    },
			"target"  : { "name": "target"  , "check": page._validation["target"  ], "label": "requirement.target"  ,                                },
			"manager" : { "name": "manager" , "check": page._validation["manager" ], "label": "requirement.manager" ,                                },
			"position": { "name": "position", "check": page._validation["position"], "label": "requirement.position",                                },
			"quantity": { "name": "quantity", "check": page._validation["quantity"], "label": "requirement.quantity"/*, "directive": "spinner"*/     },
			"status"  : { "name": "status"  , "check": page._validation["status"  ], "label": "requirement.status"  , "type": "select", "select-source": "requirement.status"    },
			"hr"      : { "name": "hr"      , "check": page._validation["hr"      ], "label": "requirement.hr"      ,                                },
			"comment" : { "name": "comment" , "check": page._validation["comment" ], "label": "requirement.comment" , "type": "textarea"        },
			"createAt": { "name": "createAt", "check": page._validation["createAt"], "label": "common.createAt"     , "type": "datetime"        },
			"updateAt": { "name": "updateAt", "check": page._validation["updateAt"], "label": "common.updateAt"     , "type": "datetime"        }
		};

	/*
	 * Form controls
	 */
	page.addView.formControls =
		[
			page._formControlRepository["corp"    ],
			page._formControlRepository["location"],
			page._formControlRepository["target"  ],
			page._formControlRepository["manager" ],
			page._formControlRepository["position"],
			page._formControlRepository["quantity"],
			page._formControlRepository["status"  ],
			page._formControlRepository["hr"      ],
			page._formControlRepository["comment" ]
		];
	page.viewView.formControls =
		[
			page.renew(angular.copy(page._formControlRepository["corp"    ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["location"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["target"  ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["manager" ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["position"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["quantity"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["status"  ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["hr"      ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["comment" ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["createAt"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"]), {"readonly": true})
		];
	page.editView.formControls =
		[
			page._formControlRepository["corp"    ],
			page._formControlRepository["location"],
			page._formControlRepository["target"  ],
			page._formControlRepository["manager" ],
			page._formControlRepository["position"],
			page._formControlRepository["quantity"],
			page._formControlRepository["status"  ],
			page._formControlRepository["hr"      ],
			page._formControlRepository["comment" ],
			page.renew(angular.copy(page._formControlRepository["createAt"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"]), {"readonly": true})
		];

	var columnDefs =
		[
			{ "field": "corp"    , "displayName": "requirement.corp"    , "minWidth": 320},
			{ "field": "location", "displayName": "requirement.location" },
			{ "field": "target"  , "displayName": "requirement.target"   },
			{ "field": "manager" , "displayName": "requirement.manager"  },
			{ "field": "position", "displayName": "requirement.position" },
			{ "field": "quantity", "displayName": "requirement.quantity" },
			{ "field": "status"  , "displayName": "requirement.status"   },
			{ "field": "hr"      , "displayName": "requirement.hr"       },
			{ "field": "comment" , "displayName": "requirement.comment"  },
			{ "field": "createAt", "displayName": "common.createAt"      },
			{ "field": "updateAt", "displayName": "common.updateAt"      }
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
