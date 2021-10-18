/*! Copyright (c) 2020 Hamilton Medical AG. All Rights Reserved. */
/******/
/******/ function QdValidator(){};
/******/
/******/
/******/
/******/ QdValidator.Rule = function() {
/******/   this.rules = {}
/******/   var _this = this;
/******/   this.addRule = function(elm, r){
/******/       _this.rules[elm] = r;
/******/       return _this;
/******/   }
/******/   return this;
/******/ }
/******/
/******/ QdValidator.Message = function() {
/******/   this.messages = {}
/******/   var _this = this;
/******/   this.addMessage = function(elm, m){
/******/       _this.messages[elm] = m;
/******/       return _this;
/******/   }
/******/   return this;
/******/ }
/******/
/******/
/******/ QdValidator.prototype.addMethod = function(elm, func){
/******/   $.validator.addMethod(elm, func);
/******/ }
/******/
/******/ QdValidator.prototype.addRuleProperty = function(name){
/******/   return {[name]: true};
/******/ }
/******/
/******/ QdValidator.prototype.addRuleEqualToProperty = function(elm){
/******/   return {equalTo: elm};
/******/ }
/******/
/******/ QdValidator.prototype.addRuleMinLengthProperty = function(size){
/******/   return {minlength: size};
/******/ }
/******/
/******/ QdValidator.prototype.addRuleProperties = function(names){
/******/   var result = {}
/******/   for(const name of names){
/******/     result[name] = true;
/******/   }
/******/   return result;
/******/ }
/******/
/******/ QdValidator.prototype.validateForm = function(form, rules, messages, errorElement, onEventHandler, onSubmitHandler){
/******/   $(form).validate({
/******/     rules: rules,
/******/     messages: messages,
/******/     errorElement: errorElement,
/******/     onfocusout: onEventHandler,
/******/     onkeyup: onEventHandler,
/******/     onclick: onEventHandler,
/******/     submitHandler: onSubmitHandler
/******/   });
/******/ }
/******/
/******/ QdValidator.prototype.doValidation = function(){
/******/  this.validateForm("#qd-upload-image-form", 
/******/                    new QdValidator.Rule().addRule("image", this.addRuleProperty("required")).rules,
/******/                    new QdValidator.Message().addMessage("image", "").messages,
/******/                    "label",
/******/                    (element, event)=>{$("#qd-upload-image-form").validate();var valid = $("#qd-upload-image-form").valid();if (valid) {$('#qd-upload-image-btn').prop('disabled', false);} else {$('#qd-upload-image-btn').prop('disabled', 'disabled');}},
/******/                    (form)=>{form.submit();}
/******/   );
/******/  this.validateForm("#qd-search-image-form", 
/******/                    new QdValidator.Rule().addRule("keyword-image", this.addRuleProperty("required")).addRule("keyword-image", this.addRuleMinLengthProperty(3)).rules,
/******/                    new QdValidator.Message().addMessage("keyword-image", "").messages,
/******/                    "label",
/******/                    (element, event)=>{$("#qd-search-image-form").validate();var valid = $("#qd-search-image-form").valid();if (valid) {$('#qd-search-image-btn').prop('disabled', false);} else {$('#qd-search-image-btn').prop('disabled', 'disabled');}},
/******/                    (form)=>{form.submit();}
/******/   );
/******/ }
/******/
/******/
/******/ export default QdValidator;
/************************************************************************/