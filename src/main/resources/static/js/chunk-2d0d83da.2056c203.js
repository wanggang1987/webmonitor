(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0d83da"],{"79df":function(t,a,e){"use strict";e.r(a);var i=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticStyle:{padding:"12px",height:"100%"}},[e("el-row",{staticStyle:{height:"80%"}},[e("el-col",{staticStyle:{height:"50%","margin-top":"48px"},attrs:{span:8}},[e("div",{staticStyle:{height:"100%"},attrs:{id:"chartmain"}})]),e("el-col",{staticStyle:{padding:"48px 0px"},attrs:{span:16}},[e("span",[t._v("报错列表")]),e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.errList}},[e("el-table-column",{attrs:{prop:"time",label:"日期",width:"180"}}),e("el-table-column",{attrs:{prop:"type",label:"类型",width:"180"}}),e("el-table-column",{attrs:{prop:"url",label:"地址",width:"180"}}),e("el-table-column",{attrs:{prop:"msg",label:"报错"}})],1)],1)],1),e("el-row",{staticStyle:{height:"80%"}},[e("el-steps",{attrs:{space:200}},t._l(t.eventList,(function(t,a){return e("el-step",{key:a,attrs:{title:t.time,description:t.type}})})),1)],1)],1)},s=[],r=e("313e"),n=e("bc3a"),l=e.n(n),o={name:"showHome",data:function(){return{taskName:"",errList:[],guid:"",eventList:[]}},created:function(){var t=this.$route.query,a=t.data;a=JSON.parse(decodeURI(a)),this.guid=a.guid,console.log(a,"接入参数")},mounted:function(){this.loadData(),this.loadBarData(),this.loadDataErr()},methods:{loadData:function(){var t=this;l.a.request({params:{guid:this.guid},url:"/manage/events"}).then((function(a){t.eventList=a.data.list})).catch((function(t){}))},loadBarData:function(){var t=this;l.a.request({params:{guid:this.guid},url:"/manage/modules"}).then((function(a){var e=[];for(var i in a.data)console.log(i),e.push({name:i,value:a.data[i]});t.loadEcharts(e)})).catch((function(t){}))},loadDataErr:function(){var t=this;l.a.request({params:{guid:this.guid},url:"/manage/errors"}).then((function(a){t.errList=a.data.list})).catch((function(t){}))},loadEcharts:function(t){var a=r["b"](document.getElementById("chartmain")),e={title:{text:"模块时长",left:"center"},tooltip:{trigger:"item"},legend:{orient:"vertical",left:"left"},series:[{name:"Access From",type:"pie",radius:"50%",data:t,emphasis:{itemStyle:{shadowBlur:10,shadowOffsetX:0,shadowColor:"rgba(0, 0, 0, 0.5)"}}}]};a.setOption(e)}}},c=o,d=e("2877"),u=Object(d["a"])(c,i,s,!1,null,null,null),h=u.exports;a["default"]=h}}]);