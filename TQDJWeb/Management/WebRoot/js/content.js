var locationHref = location.href;
var hrefparams = locationHref.split("?")[1];
var contentType = undefined; // 标识操作内容的类型， news为新闻，study为学习，tltd为两学一做，exam为考试，carousel为轮播图
var contentFun = undefined; // 标识操作内容的方法， 1为新增，2为查看，3为修改
var selectid = undefined;
var uploadFileid = 0;
var uploadFileSrc = undefined;
if (hrefparams != undefined && hrefparams != null && hrefparams != "") {
	var params = hrefparams.split("&");
	for(var i = 0; i < params.length; i++){
		if(i == 0) {
			var key1 = params[0].split("=")[0];
			if ("type" == key1) {
				contentType = params[0].split("=")[1];
			}
		} else if(i == 1) {
			var key2 = params[1].split("=")[0];
			if ("fun" == key2) {
				contentFun = params[1].split("=")[1];
			}
		} else if(i == 2) {
			var key3 = params[2].split("=")[0];
			if("id" == key3){
				selectid = params[2].split("=")[1];
			}
		}
	}
}

var news = angular.module("news", []);
news.controller('newscontent', function($scope, $http) {
	/**
	 * 上传文件控件
	 */
	$("#uploadify").uploadify({
		'swf': 'js/uploadify.swf',
		'method': 'Post',
	    'uploader': 'images/01',
	    'fileObjName': 'file',
	    'buttonText': '上传插图',
	    'fileTypeDesc': 'Image Files',
	    'fileTypeExts': '*.gif; *.jpg; *.png',
	    'auto': true,
	    'multi': true,
	    'onUploadSuccess' : function(file, data,
                response) {
	    	var result = eval("("+ data +")");
	    	var code = result.code;
	    	if ("success" == code) {
	    		uploadFileid = result.id;
	    		uploadFileSrc = result.src;
	    		$("#new-image").css("display", "block");
	    		$("#new-image").attr("src", uploadFileSrc);
	    	} else {
	    		alert("图片上传失败");
	    	}
        },
        'onUploadError' : function(file, errorCode,
                errorMsg, errorString) {
        	alert("图片上传失败,参数错误");
        }
	});
	
	if ((contentType != undefined && contentType != 'undefined' && contentType != null && contentType != 'null' && contentType != "")
		&& (contentFun != undefined && contentFun != 'undefined' && contentFun != null && contentFun != 'null' && contentFun != "")) {
		backPage();
		if ("1" == contentFun) {
			clearBtn("eibtn");
			clearBtn("ebtn");
			$("#uploadify").css("display", "block");
			$scope.create = function() {
				var createData = createObj();
				if(createData != undefined) {
					console.log(JSON.stringify(createData));
					createData = doBase64Encode(JSON.stringify(createData));
					$http({
						url : contentType + '/03',
						method : 'POST',
						data : {
							username : readUsername(),
							token : readToken(),
							data : createData
						}
					}).success(function(data) {
						if (data.code == 'success') {
							if (confirm("添加成功, 是否继续添加")) {
								location.href = "news.jsp?type=news&fun=1";
							} else {
								location.href = contentType + "list.jsp";
							}
						} else {
							alert("新增新闻，请求错误，请刷新重试...");
						}
					}).error(function(data) {
						alert(status);
					});
				}
			};
		} else if ("2" == contentFun) {
			if(selectid != undefined){
				lockInput();
				clearBtn("cbtn");
				clearBtn("ebtn");
				searchData(selectid);
				$("#uploadify").css("display", "none");
			}
			$scope.edit = function() {
				location.href = contentType + ".jsp?type="+ contentType +"&fun=3&id="+ selectid;
			};
		} else if ("3" == contentFun) {
			if(selectid != undefined) {
				clearBtn("cbtn");
				clearBtn("eibtn");
				searchData(selectid);
				$("#uploadify").css("display", "block");
			}
			$scope.update = function() {
				var updateData = updateObj();
				if(updateData != undefined) {
					updateData = doBase64Encode(JSON.stringify(updateData));
					$http({
						url : contentType + '/04',
						method : 'POST',
						headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
						data : {
							username : readUsername(),
							token : readToken(),
							data : updateData
						}
					}).success(function(result) {
						if (result.code == 'success') {
							alert("更新成功");
							location.href = contentType + ".jsp?type="+ contentType +"&fun=2&id="+ result.id;
						} else {
							alert("更新新闻，请求错误，请刷新重试...");
						}
					}).error (function(result) {
						alert(result);
					});
				}
			};
		}
		
		// 查询数据
		function searchData(id) {
			$http({
				url : contentType +'/02',
				method : 'POST',
				data : {
					username : readUsername(),
					token : readToken(),
					data: doBase64Encode(JSON.stringify(new searchObj(id)))
				}
			}).success(function(result) {
				if (result.code == 'success') {
					document.getElementById("title").value = result.data.title;
					document.getElementById("lead").value = result.data.lead;
					document.getElementById("source").value =  result.data.source;
					document.getElementById("text").value =  result.data.text;
					uploadFileid = result.data.imgid;
					var imgsrc = result.data.src;
					if(imgsrc != ""){
						$("#new-image").css("display", "block");
						$("#new-image").attr("src", imgsrc);
					}
				} else {
					alert("查询新闻数据，参数错误...");
				}
			}).error(function(result) {
				alert(result);
			});
		}
	}
});

