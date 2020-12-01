// ==UserScript==
// @name         New Userscript
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        http://test.fuzhiyiliao.cn:8080/main/index
// @grant        none
// ==/UserScript==

(function() {
    'use strict';
    // Your code here...
    var parent = document.getElementsByClassName("navbar-header pull-left")[0];
    var child1 = document.getElementsByTagName("img")[0];
    var child2 = document.getElementsByTagName("div")[3];
    parent.removeChild(child1);
    parent.removeChild(child2);
    var childOld = document.getElementById('systemType');
    var childNew = document.createElement('div');
    childNew.id = 'systemType';
    childNew.style = "float: left; margin-left: 15px;display: block;height: 30px;margin-top: 28px;color:white;font-size: 22px;line-height: 30px;border-left: 0px solid #eee;padding-left: 0px;"
    childNew.innerHTML = '总控后台管理';
    parent.replaceChild(childNew, childOld);

})();