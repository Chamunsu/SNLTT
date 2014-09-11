function login(){
	
	var mId = $("#login_id").val();
	var mPw = $("#login_password").val();
	var mAuto = $("#login_auto").is(":checked");
	
	alert("id = "+mId+", passowrd = "+mPw+" auto login is "+mAuto);
}

/*
 * id, password ���
 */
function registerResult(data){
	var result = new String(data);
	
	if(result == 'true'){
		window.alert("ȸ�� ������ �Ϸ��Ͽ����ϴ�.");
	}else{
		window.alert("ȸ�� ������ �����Ͽ����ϴ�. ����� �ٽ� �õ����ּ���.");
	}
}

function register(){
	var mId = $("#register_id").val();
	var mPw = $("#register_password").val();
	var mPw2 = $("#register_password2").val();
	var mName = $("#register_name").val();
	
	if(mPw == mPw2 && mPw != '' && mId != '' && mName != ''){
		$.post("RegistID", { id: mId, pw: mPw, name:mName },    
				function(data) {    registerResult(data);  },   "text" );
	}else{
		window.alert("�Է��Ͻ� ������ Ȯ�����ּ���.");
	}
}
/*
 * id, password ��� ��.
*/

/*
* ���� ��Ͽ��� summaryâ�� �ڵ����� ũ�� ��ȯ�ǰ� ����.
*/
function resize(obj) {
  obj.style.height = "1px";
  obj.style.height = (20+obj.scrollHeight)+"px";
}
/*
* �ڵ� ũ�� ���� ��
*/

/*
* �����̵� ���
*/
//ó�� �̹��� ũ�� �ʱ�ȭ ����  
var canvasview;
var overlay;
var picture;

//�̹��� ��ġ�� ������
var num = 1;
var firstnum;
var lastnum;

//��Ʈ�ѹٿ� ���� ��ġ�� ��Ż��ġ
var inputcurrentnum;
var spantotalnum;

//�̹����� ���� ��� ĵ����
var cmtcanvass;
var cmtStages;
var wrappers;
var toolstate;
var drawingCanvass;

//����� ���� ����
var oldPt;
var oldMidPt;
var color;
var stroke;

var path;
function viewerInit(values){
	var file = values.folder;
	init(file, values.slide_length);
}

function init(filePath, lastNum) {
	slideView = document.getElementById("back");
	slideView.setAttribute("style", "display:block;");
	toolstate = new Array(4);

	inputcurrentnum = document.getElementById("currentnum");
	spantotalnum = document.getElementById("totalnum");
	canvasview = document.getElementById("view");
	overlay = document.getElementById("overlay");
	
	firstnum = 1;
	path = filePath+"/";
	lastnum = lastNum;
	

	picture = new createjs.Bitmap(path+num + ".png");
	overlay.setAttribute('style',"width:100%; height:100%;");
	overlay.style.backgroundImage = "url(" + path+num + ".png)";
	initTool();
	initcommentcanvas();

	inputcurrentnum.setAttribute("value", num);
	spantotalnum.innerHTML = "/" + lastnum;
	document.getElementById("overraycanvas_" + (num - 1)).style.display = "block";
	//alert(stage.toDataURL("#FFFFFF","image/png"));

}

function initTool() {

	for ( var i = 0; i < toolstate.length; i++)
		toolstate[i] = false;
	
	document.getElementById("line").style.background = "#00FF00";
	document.getElementById("text").style.background = "#00FF00";
}

function initcommentcanvas() {

	cmtcanvass = new Array(lastnum);
	cmtStages = new Array(lastnum);
	wrappers = new Array(lastnum);
	drawingCanvass = new Array(lastnum);
	for ( var i = 0; i < lastnum; i++) {
		cmtcanvass[i] = document.createElement("canvas");
		cmtcanvass[i].setAttribute('id', 'overraycanvas_' + i);
		cmtcanvass[i].setAttribute('width', overlay.offsetWidth);
		cmtcanvass[i].setAttribute('height', overlay.offsetHeight);
		cmtcanvass[i].style.zIndex = i + 1;
		cmtcanvass[i].style.opacity = "0.5";
		overlay.appendChild(cmtcanvass[i]);
		document.getElementById("overraycanvas_" + i).style.display = "none";
		cmtStages[i] = new createjs.Stage(document
				.getElementById("overraycanvas_" + i));
		cmtStages[i].autoClear = false;
		cmtStages[i].enableDOMEvents(true);
		drawingCanvass[i] = new createjs.Shape();
		cmtStages[i].addChild(drawingCanvass[i]);
		cmtStages[i].update();
	}

}