var carousel = angular.module("carousel", []);
carousel.controller('carouselcontent', function($scope, $http) {
	/**
	 * 上传文件控件
	 */
	$("#uploadify").uploadify({
		'swf': 'js/uploadify.swf',
		'method': 'Post',
	    'uploader': 'images/01',
	    'fileObjName': 'file',
	    'buttonText': '上传插图',
	    'fileTypeDesc': 'Image Files',
	    'fileTypeExts': '*.gif; *.jpg; *.png',
	    'auto': true,
	    'multi': true,
	    'onUploadSuccess' : function(file, data,
                response) {
	    	var result = eval("("+ data +")");
	    	var code = result.code;
	    	if ("success" == code) {
	    		uploadFileid = result.id;
	    		uploadFileSrc = result.src;
	    		$("#new-image").css("display", "block");
	    		$("#new-image").attr("src", uploadFileSrc);
	    	} else {
	    		alert("图片上传失败");
	    	}
        },
        'onUploadError' : function(file, errorCode,
                errorMsg, errorString) {
        	alert("图片上传失败,参数错误");
        }
	});
	
	if ((contentType != undefined && contentType != 'undefined' && contentType != null && contentType != 'null' && contentType != "")
		&& (contentFun != undefined && contentFun != 'undefined' && contentFun != null && contentFun != 'null' && contentFun != "")) {
		backPage();
		if ("1" == contentFun) {
			clearBtn("eibtn");
			clearBtn("ebtn");
			$("#uploadify").css("display", "block");
			$scope.create = function() {
				var createData = createObj();
				if(createData != undefined) {
					console.log(JSON.stringify(createData));
					createData = doBase64Encode(JSON.stringify(createData));
					$http({
						url : contentType + '/03',
						method : 'POST',
						data : {
							username : readUsername(),
							token : readToken(),
							data : createData
						}
					}).success(function(data) {
						if (data.code == 'success') {
							if (confirm("添加成功, 是否继续添加")) {
								location.href = "carousel.jsp?type=carousel&fun=1";
							} else {
								location.href = contentType + "list.jsp";
							}
						} else {
							alert("新增新闻，请求错误，请刷新重试...");
						}
					}).error(function(data) {
						alert(status);
					});
				}
			};
		} else if ("2" == contentFun) {
			if(selectid != undefined){
				lockInput();
				clearBtn("cbtn");
				clearBtn("ebtn");
				searchData(selectid);
				$("#uploadify").css("display", "none");
			}
			$scope.edit = function() {
				location.href = contentType + ".jsp?type="+ contentType +"&fun=3&id="+ selectid;
			};
		} else if ("3" == contentFun) {
			if(selectid != undefined) {
				clearBtn("cbtn");
				clearBtn("eibtn");
				searchData(selectid);
				$("#uploadify").css("display", "block");
			}
			$scope.update = function() {
				var updateData = updateObj();
				if(updateData != undefined) {
					updateData = doBase64Encode(JSON.stringify(updateData));
					$http({
						url : contentType + '/04',
						method : 'POST',
						headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
						data : {
							username : readUsername(),
							token : readToken(),
							data : updateData
						}
					}).success(function(result) {
						if (result.code == 'success') {
							alert("更新成功");
							location.href = contentType + ".jsp?type="+ contentType +"&fun=2&id="+ result.id;
						} else {
							alert("更新新闻，请求错误，请刷新重试...");
						}
					}).error (function(result) {
						alert(result);
					});
				}
			};
		}
		
		// 查询数据
		function searchData(id) {
			$http({
				url : contentType +'/02',
				method : 'POST',
				data : {
					username : readUsername(),
					token : readToken(),
					data: doBase64Encode(JSON.stringify(new searchObj(id)))
				}
			}).success(function(result) {
				if (result.code == 'success') {
					document.getElementById("title").value = result.data.title;
					document.getElementById("lead").value = result.data.lead;
					document.getElementById("source").value =  result.data.source;
					document.getElementById("text").value =  result.data.text;
					uploadFileid = result.data.imgid;
					var imgsrc = result.data.src;
					if(imgsrc != ""){
						$("#new-image").css("display", "block");
						$("#new-image").attr("src", imgsrc);
					}
				} else {
					alert("查询新闻数据，参数错误...");
				}
			}).error(function(result) {
				alert(result);
			});
		}
	}
});

