var moduleName = "user";
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
			"username": [solib.validation.required(), solib.validation.stringMinLength(3), solib.validation.stringMaxLength(20)],
			"pwd": [solib.validation.required(), solib.validation.stringMinLength(6), solib.validation.stringMaxLength(50)],
			"pwdCreateAt": [solib.validation.required()],
			"pwdExpDays": [solib.validation.required()],
			"status": [solib.validation.required()],
			"role": [solib.validation.required()],
			"birth": [solib.validation.required()],
			"gender": [solib.validation.required()],
			"idCard": [solib.validation.required(), solib.validation.allowChars("0123456789X"), solib.validation.mustMatch(/^[0-9]{17}[0-9X]$/) ]
	};
	page._formControlRepository = 
	{
	    "username"   : { "name": "username"    , "check": page._validation["username"   ], "label": "user.username"    , "type": "" },
	    "pwd"        : { "name": "pwd"         , "check": page._validation["pwd"        ], "label": "user.pwd"         , "type": "" },
	    "pwdCreateAt": { "name": "pwdCreateAt" , "check": page._validation["pwdCreateAt"], "label": "user.pwdCreateAt" , "type": "datetime" },
	    "pwdExpDays" : { "name": "pwdExpDays"  , "check": page._validation["pwdExpDays" ], "label": "user.pwdExpDays"  , "type": "number" },
	    "status"     : { "name": "status"      , "check": page._validation["status"     ], "label": "user.status"      , "type": "select", "select-source": "user.status" },
	    "role"       : { "name": "role"        , "check": page._validation["role"       ], "label": "user.role"        , "type": "select", "select-source": "user.role"  },
	    "birth"      : { "name": "birth"       , "check": page._validation["birth"      ], "label": "user.birth"       , "type": "date" },
	    "gender"     : { "name": "gender"      , "check": page._validation["gender"     ], "label": "user.gender"      , "type": "select", "select-source": "user.gender"  },
	    "idCard"     : { "name": "idCard"      , "check": page._validation["idCard"     ], "label": "user.idCard"      , "type": "" },
		"createAt"   : { "name": "createAt"    , "check": page._validation["createAt"   ], "label": "common.createAt"  , "type": "datetime" },
		"updateAt"   : { "name": "updateAt"    , "check": page._validation["updateAt"   ], "label": "common.updateAt"  , "type": "datetime" }
	};

	/*
	 * Form controls
	 */
	page.addView.formControls =
		[
			page._formControlRepository["username"   ],
			page._formControlRepository["pwd"        ],
			page._formControlRepository["pwdCreateAt"],
			page._formControlRepository["pwdExpDays" ],
			page._formControlRepository["status"     ],
			page._formControlRepository["role"       ],
			page._formControlRepository["birth"      ],
			page._formControlRepository["gender"     ],
			page._formControlRepository["idCard"     ]
		];
	page.viewView.formControls =
		[
			page.renew(angular.copy(page._formControlRepository["username"   ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["pwd"        ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["pwdCreateAt"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["pwdExpDays" ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["status"     ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["role"       ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["birth"      ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["gender"     ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["idCard"     ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["createAt"   ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"   ]), {"readonly": true})
		];
	page.editView.formControls =
		[
		 	page._formControlRepository["username"   ],
			page._formControlRepository["pwd"        ],
			page._formControlRepository["pwdCreateAt"],
			page._formControlRepository["pwdExpDays" ],
			page._formControlRepository["status"     ],
			page._formControlRepository["role"       ],
			page._formControlRepository["birth"      ],
			page._formControlRepository["gender"     ],
			page._formControlRepository["idCard"     ],
			page.renew(angular.copy(page._formControlRepository["createAt"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"]), {"readonly": true})
		];

	var columnDefs = 
		[
			{"field": "username"    , "displayName": "user.username"   },
			{"field": "pwd"         , "displayName": "user.pwd"        },
			{"field": "pwdCreateAt" , "displayName": "user.pwdCreateAt"},
			{"field": "pwdExpDays"  , "displayName": "user.pwdExpDays" },
			{"field": "status"      , "displayName": "user.status"     },
			{"field": "role"        , "displayName": "user.role"       },
			{"field": "birth"       , "displayName": "user.birth"      },
			{"field": "gender"      , "displayName": "user.gender"     },
			{"field": "idCard"      , "displayName": "user.idCard"     },
            {"field": "createAt"    , "displayName": "common.createAt" }, 
            {"field": "updateAt"    , "displayName": "common.updateAt" } 
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
