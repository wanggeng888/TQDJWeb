var locationHref = location.href;
var contentType = undefined;
if (locationHref.indexOf("news") > 0) {
	contentType = "news";
} else if (locationHref.indexOf("study") > 0) {
	contentType = "study";
} else if (locationHref.indexOf("tltd") > 0) {
	contentType = "tltd";
} else if (locationHref.indexOf("carousel") > 0){
	contentType = "carousel";
}

var idarray = new Array();
var accountlist = angular.module("acctlist", []);
accountlist.controller('accttable', function($scope, $http) {
	$scope.pageArray = new Array();
	$scope.currentpage = 1;
	$scope.totalpage = 1;
	$http({
		url : "acct/04",
		method : 'POST',
		data : {
			username : readUsername(),
			token : readToken(),
			data : doBase64Encode(JSON.stringify(new PageListObj('1')))
		}
	}).success(function(data) {
		if (data.code == 'success') {
			$scope.accounts = data.data;
			$scope.totalpage = data.total_page;
			if ($scope.totalpage > 5) {
				for (var i = 0; i < 5; i++) {
					$scope.pageArray[i] = i + 1;
				}
			} else {
				for (var i = 0; i < $scope.totalpage; i++) {
					$scope.pageArray[i] = i + 1;
				}
			}
		} else {
			alert('请求错误，请刷新重试');
		}
	}).error(function(data, status) {
		alert(status);
	});

	// 分页
	$scope.dopaging = function(page) {
		if($scope.currentpage != page){
			$scope.currentpage = page;
			$http({
				url : "acct/04",
				method : 'POST',
				data : {
					username : readUsername(),
					token : readToken(),
					data : doBase64Encode(JSON.stringify(new PageListObj(page)))
				}
			}).success(function(result, status) {
				if (result.code == 'success') {
					$scope.newses = result.data;
					$scope.totalpage = result.total_page;
					if ($scope.totalpage > 5) {
						if ($scope.currentpage <= 3) {
							for (var i = 0; i < 5; i++) {
								$scope.pageArray[i] = i + 1;
							}
						} else if ($scope.currentpage >= $scope.totalpage - 3) {
							for (var i = 0; i < 5; i++) {
								$scope.pageArray[i] = $scope.totalpage + i - 4;
							}
						} else {
							for (var i = 0; i < 5; i++) {
								$scope.pageArray[i] = $scope.currentpage - 2 + i;
							}
						}
					} else {
						for (var i = 0; i < $scope.totalpage; i++) {
							$scope.pageArray[i] = i + 1;
						}
					}
				} else {
					alert("查询新闻列表，请求错误，请刷新重试");
				}
			}).error(function(result) {
				alert(result);
			});
		}
	};
});

var list = angular.module(contentType + "list", []);
list.controller(contentType + "table", function($scope, $http) {
	$scope.pageArray = new Array();
	$scope.currentpage = 1;
	$scope.totalpage = 1;
	$http({
		url : contentType + "/01",
		method : 'POST',
		data : {
			username : readUsername(),
			token : readToken(),
			data : doBase64Encode(JSON.stringify(new PageListObj('1')))
		}
	}).success(function(data, status) {
		if (data.code == 'success') {
			$scope.newses = data.data;
			$scope.totalpage = data.total_page;
			if ($scope.totalpage > 5) {
				for (var i = 0; i < 5; i++) {
					$scope.pageArray[i] = i + 1;
				}
			} else {
				for (var i = 0; i < $scope.totalpage; i++) {
					$scope.pageArray[i] = i + 1;
				}
			}
		} else {
			alert('请求错误，请刷新重试');
		}
	}).error(function(data, status) {
		alert(status);
	});

	// 分页
	$scope.dopaging = function(page) {
		if($scope.currentpage != page){
			$scope.currentpage = page;
			$http({
				url : contentType + "/01",
				method : 'POST',
				data : {
					username : readUsername(),
					token : readToken(),
					data : doBase64Encode(JSON.stringify(new PageListObj(page)))
				}
			}).success(function(result, status) {
				if (result.code == 'success') {
					$scope.newses = result.data;
					$scope.totalpage = result.total_page;
					if ($scope.totalpage > 5) {
						if ($scope.currentpage <= 3) {
							for (var i = 0; i < 5; i++) {
								$scope.pageArray[i] = i + 1;
							}
						} else if ($scope.currentpage >= $scope.totalpage - 3) {
							for (var i = 0; i < 5; i++) {
								$scope.pageArray[i] = $scope.totalpage + i - 4;
							}
						} else {
							for (var i = 0; i < 5; i++) {
								$scope.pageArray[i] = $scope.currentpage - 2 + i;
							}
						}
					} else {
						for (var i = 0; i < $scope.totalpage; i++) {
							$scope.pageArray[i] = i + 1;
						}
					}
				} else {
					alert("查询新闻列表，请求错误，请刷新重试");
				}
			}).error(function(result) {
				alert(result);
			});
		}
	};
	// 选择列表
	$scope.listItemsId = function(id){
		var checkbox = document.getElementById("checkid_"+id);
		if(checkbox.checked) {
			idarray.push(new deleteObj(id));
		} else {
			for(var i = 0; i < idarray.length; i++) {
				if(id == idarray[i].id) {
					idarray.splice(i,1);
					break;
				}
			}
		}
	};
	// 批量删除
	$scope.deleteItem = function() {
		if(idarray.length > 0) {
			$http({
				url : contentType + "/05",
				method : 'POST',
				data : {
					username : readUsername(),
					token : readToken(),
					data : doBase64Encode(JSON.stringify(idarray))
				}
			}).success(function(data, status) {
				if (data.code == 'success') {
					alert("删除成功");
					history.go(0);
				} else {
					alert('请求错误，请刷新重试');
				}
			}).error(function(data, status) {
				alert(status);
			});
		}
	};
});