var study = angular.module("study", []);
study.controller('studycontent', function($scope, $http) {
	if ((contentType != undefined && contentType != 'undefined' && contentType != null && contentType != 'null' && contentType != "")
		&& (contentFun != undefined && contentFun != 'undefined' && contentFun != null && contentFun != 'null' && contentFun != "")) {
		backPage();
		if ("1" == contentFun) {
			clearBtn("eibtn");
			clearBtn("ebtn");
			
			$scope.create = function() {
				var createData = createObj();
				if(createData != undefined) {
					console.log(JSON.stringify(createData));
					createData = doBase64Encode(JSON.stringify(createData));
					$http({
						url : contentType + '/03',
						method : 'POST',
						data : {
							username : readUsername(),
							token : readToken(),
							data : createData
						}
					}).success(function(data) {
						if (data.code == 'success') {
							alert("添加成功, 是否继续添加");
						} else {
							alert("新增新闻，请求错误，请刷新重试...");
						}
					}).error(function(data) {
						alert(status);
					});
				}
			};
		} else if ("2" == contentFun) {
			if(selectid != undefined){
				lockInput();
				clearBtn("cbtn");
				clearBtn("ebtn");
				searchData(selectid);
			}
			$scope.edit = function() {
				location.href = contentType + ".jsp?type="+ contentType +"&fun=3&id="+ selectid;
			};
		} else if ("3" == contentFun) {
			if(selectid != undefined) {
				clearBtn("cbtn");
				clearBtn("eibtn");
				searchData(selectid);
			}
			
			$scope.update = function() {
				var updateData = updateObj();
				console.log(updateData);
				if(updateData != undefined) {
					updateData = doBase64Encode(JSON.stringify(updateData));
					$http({
						url : contentType + '/04',
						method : 'POST',
						data : {
							username : readUsername(),
							token : readToken(),
							data : updateData
						}
					}).success(function(result) {
						if (result.code == 'success') {
							alert("更新成功");
							location.href = contentType + ".jsp?type="+ contentType +"&fun=2&id="+ result.id;
						} else {
							alert("更新新闻，请求错误，请刷新重试...");
						}
					}).error (function(result) {
						alert(result);
					});
				}
			};
		}
		
		// 查询数据
		function searchData(id) {
			$http({
				url : contentType +'/02',
				method : 'POST',
				data : {
					username : readUsername(),
					token : readToken(),
					data: doBase64Encode(JSON.stringify(new searchObj(id)))
				}
			}).success(function(result) {
				if (result.code == 'success') {
					document.getElementById("title").value = result.data.title;
					document.getElementById("summary").value = result.data.summary;
					document.getElementById("source").value =  result.data.source;
					document.getElementById("text").value =  result.data.text;
				} else {
					alert("查询新闻数据，参数错误...");
				}
			}).error(function(result) {
				alert(result);
			});
		}
	}
});

