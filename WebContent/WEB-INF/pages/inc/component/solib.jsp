<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var solib = solib || { page: { } };
	solib.angularModules = ['ui.bootstrap', 'chieffancypants.loadingBar', 'ngGrid', 'wysiwyg.module', 'ngSanitize', 'chart.js'];
	solib.PageView = function (name, additional) {
		this.name           = name;
		this.model          = new solib.EntityModel();
		this.formControls   = {};
		this.visible        = false;
		this.validation     = {};
		solib.renew(this, additional);
	};
	solib.EntityModel = function () {
		this._entryModel = {};
		this.entryModelList = [];
	};
	solib.Page = function ($scope) {
		solib.renew(solib.page, solib);
		$scope.page = solib.page;
		/*
		 * 1. process sock puppet properties for view model
		 * 2. provide automatic check support
		 */
		(function ($scope, page) {
			function internalAudit(myView, disableAutoCheck) {
				return function (newVal, oldVal) {
					if (!page[myView]) {
						return;
					}
					page[myView].isModelPerfect = true;
					for (var item in page._formControlRepository) {
						// synch the values between sock puppet and real model property
						if (page._formControlRepository[item].type == "date" || page._formControlRepository[item].type == "datetime") {
							if (page[myView].model[item] && typeof page[myView].model["_" + item] == "undefined") {
								page[myView].model["_" + item] = new Date(page[myView].model[item].replace(/ /, "T") + "Z");
							} else if (page[myView].model["_" + item]) {
								page[myView].model[item] = page[myView].model["_" + item].toISOString().replace(/[.].+$/, "").replace(/T/, " ");
							} else {
								page[myView].model[item] = null;
							}
						} else if (page._formControlRepository[item].type == "files") {
							if (page[myView].model["_" + item] instanceof Array) {
								page[myView].model[item] = page[myView].model["_" + item].join("/");
							}
						}
						if (disableAutoCheck) {
							continue;
						}

						// enable support for automatic validation
						var found = page[myView].formControls.filter(function(e) { return (e.name == item) || (e.name == "_" + item); });
						var errors = [];
						if (found && found.length && found[0].check && found[0].check.length) {
							for (var i in found[0].check) {
								var checkResult = found[0].check[i](page[myView].model[item], page._formControlRepository[item]["label"]);
								if (checkResult) {
									errors.push(checkResult);
								}
							}
						}
						page[myView].isModelPerfect &= !errors.length;
						if (page[myView].validation) {
							page[myView].validation["_" + item] = page[myView].validation[item] = { hasError: errors.length > 0, errorMessage: errors.join("\n") };
						}
					}
				};
			}

			$scope.$watch("page.addView.model",  internalAudit("addView",  false), true);
			$scope.$watch("page.viewView.model", internalAudit("viewView", true),  true);
			$scope.$watch("page.editView.model", internalAudit("editView", false), true);
		})($scope, solib.page);
		
		return solib.page;
	};
	solib.GridOptions = function (data, columnDefs, others) {
		var gridOptions = {
			"noKeyboardNavigation": true,
// 			"showFilter": true,
// 			"showColumnMenu": true,
// 			"enableCellSelection": true,
// 			"enableCellEdit": true,
			"enableColumnResize": true,
			"data": data,
	        "enablePinning": true,
	        "multiSelect": false,
	        "selectedItems": []
		};
        if (columnDefs && columnDefs instanceof Array && columnDefs.length > 0) {
        	// preprocess for sock puppet fields
        	var columnsSockPuppet = { };
        	for (var i in columnDefs) {
        		if (columnDefs[i].minWidth) {
            		solib.renew(columnDefs[i], {"minWidth": columnDefs[i].minWidth, "maxWidth": 600});
        		} else {
            		solib.renew(columnDefs[i], {"minWidth": 150, "maxWidth": 600});
        		}
    			if (solib.page._formControlRepository[columnDefs[i].field] && solib.page._formControlRepository[columnDefs[i].field].type == "linked") {
    				var columnSockPuppet = angular.copy(columnDefs[i]);
    				columnSockPuppet.field = "_" + columnSockPuppet.field;
    				columnsSockPuppet[columnSockPuppet.field] = columnSockPuppet;
    				columnDefs[i].visible = false;
    			}
        	}
        	var finalColumnDefs = [];
        	for (var i in columnDefs) {
        		var fieldSockPuppet = "_" + columnDefs[i].field;
        		if (columnsSockPuppet[fieldSockPuppet]) {
        			finalColumnDefs.push(columnsSockPuppet[fieldSockPuppet]);
        		}
        		finalColumnDefs.push(columnDefs[i]);
        	}
        	columnDefs = finalColumnDefs;
        	// process for fields of special data type
        	for (var i in columnDefs) {
        		if (columnDefs[i].displayName) {
        			if (solib.phrase(columnDefs[i].displayName)) {
        				columnDefs[i].displayName = solib.phrase(columnDefs[i].displayName);
        			}
        		}
        		if (solib.page._formControlRepository[columnDefs[i].field.replace(/^_/, "")] && solib.page._formControlRepository[columnDefs[i].field.replace(/^_/, "")].type == "linked") {
        			columnDefs[i].cellTemplate = '<div class="ngCellText">{{row.getProperty(col.field)}}</div>';
        			continue;
        		}
        		if (solib.page._formControlRepository[columnDefs[i].field.replace(/^_/, "")] && solib.page._formControlRepository[columnDefs[i].field.replace(/^_/, "")].type == "date") {
        			columnDefs[i].cellTemplate = '<div class="ngCellText">{{page.format.dateStringFromUTCtoLocal(row.getProperty(col.field))}}</div>';
        			continue;
        		}
        		if (solib.page._formControlRepository[columnDefs[i].field.replace(/^_/, "")] && solib.page._formControlRepository[columnDefs[i].field.replace(/^_/, "")].type == "datetime") {
        			columnDefs[i].cellTemplate = '<div class="ngCellText">{{page.format.datetimeStringFromUTCtoLocal(row.getProperty(col.field))}}</div>';
        			continue;
        		}
        		if (solib.page._formControlRepository[columnDefs[i].field].type == "number") {
        			columnDefs[i].cellTemplate = '<div class="ngCellText">{{page.format.toCurrency(row.getProperty(col.field))}}</div>';
        			continue;
        		}
        		if (solib.page._formControlRepository[columnDefs[i].field].type == "select") {
        			columnDefs[i].cellTemplate = '<div class="ngCellText">{{page.lookupV(page._formControlRepository[col.field]["select-source"], row.getProperty(col.field))}}</div>';
        			continue;
        		}
        	}
        }
    	gridOptions.columnDefs = columnDefs;
        solib.renew(this, gridOptions);
	};
	solib.hideAllViews = function () {
		for (var view in solib.page) {
			if (/^.+View$/.test(view)) {
				solib.page[view].visible = false;
			}
		}
	};
	solib.uri = function (targetURI) {
		var contextPath = "${pageContext.request.contextPath}/";
		return (contextPath + targetURI).replace(/(\/){2,}/g, "/");
	};
	solib.fileuri = function (name) {
		var contextPath = "${pageContext.request.contextPath}/";
		return (contextPath + "/rest/files/" + name).replace(/(\/){2,}/g, "/");
	};
	solib.exceluri = function (name) {
		var contextPath = "${pageContext.request.contextPath}/";
		return (contextPath + "/rest/excel/" + name).replace(/(\/){2,}/g, "/");
	};
	solib.httpErrorHandler = function(data, status, headers, config) {
		alert(status);
	};
	solib.nvl = function (v1, v2) {
		return v1 ? v1 : v2;
	};
	solib.phrase = function (phraseId) {
		try {
			var phrase = $("script[type='text/so-phrase']").text().replace(/(\r?\n){2,}/g, "\n").replace(/^(\r?\n)+|(\r?\n)+$/g, "");
			phrase = phrase.split(/\r?\n/);
			var foundPhrase = null;
			var regex = new RegExp("^" + phraseId + "=");
			for (var i in phrase) {
				if (regex.test(phrase[i])) {
					foundPhrase = phrase[i].replace(regex, "");
					break;
				}
			}
			if (foundPhrase) {
				for (var i = 1; i < arguments.length; i++) {
					foundPhrase = foundPhrase.replace(/%s/, arguments[i]);
				}
				return foundPhrase;
			}
			return "";
		} catch (e) { return ""; }
	};
	solib.slim = function (obj, slimObj) {
		for (var i in slimObj) {
			delete obj[slim[i]];
		}
	};
	solib.renew = function (obj, payload) {
		if (!payload) {
			return obj;
		}
		for (var p in payload) {
			obj[p] = payload[p];
		}
		return obj;
	};
	solib.lookupV = function (category, name) {
		var lookupNow = solib.lookup[category];
		for (var i in lookupNow) {
			if (lookupNow[i].name == name) {
				return lookupNow[i].val;
			}
		}
	};
	solib.format = {
			dateStringFromUTCtoLocal: function (utcDate) {
				if (!utcDate) return;
				var dateObj = (utcDate instanceof Date) ? utcDate : new Date(utcDate.replace(/ /, "T") + "Z");
				return [dateObj.getFullYear(), (dateObj.getMonth() + 1), dateObj.getDate()].join("-").replace(/\b(\d)\b/g, "0$1");
			},
			datetimeStringFromUTCtoLocal: function (utcDate) {
				if (!utcDate) return;
				var datePart = solib.format.dateStringFromUTCtoLocal(utcDate);
				var dateObj = (utcDate instanceof Date) ? utcDate : new Date(utcDate.replace(/ /, "T") + "Z");
				var timePart = [dateObj.getHours(), dateObj.getMinutes(), dateObj.getSeconds()].join(":").replace(/\b(\d)\b/g, "0$1");
				return datePart + " " + timePart;
			},
			toCurrency: function (num) {
				num = num + "";
				var p1 = num.split(/[.]/)[0];
				var p2 = num.split(/[.]/)[1];
				var la, lb;
				do {
					la = p1.length;
					p1 = p1.replace(/\B(\d{3})\b/, ",$1");
					lb = p1.length;
				} while(la != lb)
				return p1 + (p2 ? ("." + p2) : "");
			}
	};
	
	solib.startIntro = function (){
        var intro = introJs();
          intro.setOptions({
            steps: [
              {
                intro: "Welcome to Smart Office!"
              },
              {
                element: $("ul.nav.navbar-nav")[0],
                intro: "This is the main menu. It helps to navigate you to different applications."
              },
              {
                element: $(".panel.panel-default")[0],
                intro: "This is data area, which consists of action buttons/data grid/paginator",
                position: 'top'
              },
              {
            	element: $(".panel-heading")[0],
                intro: "Action buttons area."
              },
              {
            	element: $(".panel-heading button")[0],
                intro: "Spawn a new record",
                position: 'right'
              },
              {
            	element: $(".panel-heading button")[1],
                intro: "Select any an available data in data grid below, then clone a same one with this button.",
                position: 'right'
              },
              {
            	element: $(".panel-heading button")[2],
                intro: "View the selected data within a friendly layout",
                position: 'right'
              },
              {
            	element: $(".panel-heading button")[3],
                intro: "Edit the selected data",
                position: 'right'
              },
              {
            	element: $(".panel-heading button")[4],
                intro: "Throw the selected into trash",
                position: 'right'
              },
              {
              	element: $(".panel-body")[0],
                intro: 'Data grid',
                position: 'top'
              },
              {
              	element: $(".panel-footer ul")[0],
                intro: "Page up/down",
                position: 'top'
              }
            ]
          });

          intro.start();
    };
	
	solib.validation = {
			required: function() {
				return function (value, label) {
					label = solib.phrase(label);
					if (!value || (value instanceof Array && value.length == 0) || (value + "").trim().length == 0) {
						return solib.phrase("solib.validation.message.common.required", label);
					}
					return null;
				}
			},
			unique: function(view, field) {
				return function (value, label) {
					label = solib.phrase(label);
					if (view.model.filter) {
						var found = view.model.filter(function (e) { return e[field] == value; } )
						return (found && found.length > 1) ? solib.phrase("solib.validation.message.common.unique", label) : null;
					}
					return null;
				}
			},
			mustMatch: function(regex, message) {
				message = solib.phrase(message);
				if (regex && regex instanceof RegExp) {
					return function (value, label) {
						label = solib.phrase(label);
						return regex.test(value) ? null : solib.phrase("solib.validation.message.common.must.match", label, message);
					}
				} else {
					return function () { return null };
				}
			},
			mustNotMatch: function(regex) {
				if (regex && regex instanceof RegExp) {
					return function (value, label) {
						label = solib.phrase(label);
						return !regex.test(value) ? null : solib.phrase("solib.validation.message.common.must.not.match", label);
					}
				} else {
					return function () { return null };
				}
			},
			allowChars: function(chars) {
				return function (value, label) {
					label = solib.phrase(label);
					if (value) {
						for (var i = 0; i < value.length; i++) {
							if (chars.indexOf(value[i]) < 0) {
								return solib.phrase("solib.validation.message.common.allow.chars", label, chars);
							}
						}
					}
					return null;
				};
			},
			notAllowChars: function(chars) {
				return function (value, label) {
					label = solib.phrase(label);
					if (value) {
						for (var i = 0; i < value.length; i++) {
							if (chars.indexOf(value[i]) > -1) {
								return solib.phrase("solib.validation.message.common.not.allow.chars", label, chars);
							}
						}
					}
					return null;
				}
			},
			numberValid: function() {
				return function (value, label) {
					label = solib.phrase(label);
					return solib.validation._isNum(value) ? null : solib.phrase("solib.validation.message.number.valid", label);
				}
			},
			numberRange: function(min, max) {
				var minLabel = solib.format.toCurrency(min);
				var maxLabel = solib.format.toCurrency(max);
				return function (value, label) {
					label = solib.phrase(label);
					if (solib.validation._isNum(value)) {
						value = value - 0;
						return ((min <= value) && (value <= max)) ? null : solib.phrase("solib.validation.message.number.range", label, minLabel, maxLabel);
					}
					return null;
				}
			},
			numberMax: function(max) {
				var maxLabel = solib.format.toCurrency(max);
				return function (value, label) {
					label = solib.phrase(label);
					if (!value) {
						return null;
					}
					return (solib.validation._isNum(value) && (value - 0 > max)) ? solib.phrase("solib.validation.message.number.max", label, maxLabel) : null;
				}
			},
			numberMin: function(min) {
				var minLabel = solib.format.toCurrency(min);
				return function (value, label) {
					label = solib.phrase(label);
					if (!value) {
						return null;
					}
					return (solib.validation._isNum(value) && (value - 0 < min)) ? solib.phrase("solib.validation.message.number.min", label, minLabel) : null;
				}
			},
			numberIntegerOnly: function() {
				return function (value, label) {
					label = solib.phrase(label);
					return /^-?[0-9]+$/.test(value) ? null : solib.phrase("solib.validation.message.number.integer.only", label);
				}
			},
			stringMinLength: function(min) {
				return function (value, label) {
					label = solib.phrase(label);
					if (value && value.length < min) {
						return solib.phrase("solib.validation.message.string.min.length", label, min, value.length);
					} else {
						return null;
					}
				}
			},
			stringMaxLength: function(max) {
				return function (value, label) {
					label = solib.phrase(label);
					if (value && value.length > max) {
						return solib.phrase("solib.validation.message.string.max.length", label, max, value.length);
					} else {
						return null;
					}
				}
			},
			dateValid: function () {
				return function (value, label) {
					label = solib.phrase(label);
					if (!value) {
						return null;
					}
					if (value && (value instanceof Date)) {
						return null;
					}
					if (value && value.length > max) {
						return solib.phrase("solib.validation.message.string.max.length", label);
					} else {
						return null;
					}
				}
			},
			dateRange: function(value, label) {
				label = solib.phrase(label);
				return value ? null : solib.phrase("solib.validation.message.common.required", label);
			},
			dateMin: function(value, label) {
				label = solib.phrase(label);
				return value ? null : solib.phrase("solib.validation.message.common.required", label);
			},
			dateMax: function(value, label) {
				label = solib.phrase(label);
				return value ? null : solib.phrase("solib.validation.message.common.required", label);
			},
			_isNum: function (value) {
				return /^-?([1-9][0-9]*|0)([.][0-9]{1,2})?$/.test(value + "");
			}
	};

	http://stackoverflow.com/questions/2573521/how-do-i-output-an-iso-8601-formatted-string-in-javascript
	if ( !Date.prototype.toISOString ) {
	    ( function() {

	        function pad(number) {
	            var r = String(number);
	            if ( r.length === 1 ) {
	                r = '0' + r;
	            }
	            return r;
	        }

	        Date.prototype.toISOString = function() {
	            return this.getUTCFullYear()
	                + '-' + pad( this.getUTCMonth() + 1 )
	                + '-' + pad( this.getUTCDate() )
	                + 'T' + pad( this.getUTCHours() )
	                + ':' + pad( this.getUTCMinutes() )
	                + ':' + pad( this.getUTCSeconds() )
	                + '.' + String( (this.getUTCMilliseconds()/1000).toFixed(3) ).slice( 2, 5 )
	                + 'Z';
	        };

	    }() );
	}

</script>
