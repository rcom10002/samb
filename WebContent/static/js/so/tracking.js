var moduleName = "tracking";
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

	function reqirementLinkedHandler(currentView) {
		$scope.control.openLinked('/rest/linked/requirement', function (selectedItem) {
			page[currentView].model._toRequirement = selectedItem.val;
			page[currentView].model.toRequirement = selectedItem.name;
		});
	}

	function fileHandler(currentView) {
		return function (files) {
			page[currentView].model._files = files;
			page[currentView].model.files  = (files && files.length > 0) ? files.join("/") : "";
		};
	}

	/*
	 * Meta data for development-aid
	 */
	page._validation =
	{
		"toRequirement"   : [solib.validation.required()],
		"candidate"       : [solib.validation.required(), solib.validation.unique(page.listView, "candidate")],
		"phone"           : [solib.validation.required(), solib.validation.allowChars("0123456789-")],
		"hr"              : [solib.validation.required()],
		"workLocation"    : [solib.validation.required()],
		"skill"           : [solib.validation.required()],
		"experience"      : [solib.validation.required(), solib.validation.numberIntegerOnly(), solib.validation.numberRange(1, 50)],
	    "language"        : [solib.validation.required()],
	    "employer"        : [solib.validation.required()],
	    "level"           : [solib.validation.required()],
	    "remarks"         : [],
	    "toSubmitResumeAt": [solib.validation.required()],
	    "interviewResult" : [],
	    "interviewTime"   : [solib.validation.required()],
	    "expectedSalary"  : [solib.validation.required(), solib.validation.numberValid(), solib.validation.numberIntegerOnly(), solib.validation.numberMin(100)],
	    "files"           : []
	};
	page._formControlRepository = 
	{
		"toRequirement"   : {"name": "toRequirement"    , "check": page._validation["toRequirement"    ], "label": "tracking.toRequirement"   , "type": "linked", "linked-handler": reqirementLinkedHandler },
        "candidate"       : {"name": "candidate"        , "check": page._validation["candidate"        ], "label": "tracking.candidate"                                     },
        "phone"           : {"name": "phone"            , "check": page._validation["phone"            ], "label": "tracking.phone"                                         },
        "hr"              : {"name": "hr"               , "check": page._validation["hr"               ], "label": "tracking.hr"                                            },
        "workLocation"    : {"name": "workLocation"     , "check": page._validation["workLocation"     ], "label": "tracking.workLocation"    , "type": "select", "select-source": "requirement.location"},
        "skill"           : {"name": "skill"            , "check": page._validation["skill"            ], "label": "tracking.skill"                                         },
        "experience"      : {"name": "experience"       , "check": page._validation["experience"       ], "label": "tracking.experience"                                    },
        "language"        : {"name": "language"         , "check": page._validation["language"         ], "label": "tracking.language"        , "type": "select", "select-source": "tracking.language"   },
        "employer"        : {"name": "employer"         , "check": page._validation["employer"         ], "label": "tracking.employer"                                      },
        "level"           : {"name": "level"            , "check": page._validation["level"            ], "label": "tracking.level"                                         },
        "remarks"         : {"name": "remarks"          , "check": page._validation["remarks"          ], "label": "tracking.remarks"                                       },
        "toSubmitResumeAt": {"name": "toSubmitResumeAt" , "check": page._validation["toSubmitResumeAt" ], "label": "tracking.toSubmitResumeAt", "type": "date"              },
        "interviewResult" : {"name": "interviewResult"  , "check": page._validation["interviewResult"  ], "label": "tracking.interviewResult"                               },
        "interviewTime"   : {"name": "interviewTime"    , "check": page._validation["interviewTime"    ], "label": "tracking.interviewTime"   , "type": "datetime"          },
        "expectedSalary"  : {"name": "expectedSalary"   , "check": page._validation["expectedSalary"   ], "label": "tracking.expectedSalary"  , "type": "number"            },
        "files"           : {"name": "files"            , "check": page._validation["files"            ], "label": "tracking.files"           , "type": "files", "file-handler": fileHandler },
        "createAt"        : {"name": "createAt"         , "check": page._validation["createAt"         ], "label": "common.createAt"          , "type": "datetime"          },
        "updateAt"        : {"name": "updateAt"         , "check": page._validation["updateAt"         ], "label": "common.updateAt"          , "type": "datetime"          },
	};

	/*
	 * Form controls
	 */
	page.addView.formControls =
		[
			page._formControlRepository["toRequirement"   ],
			page._formControlRepository["candidate"       ],
			page._formControlRepository["phone"           ],
			page._formControlRepository["hr"              ],
			page._formControlRepository["workLocation"    ],
			page._formControlRepository["skill"           ],
			page._formControlRepository["experience"      ],
			page._formControlRepository["language"        ],
			page._formControlRepository["employer"        ],
			page._formControlRepository["level"           ],
			page._formControlRepository["remarks"         ],
			page._formControlRepository["toSubmitResumeAt"],
			page._formControlRepository["interviewResult" ],
			page._formControlRepository["interviewTime"   ],
			page._formControlRepository["expectedSalary"  ],
			page._formControlRepository["files"           ]
		];
	page.viewView.formControls =
		[
			page.renew(angular.copy(page._formControlRepository["toRequirement"   ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["candidate"       ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["phone"           ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["hr"              ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["workLocation"    ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["skill"           ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["experience"      ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["language"        ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["employer"        ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["level"           ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["remarks"         ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["toSubmitResumeAt"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["interviewResult" ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["interviewTime"   ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["expectedSalary"  ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["files"           ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["createAt"        ]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"        ]), {"readonly": true})
		];
	page.editView.formControls =
		[
			page._formControlRepository["toRequirement"   ],
			page._formControlRepository["candidate"       ],
			page._formControlRepository["phone"           ],
			page._formControlRepository["hr"              ],
			page._formControlRepository["workLocation"    ],
			page._formControlRepository["skill"           ],
			page._formControlRepository["experience"      ],
			page._formControlRepository["language"        ],
			page._formControlRepository["employer"        ],
			page._formControlRepository["level"           ],
			page._formControlRepository["remarks"         ],
			page._formControlRepository["toSubmitResumeAt"],
			page._formControlRepository["interviewResult" ],
			page._formControlRepository["interviewTime"   ],
			page._formControlRepository["expectedSalary"  ],
			page._formControlRepository["files"           ],
			page.renew(angular.copy(page._formControlRepository["createAt"]), {"readonly": true}),
			page.renew(angular.copy(page._formControlRepository["updateAt"]), {"readonly": true})
		];

	var columnDefs = 
		[
  			{"field": "toRequirement"    , "displayName": "tracking.toRequirement"   , "minWidth": 320},
			{"field": "candidate"        , "displayName": "tracking.candidate"       },
			{"field": "phone"            , "displayName": "tracking.phone"           },
			{"field": "hr"               , "displayName": "tracking.hr"              },
			{"field": "workLocation"     , "displayName": "tracking.workLocation"    },
			{"field": "skill"            , "displayName": "tracking.skill"           },
			{"field": "experience"       , "displayName": "tracking.experience"      },
			{"field": "language"         , "displayName": "tracking.language"        },
			{"field": "employer"         , "displayName": "tracking.employer"        },
			{"field": "level"            , "displayName": "tracking.level"           },
			{"field": "remarks"          , "displayName": "tracking.remarks"         },
			{"field": "toSubmitResumeAt" , "displayName": "tracking.toSubmitResumeAt"},
			{"field": "interviewResult"  , "displayName": "tracking.interviewResult" },
			{"field": "interviewTime"    , "displayName": "tracking.interviewTime"   },
			{"field": "expectedSalary"   , "displayName": "tracking.expectedSalary"  },
			{"field": "createAt"         , "displayName": "common.createAt"          },
			{"field": "updateAt"         , "displayName": "common.updateAt"          }
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
