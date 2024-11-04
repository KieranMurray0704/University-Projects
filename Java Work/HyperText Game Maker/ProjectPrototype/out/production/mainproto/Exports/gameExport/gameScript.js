const nodeInfoArr = [];
const actionArr = [];

function intialiseArrs(){
	var pageTitle = document.getElementById("nodeTitle");
	var pageContent = document.getElementById("nodeContent");
	pageTitle.innerHTML = "GAME LOAD ERROR, NO NODE TITLE FOUND.";
	arrayConstruct.feedActions();
	arrayConstruct.feedArray();
	pageTitle.innerHTML = nodeInfoArr[0].nodeTitle;
	pageContent.innerHTML = nodeInfoArr[0].nodeContent;
	conTable.clearTab.call();
	actTable.clearTab.call();
	conTable.fillCons(nodeInfoArr[0]);
	actTable.fillTab(nodeInfoArr[0]);
}

function connectClick(connectInp){
	conTable.clearTab.call();
	actTable.clearTab.call();
	var pageTitle = document.getElementById("nodeTitle");
	var pageContent = document.getElementById("nodeContent");
	for(var i = 0; i < nodeInfoArr.length; i++){
		let tempID = nodeInfoArr[i].nodeID;
		if(tempID == connectInp){
			pageTitle.innerHTML = nodeInfoArr[i].nodeTitle;
			pageContent.innerHTML = nodeInfoArr[i].nodeContent;
			conTable.fillCons(nodeInfoArr[i]);
			actTable.fillTab(nodeInfoArr[i]);
			break;
		}
	}
}

function actionClick(nodeInp, actionInp){
	var pageTitle = document.getElementById("nodeTitle");
	pageTitle.innerHTML = "yippee";
	for(let i = 0; i < nodeInfoArr.length; i++){
		if(nodeInfoArr[i].nodeID == nodeInp){
			let tempArr = nodeInfoArr[i].actNames;
			let actionName = tempArr[actionInp];
			for(let j = 0; j <  actionArr.length; j++){
				if(actionArr[j].actionID == actionName){
					if(actionArr[j].actionBool == false){
						actionArr[j].actionBool = true;
					}else{
						actionArr[j].actionBool = false;
					}
					break;
				}
			}
		}
	}
}

const conTable = {
	clearTab: function(){
		var connectsTable = document.getElementById("connectTable");
		var rowNum = connectsTable.rows.length;
		for(var loop = 0; loop < rowNum; loop++){
			connectsTable.deleteRow(0);	
		}
	},
	fillCons: function(inpArr){
		var connectsTable = document.getElementById("connectTable");
		for(var i = 0; i < inpArr.connIDs.length; i++){
			let tempAct = inpArr.connAct[i];
			if(tempAct != "No Action"){
				let tog = actionArr.find(x => x.actionID === tempAct).actionBool;
				if(tog == true){
					let newRow = connectsTable.insertRow(0);
					let newCell = newRow.insertCell(0);
					let tempTex = inpArr.connTex[i];
					let tempCon = inpArr.connIDs[i];
					newCell.innerHTML = '<h3><a href="#" onclick="connectClick(' + tempCon + ')";>' + tempTex + '</a></h3>';
				}
			}else{
				let newRow = connectsTable.insertRow(0);
				let newCell = newRow.insertCell(0);
				let tempTex = inpArr.connTex[i];
				let tempCon = inpArr.connIDs[i];
				newCell.innerHTML = '<h3><a href="#" onclick="connectClick(' + tempCon + ')";>' + tempTex + '</a></h3>';
			}
		}
	}
}

const actTable = {
	clearTab: function(){
		var actionsTable = document.getElementById("actionsTable");
		var rowNum = actionsTable.rows.length;
		for(var loop = 0; loop < rowNum; loop++){
			actionsTable.deleteRow(0);	
		}
	},
	fillTab: function(inpArr){
		var actionsTable = document.getElementById("actionsTable");
		let rowCount = 0;
		for(let i = 0; i < inpArr.actNames.length; i++){
			let nameCheck = inpArr.actNames[i]
			let togCheck = actionArr.find(x => x.actionID === nameCheck).actionBool;
			let oneTimeCheck = actionArr.find(y => y.actionID === nameCheck).actionOneTime;
			if(oneTimeCheck == false){
				const newRow = actionsTable.insertRow(0);
				const newCell = newRow.insertCell(0);
				let tempName = inpArr.actNames[i];
				let tempTex = inpArr.actTex[i];
				newCell.innerHTML = '<h3><a href="#" onclick="actionClick('+ inpArr.nodeID + ', ' + i + ')";>' + tempTex + '</a></h3>';
			}else if(togCheck == false){
				const newRow = actionsTable.insertRow(0);
				const newCell = newRow.insertCell(0);
				let tempName = inpArr.actNames[i];
				let tempTex = inpArr.actTex[i];
				newCell.innerHTML = '<h3><a href="#" onclick="actionClick(' + inpArr.nodeID + ', ' + i + ')";>' + tempTex + '</a></h3>';
			}
		}
	}
}

const arrayConstruct = {
	feedArray: function(){
		var tempObj = {nodeID: 0, nodeTitle: "NEW NODE", nodeContent: "CONTENT", connIDs: [1], connTex: ["Use the apple to fight someone"], connAct: ["No Action"], actNames: ["Apple"], actTex: ["Eat the Apple"]};
		nodeInfoArr.push(tempObj);
		var tempObj = {nodeID: 1, nodeTitle: "NEW NODE", nodeContent: "CONTENT", connIDs: [], connTex: [], connAct: [], actNames: [], actTex: []};
		nodeInfoArr.push(tempObj);
	},
	feedActions: function(){
		var acInp = {actionID: "Apple", actionBool: false, actionOneTime: true}; 
		actionArr.push(acInp);
	}
}		var tempObj = {nodeID: 0, nodeTitle: "NEW NODE", nodeContent: "CONTENT
", connIDs: [1], connTex: ["New Connection"], connAct: ["No Action"], actNames: [], actTex: []};
		nodeInfoArr.push(tempObj);
		var tempObj = {nodeID: 1, nodeTitle: "NEW NODE", nodeContent: "CONTENT
", connIDs: [], connTex: [], connAct: [], actNames: [], actTex: []};
		nodeInfoArr.push(tempObj);
	},
	feedActions: function(){
		var acInp = {actionID: "", actionBool: false, actionOneTime: false}; 
		actionArr.push(acInp);
	}
}		var tempObj = {nodeID: 0, nodeTitle: "NEW NODE", nodeContent: "CONTENT
", connIDs: [1], connTex: ["New Connection"], connAct: ["No Action"], actNames: [], actTex: []};
		nodeInfoArr.push(tempObj);
		var tempObj = {nodeID: 1, nodeTitle: "NEW NODE", nodeContent: "CONTENT
", connIDs: [], connTex: [], connAct: [], actNames: [], actTex: []};
		nodeInfoArr.push(tempObj);
	},
	feedActions: function(){
		var acInp = {actionID: "", actionBool: false, actionOneTime: false}; 
		actionArr.push(acInp);
	}
}