function updateCanvas() {
	initTool();
	StageLinstenrstop();
	document.getElementById("line").style.background = "#00FF00";
	for ( var i = 0; i < lastnum; i++)
		document.getElementById("overraycanvas_" + i).style.display = "none";

	overlay.style.backgroundImage = "url(" + path+num + ".png)";
	document.getElementById("overraycanvas_" + (num - 1)).style.display = "block";
	inputcurrentnum.setAttribute("value", num);

}

function handleClick(event) {
	num++;
	updateCanvas();
}

function handleControl(state) {

	switch (state) {
	case 0:
		num = firstnum;
		updateCanvas();
		break;
	case 1:
		if (num == firstnum) {
			alert("ù��° ������ �Դϴ�!!");

		} else {
			num--;
			updateCanvas();
		}

		break;
	case 2:
		if (num == lastnum) {
			alert("������ ������ �Դϴ�!!");

		} else {
			num++;
			updateCanvas();
		}
		break;
	case 3:
		num = lastnum;
		updateCanvas();
		break;

	}

}

function clickToolbox(tool) {
	switch (tool) {
	case 0:
		StageLinstenrstop();
		initTool();
		
		if (!toolstate[0]) {
			document.getElementById("text").style.background = "#000080";
			toolstate[0] = true;

			cmtStages[num - 1].addEventListener("stagemousedown", handleText);

		} else {
			document.getElementById("text").style.background = "#00FF00";
			toolstate[0] = false;
		}
		break;
	case 1:
		if (!toolstate[1]) {

		} else {

		}
		break;
	case 2:

		StageLinstenrstop();
		initTool();
		if (!toolstate[2]) {
			color = "#FF0000";
			document.getElementById("line").style.background = "#000080";
			toolstate[2] = true;
			createjs.Touch.enable(cmtStages[num - 1]);
			createjs.Ticker.setFPS(24);
			cmtStages[num - 1].addEventListener("stagemousedown",
					handleMouseDown);
			cmtStages[num - 1].addEventListener("stagemouseup",
					handleMouseUp);
		} else {
			document.getElementById("line").style.background = "#00FF00";
			toolstate[2] = false;
		}
		break;
	case 3:
		if (!toolstate[3]) {
			color = "#F"
			toolstate[3] = true;

		} else {
			color = "#FF0000";
			toolstate[3] = false;

		}
		break;
	case 4:
		break;
	case 5:
		break;
	}
}

function StageLinstenrstop() {

	cmtStages[num - 1].removeAllEventListeners();
}
function handleMouseDown(event) {

	stroke = 10;
	oldPt = new createjs.Point(cmtStages[num - 1].mouseX,
			cmtStages[num - 1].mouseY);
	oldMidPt = oldPt;
	cmtStages[num - 1].addEventListener("stagemousemove", handleMouseMove);
}

function handleMouseMove(event) {
	var midPt = new createjs.Point(
			oldPt.x + cmtStages[num - 1].mouseX >> 1, oldPt.y
					+ cmtStages[num - 1].mouseY >> 1);

	drawingCanvass[num - 1].graphics.clear().setStrokeStyle(stroke,
			'round', 'round').beginStroke(color).moveTo(midPt.x, midPt.y)
			.curveTo(oldPt.x, oldPt.y, oldMidPt.x, oldMidPt.y);

	oldPt.x = cmtStages[num - 1].mouseX;
	oldPt.y = cmtStages[num - 1].mouseY;

	oldMidPt.x = midPt.x;
	oldMidPt.y = midPt.y;
	console.log(oldMidPt.x + " " + oldMidPt.y);

	cmtStages[num - 1].update();
}

function handleMouseUp(event) {
	cmtStages[num - 1].removeEventListener("stagemousemove",
			handleMouseMove);
}

function handleText(event) {

	var text = prompt("Please enter Text!!", "");
	if (text != null) {
		var text = new createjs.Text(text, "bold 10px Arial", color);
		text.x = cmtStages[num - 1].mouseX;
		
		text.y = cmtStages[num - 1].mouseY;
		cmtStages[num - 1].addChild(text);
		cmtStages[num - 1].update();
	}

}

function colorSelect(selectcolor){
	color = selectcolor;
	
}
/*
* �����̵� ��� ��.
*/