var tltd = angular.module("tltd", []);
tltd.controller('tltdcontent', function($scope, $http) {
	if ((contentType != undefined && contentType != 'undefined' && contentType != null && contentType != 'null' && contentType != "")
		&& (contentFun != undefined && contentFun != 'undefined' && contentFun != null && contentFun != 'null' && contentFun != "")) {
		backPage();
		if ("1" == contentFun) {
			clearBtn("eibtn");
			clearBtn("ebtn");
			
			$scope.create = function() {
				var createData = createObj();
				if(createData != undefined) {
					console.log(JSON.stringify(createData));
					createData = doBase64Encode(JSON.stringify(createData));
					$http({
						url : contentType + '/03',
						method : 'POST',
						data : {
							username : readUsername(),
							token : readToken(),
							data : createData
						}
					}).success(function(data) {
						if (data.code == 'success') {
							alert("添加成功, 是否继续添加");
						} else {
							alert("新增新闻，请求错误，请刷新重试...");
						}
					}).error(function(data) {
						alert(status);
					});
				}
			};
		} else if ("2" == contentFun) {
			if(selectid != undefined){
				lockInput();
				clearBtn("cbtn");
				clearBtn("ebtn");
				searchData(selectid);
			}
			$scope.edit = function() {
				location.href = contentType + ".jsp?type="+ contentType +"&fun=3&id="+ selectid;
			};
		} else if ("3" == contentFun) {
			if(selectid != undefined) {
				clearBtn("cbtn");
				clearBtn("eibtn");
				searchData(selectid);
			}
			
			$scope.update = function() {
				var updateData = updateObj();
				if(updateData != undefined) {
					updateData = doBase64Encode(JSON.stringify(updateData));
					$http({
						url : contentType + '/04',
						method : 'POST',
						data : {
							username : readUsername(),
							token : readToken(),
							data : updateData
						}
					}).success(function(result) {
						if (result.code == 'success') {
							alert("更新成功");
							location.href = contentType + ".jsp?type="+ contentType +"&fun=2&id="+ result.id;
						} else {
							alert("更新新闻，请求错误，请刷新重试...");
						}
					}).error (function(result) {
						alert(result);
					});
				}
			};
		}
		
		// 查询数据
		function searchData(id) {
			$http({
				url : contentType +'/02',
				method : 'POST',
				data : {
					username : readUsername(),
					token : readToken(),
					data: doBase64Encode(JSON.stringify(new searchObj(id)))
				}
			}).success(function(result) {
				if (result.code == 'success') {
					document.getElementById("title").value = result.data.title;
					document.getElementById("summary").value = result.data.summary;
					document.getElementById("source").value =  result.data.source;
					document.getElementById("text").value =  result.data.text;
				} else {
					alert("查询新闻数据，参数错误...");
				}
			}).error(function(result) {
				alert(result);
			});
		}
	}
});

