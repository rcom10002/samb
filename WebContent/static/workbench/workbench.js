var app = angular.module("workbench", []);

var global = {};

app.controller("workbench-controller", function ($scope, $http) {
	$scope.global = global;
	global.switchView = function(view) {
		$scope.sqlView.visible = false;
		
		$scope[view].visible = true;
	};

	function View(name) {
		global[name + "View"] = this;
		$scope[name + "View"] = this;
		this.model = {};
		this.visible = false;
	}

	var sqlView = new View("sql");
	sqlView.startRun = function () {
		if (!sqlView.model.sqls) {
			alert("SQL cannot be empty");
			return;
		}
		$http["put"]("/so/rest/workbench/executeSQL", sqlView.model)
		.success(function(data, status, headers, config) {
			sqlView.response = [];
			var response = data.payload;
			for (var i in response) {
				if (_.isArray(response[i]) && response[i].length > 0) {
					var maxWidth = {};
					var keys = _.keys(response[i][0]);
					_.each(keys, function (key) { maxWidth[key] = key.length + 3; } );
					_.each(response[i], function (row) {
						_.each(keys, function (key) {
							if (!_.isEmpty(row[key]) ) {
								maxWidth[key] = _.max([maxWidth[key], row[key].toString().length]) + 3;
							} else {
								maxWidth[key] = _.max([maxWidth[key], 3]);
							}
						});
					});
					var dataInPlainText = "";
					_.each(keys, function (key) {
						dataInPlainText += (key + new Array(maxWidth[key]).join(" ")).substring(0, maxWidth[key]);
					});
					dataInPlainText += "\n";
					_.each(response[i], function (row) {
						_.each(keys, function (key) {
							dataInPlainText += (row[key] + new Array(maxWidth[key]).join(" ")).substring(0, maxWidth[key]);
						});
						dataInPlainText += "\n";
					});
					sqlView.response.push(dataInPlainText);
				} else {
					sqlView.response.push(response[i]);
				}
			}
			sqlView.response = sqlView.response.join("\n------------------------------------------------\n\n");
			console.log(sqlView.response);
		});
	};
	sqlView.confirmRun = function () {
		
	};
	
});