var exam = angular.module("examlist", []);
exam.controller("examtable", function($scope, $http) {
	$scope.pageArray = new Array();
	$scope.currentpage = 1;
	$scope.totalpage = 1;
	$http({
		url : "exam/06",
		method : 'POST',
		data : {
			username : readUsername(),
			token : readToken(),
			data : doBase64Encode(JSON.stringify(new PageListObj('1')))
		}
	}).success(function(data, status) {
		if (data.code == 'success') {
			$scope.exames = data.data;
			$scope.totalpage = data.total_page;
			if ($scope.totalpage > 5) {
				for (var i = 0; i < 5; i++) {
					$scope.pageArray[i] = i + 1;
				}
			} else {
				for (var i = 0; i < $scope.totalpage; i++) {
					$scope.pageArray[i] = i + 1;
				}
			}
		} else {
			alert('请求错误，请刷新重试');
		}
	}).error(function(data, status) {
		alert(data);
	});

	// 分页
	$scope.dopaging = function(page) {
		if($scope.currentpage != page){
			$scope.currentpage = page;
			$http({
				url : "exam/06",
				method : 'POST',
				data : {
					username : readUsername(),
					token : readToken(),
					data : doBase64Encode(JSON.stringify(new PageListObj(page)))
				}
			}).success(function(result) {
				if (result.code == 'success') {
					$scope.exames = result.data;
					$scope.totalpage = result.total_page;
					if ($scope.totalpage > 5) {
						if ($scope.currentpage <= 3) {
							for (var i = 0; i < 5; i++) {
								$scope.pageArray[i] = i + 1;
							}
						} else if ($scope.currentpage >= $scope.totalpage - 3) {
							for (var i = 0; i < 5; i++) {
								$scope.pageArray[i] = $scope.totalpage + i - 4;
							}
						} else {
							for (var i = 0; i < 5; i++) {
								$scope.pageArray[i] = $scope.currentpage - 2 + i;
							}
						}
					} else {
						for (var i = 0; i < $scope.totalpage; i++) {
							$scope.pageArray[i] = i + 1;
						}
					}
				} else {
					alert("查询新闻列表，请求错误，请刷新重试");
				}
			}).error(function(result) {
				alert(result);
			});
		}
	};
	// 选择列表
	$scope.listItemsId = function(id){
		var checkbox = document.getElementById("checkid_"+id);
		if(checkbox.checked) {
			idarray.push(new deleteObj(id));
		} else {
			for(var i = 0; i < idarray.length; i++) {
				if(id == idarray[i].id) {
					idarray.splice(i,1);
					break;
				}
			}
		}
	};
	// 批量删除
	$scope.deleteItem = function() {
		if(idarray.length > 0) {
			$http({
				url : "exam/05",
				method : 'POST',
				data : {
					username : readUsername(),
					token : readToken(),
					data : doBase64Encode(JSON.stringify(idarray))
				}
			}).success(function(data, status) {
				if (data.code == 'success') {
					alert("删除成功");
					history.go(0);
				} else {
					alert('请求错误，请刷新重试');
				}
			}).error(function(data, status) {
				alert(status);
			});
		}
	};
});

function readUsername() {
	return document.cookie.split('&')[0].split('=')[1];
}

function readToken() {
	return document.cookie.split('&')[1].split('=')[1];
}

function PageListObj(value) {
	this.current_page = value;
}

function deleteObj(value) {
	this.id = value;
}