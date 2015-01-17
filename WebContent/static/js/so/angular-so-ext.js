var app = app || {};
app.controller("global-controller", function($scope, $http, $modal, $log) {

	var control = control || { cache: { } };
	$scope.control = control;

	$scope.control.phrase = {
		"button-add"             : solib.phrase('control.uploadfile.add'      ),
		"button-remove"          : solib.phrase('control.uploadfile.remove'   ),
		"button-action"          : solib.phrase('control.uploadfile.action'   ),
		"button-upload"          : solib.phrase('control.uploadfile.upload'   ),
		"button-reset"           : solib.phrase('control.uploadfile.reset'    ),
		"button-abort"           : solib.phrase('control.uploadfile.abort'    ),
		"button-complete"        : solib.phrase('control.uploadfile.complete' ),
		"button-cancel"          : solib.phrase('button.cancel'               ),
		"button-submit"          : solib.phrase('button.submit'               ),
		"button-ok"              : solib.phrase('button.ok'                   ),
		"button-exit"            : solib.phrase('button.exit'                 ),
		"datepicker-clear"       : solib.phrase('control.datepicker.clear'    ),
		"datepicker-close"       : solib.phrase('control.datepicker.close'    ),
		"datepicker-today"       : solib.phrase('control.datepicker.today'    ),
		"datepicker-morning"     : solib.phrase('control.datepicker.morning'  ),
		"datepicker-afternoon"   : solib.phrase('control.datepicker.afternoon'),
		"pagination-first"       : solib.phrase('common.pagination.first'     ),
		"pagination-up"          : solib.phrase('common.pagination.up'        ),
		"pagination-down"        : solib.phrase('common.pagination.down'      ),
		"pagination-last"        : solib.phrase('common.pagination.last'      )
	};

	$scope.control.openDatepicker = function ($event) {
		$event.preventDefault();
		$event.stopPropagation();
		// for (var i in control) {
		//	  console.log((i + ""));
		//	  if ((i + "").length != (i + "").replace(/visible/, "").length) { 
		//		  control[i] = false;
		//	  }
		// }
		control[($event.target || $event.srcElement).id + "visible"] = true;
	};

    $scope.control.openAlert = function (title, text, okHandler) {
    	title = solib.phrase(title);
    	text = solib.phrase(text);
        var modalInstance = $modal.open({
            templateUrl: "form-control-alert",
            controller:function ($scope, $modalInstance) {
            	$scope.title = title;
            	$scope.text  = text;
            	$scope.control = control;
                $scope.ok = function () {
                    $modalInstance.close();
                    if (okHandler && ("function" == typeof okHandler)) {
                    	okHandler();
                    }
                };
            }
        });
    };

    $scope.control.openConfirm = function (title, text, okHandler, cancelHandler) {
    	title = solib.phrase(title);
    	text = solib.phrase(text);
        var modalInstance = $modal.open({
            templateUrl: "form-control-confirm",
            backdrop: "static",
            controller:function ($scope, $modalInstance) {
            	$scope.title = title;
            	$scope.text  = text;
            	$scope.control = control;
                $scope.ok = function () {
                    if (okHandler && ("function" == typeof okHandler)) {
                    	okHandler();
                    }
                    $modalInstance.close();
                };
                $scope.cancel = function () {
                    if (cancelHandler && ("function" == typeof cancelHandler)) {
                    	cancelHandler();
                    }
                    $modalInstance.close();
                };
            }
        });
    };

    $scope.control.openLinked = function (uri, okHandler, cancelHandler) {
        var modalInstance = $modal.open({
            templateUrl: 'form-control-linked',
            controller: function ($scope, $modalInstance) {
            	$scope.control = control;
            	$scope.query = function () {
            		var uriWithPage = uri;
            		if (control.cache.linked && control.cache.linked.pagination) {
            			uriWithPage += "/" + control.cache.linked.pagination.currentPageNumber;
            		}
	            	$http["get"](solib.uri(uriWithPage))
	        		.success(function(data, status, headers, config) {
	        			control.cache.linked = {
	        					"pagination": data.pagination,
	        					"list": data.list
	        			};
	            	    $scope.linked = control.cache.linked;
	        		})
	        		.error(solib.httpErrorHandler);
            	};

        	    $scope.ok = function () {
        	        $modalInstance.close($scope.linked.selectedItem);
        	    };
        	    $scope.cancel = function () {
        	        $modalInstance.dismiss('cancel');
        	    };
        	    $scope.selectedItemId = 100;

        	    $scope.query();
            }
        });

        modalInstance.result.then(function (selectedItem) {
            if (okHandler && ("function" == typeof okHandler)) {
            	okHandler(selectedItem);
            }
            //$scope.selected = selectedItem.id;
        }, function () {
            if (cancelHandler && ("function" == typeof cancelHandler)) {
            	cancelHandler();
            }
            //$log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.control.openFileUpload = function (okHandler, existingFiles) {
        var modalInstance = $modal.open({
            templateUrl: "form-control-file-upload",
            backdrop: "static",
            keyboard: false,
            controller:function ($scope, $modalInstance) {
            	$scope.control = control;
            	$scope.done = false;
            	$scope.existingFiles = angular.copy(existingFiles);
                $scope.cancel = function () {
                    $modalInstance.close();
                };
                $scope.reset = function () {
                	var uploadFileIframe = $("#file-container")[0];
                	var innerWindow = uploadFileIframe.contentWindow || uploadFileIframe;
                	innerWindow.location = "static/html/uploadMultiple.html";
                	$scope.existingFiles = angular.copy(existingFiles);
                	$scope.done = false;
                };
                $scope.upload = function () {
                	var uploadFileIframe = $("#file-container")[0];
                	var innerWindow = uploadFileIframe.contentWindow || uploadFileIframe;
                	if (innerWindow.uploadNow()) {
                    	$scope.done = true;
                	}
                };
                $scope.complete = function () {
                	var uploadFileIframe = $("#file-container")[0];
                	var innerWindow = uploadFileIframe.contentWindow || uploadFileIframe;
                	var contentElement = $('pre', innerWindow.document);
                	var files = [];
                	if (contentElement.length > 0) {
                		files = contentElement.html().replace(/^(\r?\n)*|(\r?\n)*$/g, "").replace(/(\r?\n){2,}/g, "$1").split(/\r?\n/g);
                	}
//                	for (var i in files) {
//                    	console.log(i, files[i]);
//                	}
                    if (okHandler && ("function" == typeof okHandler)) {
                    	okHandler(files.concat(($scope.existingFiles && $scope.existingFiles.length > 0) ? $scope.existingFiles : []));
                    }
                    $modalInstance.close();
                };
                $scope.add = function () {
                	var uploadFileIframe = $("#file-container")[0];
                	var innerWindow = uploadFileIframe.contentWindow || uploadFileIframe;
                	innerWindow.addFileControl();
                };
                $scope.remove = function ($index) {
                	$scope.done = true;
                	$scope.existingFiles.splice($index, 1);
                };
            }
        });
    };

//    $scope.control.openFileUpload = function (title, text, okHandler, cancelHandler) {
//    	$scope.items = [];
//    	title = solib.phrase(title);
//    	text = solib.phrase(text);
//        var modalInstance = $modal.open({
//            templateUrl: "form-control-file-upload",
//            backdrop: "static",
//            resolve: {
//                items: function () {
//                  return $scope.items;
//                }
//              },
//            controller:function ($scope, $modalInstance, items) {
//            	$scope.title = title;
//            	$scope.text  = text;
//                $scope.ok = function () {
//                    if (okHandler && ("function" == typeof okHandler)) {
//                    	okHandler();
//                    }
//                    $modalInstance.close();
//                };
//                $scope.cancel = function () {
//                    if (cancelHandler && ("function" == typeof cancelHandler)) {
//                    	cancelHandler();
//                    }
//                    $modalInstance.close();
//                };
//            }
//        });
//    };


});

app.filter("sanitize", ['$sce', function($sce) {
	return function(htmlCode){
		return $sce.trustAsHtml(htmlCode);
	}
}]);
