(function(window) {
	'use strict';
	var document = window.document;
	var console = window.console;
	var WILDGOOSE = window.WILDGOOSE || {};
	WILDGOOSE.header = WILDGOOSE.header || {};

	// 의존성 선언 
	var Account = WILDGOOSE.account;
	var Popup = CAGE.ui.popup;
	var TemplateUtil = CAGE.util.template;
	var Dom = CAGE.util.dom;
	
	
	var leaveBtn = document.querySelector("#leave");
	
	var leavePopup = new Popup.ajaxPopup({
		element: leaveBtn,
		templateUrl: "/api/v1/templates/withdraw.html",
		templateLoader: function(AjaxResponse) {
			var templateStr = JSON.parse(AjaxResponse).data.template;
			var randNum = JSON.parse(AjaxResponse).data.rand;
			var compiler = TemplateUtil.getCompiler(templateStr);
			return compiler({
				"randNum": randNum
			}, templateStr);		
		}
	});
	
	leavePopup.afteropen.add(function() {
		var params = ["password"];
		Account.addValidationEvent(params);
		var btn = arguments[0].querySelector("#withdraw");
		btn.addEventListener("click", Account.withdrawAccount.bind(this, leavePopup), false);
	});
	
	var changePwBtn = document.querySelector("#change-password");
	
	var changePwPopup = new Popup.ajaxPopup({
		element: changePwBtn,
		templateUrl: "/api/v1/templates/changePassword.html",
		templateLoader: function(AjaxResponse) {
			var templateStr = JSON.parse(AjaxResponse).data.template;
			var randNum = JSON.parse(AjaxResponse).data.rand;
			var compiler = TemplateUtil.getCompiler(templateStr);
			return compiler({
				"randNum": randNum
			}, templateStr);		
		}
	});
	
	changePwPopup.afteropen.add(function() {
		var params = ["password"];
		Account.addValidationEvent(params);
		var btn = arguments[0].querySelector("#change");
		btn.addEventListener("click", Account.changePassword.bind(this, changePwPopup), false);
	});
	
	
}(this));