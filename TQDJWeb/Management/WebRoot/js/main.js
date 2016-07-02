function menuSelect(id) {
	switch (id) {
	case 1:
		$("#option-frame").attr("src","accountlist.jsp");
		break;
	case 2:
		$("#option-frame").attr("src","newslist.jsp");
		break;
	case 3:
		$("#option-frame").attr("src","studylist.jsp");
		break;
	case 4:
		$("#option-frame").attr("src","tltdlist.jsp");
		break;
	case 5:
		$("#option-frame").attr("src","examlist.jsp");
		break;
	case 6:
		$("#option-frame").attr("src","carousellist.jsp");
		break;
	}
}

function logout(){
	alert("退出");
}