var exam = angular.module("exam", []);
exam.controller('examcontent', function($scope, $http) {
	if ((contentType != undefined && contentType != 'undefined' && contentType != null && contentType != 'null' && contentType != "")
		&& (contentFun != undefined && contentFun != 'undefined' && contentFun != null && contentFun != 'null' && contentFun != "")) {
		backPage();
		if ("1" == contentFun) {
			clearBtn("eibtn");
			clearBtn("ebtn");
			
			$scope.create = function() {
				var createData = createObj();
				if(createData != undefined) {
					console.log(JSON.stringify(createData));
					createData = doBase64Encode(JSON.stringify(createData));
					$http({
						url : contentType + '/03',
						method : 'POST',
						data : {
							username : readUsername(),
							token : readToken(),
							data : createData
						}
					}).success(function(data) {
						if (data.code == 'success') {
							if (confirm("添加成功, 是否继续添加")) {
								location.href = "exam.jsp?type=exam&fun=1";
							} else {
								location.href = contentType + "list.jsp";
							}
						} else {
							alert("新考题闻，请求错误，请刷新重试...");
						}
					}).error(function(data) {
						alert(status);
					});
				}
			};
		} else if ("2" == contentFun) {
			if(selectid != undefined){
				lockInput();
				clearBtn("cbtn");
				clearBtn("ebtn");
				searchData(selectid);
			}
			$scope.edit = function() {
				location.href = contentType + ".jsp?type="+ contentType +"&fun=3&id="+ selectid;
			};
		} else if ("3" == contentFun) {
			if(selectid != undefined) {
				clearBtn("cbtn");
				clearBtn("eibtn");
				searchData(selectid);
			}
			$scope.update = function() {
				var updateData = updateObj();
				if(updateData != undefined) {
					updateData = doBase64Encode(JSON.stringify(updateData));
					$http({
						url : contentType + '/04',
						method : 'POST',
						data : {
							username : readUsername(),
							token : readToken(),
							data : updateData
						}
					}).success(function(result) {
						if (result.code == 'success') {
							alert("更新成功");
							location.href = contentType + ".jsp?type="+ contentType +"&fun=2&id="+ result.id;
						} else {
							alert("更考题闻，请求错误，请刷新重试...");
						}
					}).error (function(result) {
						alert(result);
					});
				}
			};
		}
		
		// 查询数据
		function searchData(id) {
			$http({
				url : contentType +'/07',
				method : 'POST',
				data : {
					username : readUsername(),
					token : readToken(),
					data: doBase64Encode(JSON.stringify(new searchObj(id)))
				}
			}).success(function(result) {
				if (result.code == 'success') {
					document.getElementById("subject").value = result.data.subject;
					document.getElementById("option1").value = result.data.option1;
					document.getElementById("option2").value = result.data.option2;
					document.getElementById("option3").value = result.data.option3;
					document.getElementById("option4").value = result.data.option4;
					setCheckbox(result.data.answer);
				} else {
					alert("查询考题数据，参数错误...");
				}
			}).error(function(result) {
				alert(result);
			});
		}
	}
});

function clearBtn(buttonid) {
	var btn = document.getElementById(buttonid);
	btn.setAttribute("ng-click", "");
	btn.innerHTML = "";
	btn.style.display = "none";
}

function backPage() {
	var goback = document.getElementById("goback");
	if("1" == contentFun || "2" == contentFun) {
		goback.setAttribute("href", contentType + "list.jsp");
	} else if("3" == contentFun){
		goback.setAttribute("href", contentType + ".jsp?type="+ contentType +"&fun=2&id="+ selectid);
	}
}

function createObj(){
	var resultObj = undefined;
	if("exam" == contentType) {
		var subject = document.getElementById("subject").value;
		var option1 = document.getElementById("option1").value;
		var option2 = document.getElementById("option2").value;
		var option3 = document.getElementById("option3").value;
		var option4 = document.getElementById("option4").value;
		var answer = dosetCheckbox();
		resultObj = new createExamObj(subject, option1, option2, option3, option4, answer);
	} else {
		var title = document.getElementById("title").value;
		var source = document.getElementById("source").value;
		var text = document.getElementById("text").value;
		if(title != "" && source != "" && text != "") {
			if ("news" == contentType || "carousel" == contentType) {
				var lead = document.getElementById("lead").value;
				resultObj = new createNewsObj(title, lead, source, text, uploadFileid);
			} else if ("study" == contentType) {
				var summary = document.getElementById("summary").value;
				resultObj = new createStudyObj(title, summary, source, text);
			} else if ("tltd" == contentType) {
				var summary = document.getElementById("summary").value;
				resultObj = new createTltdObj(title, summary, source, text);
			}
		} else {
			alert("参数有空");
		}
	}
	return resultObj;
}

