(function(window) {
	'use strict';
	var document = window.document;
	var console = window.console;
	var WILDGOOSE = window.WILDGOOSE || {};
	WILDGOOSE.account = WILDGOOSE.account || {};
	WILDGOOSE.account.super_type = WILDGOOSE.account.super_type || {};

	/*
	 * validation action
	 */
	var Dom = CAGE.util.dom;
//	var Observer = CAGE.type.observer;
	var Ajax = CAGE.ajax;
	var Validator = WILDGOOSE.validator;
	
	function Account(args) {
		this.selectedEl = {};
		this.submitEl = null;
		this.randomNumber = null;
		this.method = null;
		this.rule = null;
		this.names = null;
		this.form = null;
		this.url = null;
		
		this.account(args);
	};
	
	Account.prototype = {
		constructor: "Account",
		account: function(args) {
			if (args !== undefined) {
				this.method = args.method;
				this.rule = args.rule;
				this.form = document.querySelector(args.form);
				this.url = args.url;
				
				if (this.rule !== undefined) {
					this.names = Object.keys(this.rule);
					this._extract();
					this._addValidationEvent();
					this.validator = new Validator(this.form, this.rule);
				}
				
				// account submit버튼을 serInterval을 이용하여 계속적 탐지
				if (this.submitEl !== undefined) {
					var obArgs = {
						targetElArr: Object.valueOf(this.selectedEl),
						observerEl: this.submit
					};
					this.observer = new Observer(obArgs);
					this.observer.activate();
//					this.observer.deactivate();
					
//					this.submitEl.setObserver(this.);
//					this.submitEl.addEventListener("observe", )
				}
			}
		},
		exec: function(callback) {
			Ajax[this.method]({
				"url": this.url,
				"success": function() {
					callback();
					console.log("Success!");
				},
				"failure": function() {
					console.log("FAIL!");
				},
				"data": this._getPayload()
			});
		},
		_getPayload: function() {
			/*
			 * interface
			 * subType에서 _getPayload를 구현해야함.
			 */ 
			return null;
		},
		
		_extract: function() {
			for (var i = this.form.length - 1; i >= 0; --i) {
				var el = this.form[i];
				if (el.name == "submit") {
					this.submitEl = el;
					continue;
				}
				if (el.name == "randomNumber") {
					this.randomNumber = el.value;
					continue;
				}
				
				if (this.names !== undefined && this.names.indexOf(el.name) != -1) {
					this.selectedEl[el.name] = el;
				}
			}
		},
			
		_addValidationEvent: function() {
			for (var name in this.selectedEl) {
				var el = this.selectedEl[name];
				el.addEventListener("blur", this._checkValidation.bind(this), false);
			}
		},
		
		_checkValidation: function(evt) {
			var inputEl = evt.target;
			if (this.validator.check(inputEl)) {
				console.log("validation ok");
			}
			
			// 각 input의 className을 확인하여 sumbit 버튼 활성화
			this._checkStatusOfSubmit.call(this);
		},
		
		// form에 입력된 내용이 valid한지를 확인하여 회원가입 버튼 활성화 / 비활성화
		_checkStatusOfSubmit: function() {
			var flag = true;
			for (var name in this.selectedEl) {
				if (!Dom.hasClass(this.selectedEl[name], "status-approved")) {
					flag = false;
					break;
				}
			}
			Dom[flag?"removeClass":"addClass"](this.submitEl, "disable");
		}
	};
	
	
	WILDGOOSE.account.super_type = Account;
	
	// 글로벌 객체에 모듈을 프로퍼티로 등록한다.
	if (typeof module !== 'undefined' && module.exports) {
		module.exports = WILDGOOSE;
		// browser export
	} else {
		window.WILDGOOSE = WILDGOOSE;
	}    	

}(this));