function updateObj() {
	var resultObj = undefined;
	if("exam" == contentType) {
		var subject = document.getElementById("subject").value;
		var option1 = document.getElementById("option1").value;
		var option2 = document.getElementById("option2").value;
		var option3 = document.getElementById("option3").value;
		var option4 = document.getElementById("option4").value;
		var answer = dosetCheckbox();
		resultObj = new updateExamObj(selectid, subject, option1, option2, option3, option4, answer);
	} else {
		var title = document.getElementById("title").value;
		var source = document.getElementById("source").value;
		var text = document.getElementById("text").value;
		if(title != "" && source != "" && text != "") {
			if ("news" == contentType || "carousel" == contentType) {
				var lead = document.getElementById("lead").value;
				resultObj = new updateNewsObj(selectid, title, lead, source, text, uploadFileid);
			} else if ("study" == contentType) {
				var summary = document.getElementById("summary").value;
				resultObj = new updateStudyObj(selectid, title, summary, source, text);
			} else if ("tltd" == contentType) {
				var summary = document.getElementById("summary").value;
				resultObj = new updateTltdObj(selectid, title, summary, source, text);
			}
		} else {
			alert("参数有空");
		}
	}
	return resultObj;
}

function lockInput() {
	if("exam" == contentType) {
		document.getElementById("subject").disabled = true;
		document.getElementById("option1").disabled = true;
		document.getElementById("option2").disabled = true;
		document.getElementById("option3").disabled = true;
		document.getElementById("option4").disabled = true;
		document.getElementById("answer1").disabled = true;
		document.getElementById("answer2").disabled = true;
		document.getElementById("answer3").disabled = true;
		document.getElementById("answer4").disabled = true;
	} else {
		document.getElementById("title").disabled = true;
		document.getElementById("source").disabled = true;
		document.getElementById("text").disabled = true;
		if("news" == contentType) {
			document.getElementById("lead").disabled = true;
		} else if("study" == contentType) {
			document.getElementById("summary").disabled = true;
		} else if("tltd" == contentType) {
			document.getElementById("summary").disabled = true;
		}
	}
}

function createNewsObj(title, lead, source, text, imgid) {
	this.title = title;
	this.lead = lead;
	this.source = source;
	this.text = text;
	this.imgid = imgid;
}

function updateNewsObj(id, title, lead, source, text, imgid) {
	this.id = id;
	this.title = title;
	this.lead = lead;
	this.source = source;
	this.text = text;
	this.imgid = imgid;
}

function createStudyObj(title, summary, source, text){
	this.title = title;
	this.summary = summary;
	this.source = source;
	this.text = text;
}

function updateStudyObj(id, title, summary, source, text) {
	this.id = id;
	this.title = title;
	this.summary = summary;
	this.source = source;
	this.text = text;
}

function createTltdObj(title, summary, source, text){
	this.title = title;
	this.summary = summary;
	this.source = source;
	this.text = text;
}

function updateTltdObj(id, title, summary, source, text) {
	this.id = id;
	this.title = title;
	this.summary = summary;
	this.source = source;
	this.text = text;
}

function createExamObj(subject, option1, option2, option3, option4, answer) {
	this.subject = subject;
	this.option1 = option1;
	this.option2 = option2;
	this.option3 = option3;
	this.option4 = option4;
	this.answer = answer;
}

function updateExamObj(id, subject, option1, option2, option3, option4, answer) {
	this.id = id;
	this.subject = subject;
	this.option1 = option1;
	this.option2 = option2;
	this.option3 = option3;
	this.option4 = option4;
	this.answer = answer;
}

function dosetCheckbox() {
	var result = undefined;
	if (document.getElementById("answer1").checked == true) {
		result = "1";
	} else if (document.getElementById("answer2").checked == true) {
		result = "2";
	} else if (document.getElementById("answer3").checked == true) {
		result = "3";
	} else if (document.getElementById("answer4").checked == true) {
		result = "4";
	}
	return result;
}

function setCheckbox(answer) {
	if ("1" == answer) {
		document.getElementById("answer1").checked = true;
	} else if ("2" == answer) {
		document.getElementById("answer2").checked = true;
	} else if ("3" == answer) {
		document.getElementById("answer3").checked = true;
	} else if ("4" == answer) {
		document.getElementById("answer4").checked = true;
	}
}

function searchObj(id){
	this.id = id;
}

function readUsername() {
	return document.cookie.split('&')[0].split('=')[1];
}

function readToken() {
	return document.cookie.split('&')[1].split('=')[1];
}